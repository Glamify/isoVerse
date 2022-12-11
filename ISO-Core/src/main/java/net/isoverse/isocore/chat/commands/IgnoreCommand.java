package net.isoverse.isocore.chat.commands;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.utills.Msg;
import org.bson.conversions.Bson;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.mongodb.client.model.Updates.set;

public class IgnoreCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            PlayerData playerData = new PlayerData(Filters.eq("uuid", String.valueOf(player.getUniqueId())));
            PlayerData playerData2 = new PlayerData(Filters.eq("ign", args[0]));
            PlayerDataWrapper[] playerFile = playerData.get();
            PlayerDataWrapper[] playerFile2 = playerData2.get();


            if (args.length == 0) {
                player.sendMessage(Msg.errorMsg("syntax",  label + " [player]"));
                return true;
            }

            if (playerFile2.length == 0) {
                player.sendMessage(Msg.errorMsg("connected"));
                return true;
            }

            if (playerFile2[0].getRanks().contains("Admin") || playerFile2[0].getRanks().contains("Dev") || playerFile2[0].getRanks().contains("Mod")) {
                player.sendMessage(Msg.errorMsg("Invalid player.", "You are unable to block staff."));
                return true;
            }

            boolean add = true;
            for (String blocked : playerFile[0].getBlocked()) {
                if (blocked.equals(playerFile2[0].getUuid())) {
                    add = false;
                }
            }
            if (add) {
                playerFile[0].getBlocked().add(playerFile2[0].getUuid());
                player.sendMessage(Msg.successMsg("Player blocked.", "You have blocked " + args[0] + "."));
            } else {
                playerFile[0].getBlocked().remove(playerFile2[0].getUuid());
                player.sendMessage(Msg.successMsg("Player unblocked.", "You have unblocked " + args[0] + "."));
            }

            //set Mongo update and push to DB
            playerData.replace(playerFile[0]);

            //send the player a success message


            return true;
        }
        return false;
    }

}
