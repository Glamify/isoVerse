package net.isoverse.isocore.spells.command;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.playerdata.SpellWrapper;
import net.isoverse.isocore.spells.SpellType;
import net.isoverse.isocore.spells.SpellsManager;
import net.isoverse.isocore.utills.Msg;
import net.isoverse.isocore.utills.Time;
import org.bson.conversions.Bson;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            if (args.length == 0) {
                player.sendMessage(Msg.errorMsg("syntax", label + " [player]"));
                return true;
            }

            PlayerData playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
            PlayerDataWrapper[] playerFile = playerData.get();
            SpellWrapper spells = playerFile[0].getSpells();

            double spellCount = spells.getCry();
            if (spellCount <= 0) {
                player.sendMessage(Msg.errorMsg("spells"));
                return true;
            }

            long time;
            if (player.hasPermission("iso.pro")) {
                time = spells.getSpellLast() + Time.toMilliSec("3m");
            } else {
                time = spells.getSpellLast() + Time.toMilliSec("9m");
            }

            if (System.currentTimeMillis() < time) {
                player.sendMessage(Msg.errorMsg("cooldown", Time.left(time).replaceFirst(" ", "")));
                if (!player.hasPermission("iso.pro")) {
                    player.sendMessage(Msg.errorMsg("", "Get &bPro &7to cut your spell cooldown time to 3 minutes!"));
                }
                return true;
            }

            spells.setEyeRoll(spellCount - 1);
            spells.setSpellLast(System.currentTimeMillis());

            playerFile[0].setSpells(spells);
            playerData.replace(playerFile[0]);

            new SpellsManager(SpellType.CRY, player.getName());

        }
        return true;
    }
}
