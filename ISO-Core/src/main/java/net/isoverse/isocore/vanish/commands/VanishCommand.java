package net.isoverse.isocore.vanish.commands;

import net.isoverse.isocore.vanish.VanishManager;
import net.isoverse.isocore.utills.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            if (!(player.hasPermission("iso.mod"))) {
                player.sendMessage(Msg.errorMsg("permission", ""));
                return true;
            }

            if (VanishManager.getVanishState(player)) {
                VanishManager.setVanishState(player, false);
                player.sendMessage(Msg.noticeMsg("","You have *disabled* your vanish mode."));
                Msg.rawGlobal("&8[&a+&8]&7 " + player.getName());
            } else {
                VanishManager.setVanishState(player, true);
                player.sendMessage(Msg.noticeMsg("","You have *enabled* your vanish mode."));
                Msg.rawGlobal("&8[&c-&8]&7 " + player.getName());
            }

        }

        return false;
    }
}
