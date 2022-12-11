package net.isoverse.isocore.chat.mail.commands;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.chat.mail.MailWrapper;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.utills.Msg;
import net.isoverse.isocore.utills.Time;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MailCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            PlayerData playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
            PlayerDataWrapper[] playerFile = playerData.get();
            if (args.length == 0) {

                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Mail"));

                int i = 0;
                for (MailWrapper mailLoop : playerFile[0].getMail()) {
                    String message = mailLoop.getMessage().length() > 40 ? mailLoop.getMessage().substring(0, 40) + "..." : mailLoop.getMessage();
                    if (mailLoop.isStatus()) {
                        player.sendMessage(Msg.format("&7○ &8[&7#&l" + i + "&8]&7 " + message));

                    } else {
                        player.sendMessage(Msg.format("&b● &8[&b#&l" + i + "&8]&7 " + message));
                    }
                    i++;
                }

                if (i == 0) {
                    player.sendMessage(Msg.format(" &oYou don't have any mail."));
                }

                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
            } else if (args[0].equalsIgnoreCase("clear")) {
                ArrayList<MailWrapper> mail = new ArrayList<>();
                playerData.update(Updates.set("mail", mail));
                player.sendMessage(Msg.noticeMsg("", "Your mailbox has been cleared."));
            } else {
                int id;
                try {
                     id = Integer.parseInt(args[0]);
                } catch(NumberFormatException e) {
                    player.sendMessage(Msg.errorMsg("Invalid mail message.", "You don't have a message *" + args[0] + "*."));
                    return true;
                }

                try {
                    playerFile[0].getMail().get(id);
                } catch(Exception e) {
                    player.sendMessage(Msg.errorMsg("Invalid mail message.", "You don't have a message *" + args[0] + "*."));
                    return true;
                }

                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Mail"));
                MailWrapper mail = playerFile[0].getMail().get(id);
                player.sendMessage(Msg.format(" Now viewing mail message *" + id + "* sent on " + Time.timestamp(mail.getReceivedDate()) + ":"));
                player.sendMessage(Msg.format(" &o" + mail.getServer() + " &7» &f" + mail.getMessage()));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));

                mail.setStatus(true);
                Bson update = Updates.set("mail." + id, mail);
                playerData.update(update);
            }
        }
        return true;
    }
}
