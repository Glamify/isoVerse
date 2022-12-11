package net.isoverse.isocore.queue;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.chat.nicknames.NickRequest;
import net.isoverse.isocore.chat.nicknames.NickRequestWrapper;
import net.isoverse.isocore.chat.nicknames.commands.NicknameCommand;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.tickets.TicketMongoFunctions;
import net.isoverse.isocore.tickets.TicketWrapper;
import net.isoverse.isocore.utills.Msg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class QueueCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            if (!player.hasPermission("iso.mod")) {
                player.sendMessage(Msg.errorMsg("permission"));
                return true;
            }

            ArrayList<String> show = new ArrayList<>();
            boolean display = false;


            if (args.length >= 1) {
                if (args[0].contains("ticket") || args[0].contains("modreq")) {
                    show.add("ticket");
                }
                if (args[0].contains("nick")) {
                    show.add("nickname");
                }
            } else {
                show.add("ticket");
                show.add("nickname");
            }


            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_RED, ChatColor.RED, "Queue"));

            /*
             * Tickets
             */

            if (show.contains("ticket")) {
                TicketWrapper[] tickets = TicketMongoFunctions.getTickets(Filters.eq("status","open"));

                for (int i = 0; i < tickets.length; i++) {
                    if (i == 0) {
                        player.sendMessage(Msg.format(" Open Tickets:"));
                    }

                    PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid",tickets[i].uuid)).get();

                    TextComponent textComponent = Component.text()
                            .content("  ")
                            .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                    .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Click to close this ticket"))))
                                    .clickEvent(ClickEvent.suggestCommand("/ticket close " + tickets[i].serverIdNum))
                                    .build())
                            .append(Component.text(" "))
                            .append(Component.text().content(Msg.format("&8[&b&li&8]"))
                                    .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Click to learn about ticket"))))
                                    .clickEvent(ClickEvent.suggestCommand("/ticket info " + tickets[i].serverIdNum))
                                    .build())
                            .append(Component.text(Msg.format(" &f" + tickets[i].category + " &7by " + playerData[0].getIgn())))
                            .build();
                    player.sendMessage(textComponent);
                    display = true;
                }
            }

            /*
            * Nickname
            * Coming soon
            */

            if (show.contains("nickname")) {
                NickRequestWrapper[] nicknames = new NickRequest(null).get();

                for (int i = 0; i < nicknames.length; i++) {
                    if (i == 0) {
                        player.sendMessage(Msg.format(" Open Nickname Requests:"));
                    }

                    PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid",nicknames[i].uuid)).get();

                    TextComponent textComponent = Component.text()
                            .content("  ")
                            .append(Component.text().content(Msg.format("&8[&a&l✔&8]"))
                                    .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Click to accept this nickname"))))
                                    .clickEvent(ClickEvent.suggestCommand("/nick accept " + playerData[0].getIgn()))
                                    .build())
                            .append(Component.text(" "))
                            .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                    .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Click to reject this nickname"))))
                                    .clickEvent(ClickEvent.suggestCommand("/nick reject " + playerData[0].getIgn()))
                                    .build())
                            .append(Component.text(Msg.format(" &f" + nicknames[i].nickname + " &7by " + playerData[0].getIgn())))
                            .build();
                    player.sendMessage(textComponent);
                    display = true;
                }
            }

            /*
            * Display empty queue message
            */

            if (display == false) {
                player.sendMessage(Msg.format("  &oThere are no items in the queue."));
            }
            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_RED));
        }
        return true;
    }


}
