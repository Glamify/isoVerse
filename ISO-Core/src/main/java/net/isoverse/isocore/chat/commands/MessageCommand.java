package net.isoverse.isocore.chat.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mongodb.client.model.Filters;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.utills.Msg;
import org.bson.conversions.Bson;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.mongodb.client.model.Updates.set;

public class MessageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            if (args.length <= 1) {
                player.sendMessage(Msg.errorMsg("syntax",  label + " [player] [message]"));
                return true;
            }


            PlayerData playerData = new PlayerData(Filters.eq("uuid", String.valueOf(player.getUniqueId())));
            PlayerData playerData2 = new PlayerData(Filters.eq("ignLower", args[0].toLowerCase()));

            PlayerDataWrapper[] playerFile = playerData.get();
            PlayerDataWrapper[] playerFile2 = playerData2.get();

            if (playerFile2.length == 0) {
                player.sendMessage(Msg.errorMsg("connected"));
                return true;
            }
            if (playerFile2[0].getStatus().equalsIgnoreCase("Offline")) {
                player.sendMessage(Msg.errorMsg("offline", ""));
                return true;
            }

            String message = "";
            for (int i = 1; i < args.length; i++) {
                message = message + " " + args[i];
            }

            final ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PrivateMessage");
            out.writeUTF(player.getName());
            out.writeUTF(args[0]);
            out.writeUTF(message);
            player.sendPluginMessage(ISOCore.getInstance(), "iso:chat", out.toByteArray());

            playerFile[0].setReplyingTo(playerFile2[0].getIgn());
            playerFile2[0].setReplyingTo(playerFile[0].getIgn());

            playerData.replace(playerFile[0]);
            playerData2.replace(playerFile2[0]);
        }

        return false;
    }
}
