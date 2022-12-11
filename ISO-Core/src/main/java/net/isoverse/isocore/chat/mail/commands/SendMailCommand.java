package net.isoverse.isocore.chat.mail.commands;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.chat.mail.MailWrapper;
import net.isoverse.isocore.chat.mail.SendMail;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.utills.Msg;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SendMailCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("iso.op")) {
            sender.sendMessage(Msg.errorMsg("permission"));
            return true;
        }

        PlayerData playerData = new PlayerData(Filters.eq("ign", args[0]));
        PlayerDataWrapper[] playerFile = playerData.get();

        if (playerFile.length == 0) {
            sender.sendMessage(Msg.errorMsg("connected", ""));
            return true;
        }

        String message = "";
        for (int i = 1; i < args.length; i++) {
            message = message + " " + args[i];
        }

        Player player = Bukkit.getPlayerExact(args[0]);
        if (player != null) {
            player.sendMessage(Msg.noticeMsg("New mail message.","You have an unread */mail* message."));
        }

        new SendMail(playerData, message.substring(1));


        return true;
    }
}
