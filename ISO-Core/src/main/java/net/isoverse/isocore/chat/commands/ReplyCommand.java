package net.isoverse.isocore.chat.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mongodb.client.model.Filters;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.utills.Msg;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            PlayerDataWrapper[] playerFile = new PlayerData(Filters.eq("uuid", String.valueOf(player.getUniqueId()))).get();
            PlayerDataWrapper[] playerFile2 = new PlayerData(Filters.eq("ign", playerFile[0].getReplyingTo())).get();

            if (playerFile2.length == 0 || playerFile2[0].getStatus().equalsIgnoreCase("Offline")) {
                player.sendMessage(Msg.errorMsg("You don't have anyone to reply to.", ""));
                return true;
            }

            if (args.length == 0) {
                player.sendMessage(Msg.errorMsg("syntax",  label + " [message]"));
                return true;
            }

            String message = "";
            for (int i = 0; i < args.length; i++) {
                message = message + " " + args[i];
            }

            final ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("PrivateMessage");
            out.writeUTF(player.getName());
            out.writeUTF(playerFile[0].getReplyingTo());
            out.writeUTF(String.join(" ", message));
            player.sendPluginMessage(ISOCore.getInstance(), "iso:chat", out.toByteArray());

            return true;
        }

        return false;
    }
}
