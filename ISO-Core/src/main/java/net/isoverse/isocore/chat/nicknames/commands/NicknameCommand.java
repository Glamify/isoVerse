package net.isoverse.isocore.chat.nicknames.commands;

import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.isoverse.isocore.chat.mail.MailWrapper;
import net.isoverse.isocore.chat.mail.SendMail;
import net.isoverse.isocore.chat.nicknames.NickRequest;
import net.isoverse.isocore.chat.nicknames.NickRequestWrapper;
import net.isoverse.isocore.chat.nicknames.NicknameWrapper;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.utills.Msg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.mongodb.client.model.Updates.push;
import static com.mongodb.client.model.Updates.set;

public class NicknameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Nicknames"));
                player.sendMessage(Msg.format(" &a/" + label + " list &7- &fView a list of your accepted nicknames"));
                player.sendMessage(Msg.format(" &a/" + label + " list [player]&7- &fView a list of another player's nicknames"));
                player.sendMessage(Msg.format(" &a/" + label + " request [nickname] &7- &fRequest a new nickname"));
                player.sendMessage(Msg.format(" &a/" + label + " activate [ID] &7- &fEnable an accepted nickname"));
                player.sendMessage(Msg.format(" &a/" + label + " deactivate &7- &fTurn off your current nickname"));
                player.sendMessage(Msg.format(" &a/" + label + " delete [ID] &7- &fRemove an accepted nickname"));
                if (player.hasPermission("iso.mod")) {
                    player.sendMessage(Msg.format(" &c/mod nick &7- &fView nickname moderation commands"));
                }
                if (player.hasPermission("iso.elite")) {
                    player.sendMessage("");
                    player.sendMessage(Msg.format(" &fBecause you're an &aElite&f, you get a formatted name change."));
                    player.sendMessage(Msg.format(" &7You can use any amount of colors you want, and even"));
                    player.sendMessage(" §7§lspecial §r§7§oformatting§r§7. No funky characters, and your nickname must be between 1 and 16 characters. You cannot use §4dark red §7(&4), §clight red §7(&c), §5dark purple §7(&5) or §0black §7(&0).");
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                    return true;
                }
                if (player.hasPermission("iso.titan")) {
                    player.sendMessage("");
                    player.sendMessage(Msg.format(" &fBecause you're a &bTitan&f, you get a color name change"));
                    player.sendMessage(" §7You can use different colors, no funky characters, and your nickname must be between 1 and 16 characters. You cannot use §4dark red §7(&4), §clight red §7(&c), §5dark purple §7(&5) or §0black §7(&0).");
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                    return true;
                }
                if (player.hasPermission("iso.ultra")) {
                    player.sendMessage("");
                    player.sendMessage(Msg.format(" &fBecause you're an &3Ultra&f, you get a single color name change."));
                    player.sendMessage(" §7You can use one color, no funky characters, and your nickname must be between 1 and 16 characters. You cannot use §4dark red §7(&4), §clight red §7(&c), §5dark purple §7(&5) or §0black §7(&0).");
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                    return true;
                }
                if (player.hasPermission("iso.hero")) {
                    player.sendMessage(Msg.format(""));
                    player.sendMessage(Msg.format(" &fBecause you're a &9Hero&f, you get a simple name change."));
                    player.sendMessage(Msg.format(" You cannot use any colors or funky characters, and your nickname must be between 1 and 16 characters."));
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                    return true;
                }
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
            }

            if (args[0].equalsIgnoreCase("list")) {
                PlayerData playerData;
                if (args.length == 1) {
                    playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
                } else {
                    playerData = new PlayerData(Filters.eq("ignLower", args[1].toLowerCase()));
                }

                PlayerDataWrapper[] playerFile = playerData.get();

                if (player.getName().equalsIgnoreCase(playerFile[0].getIgn())) {
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Nicknames"));

                    int i = 0;
                    for (NicknameWrapper nickLoop : playerFile[0].getNicknames()) {
                        //Accepted
                        if (nickLoop.getStatus().equalsIgnoreCase("Accepted")) {
                            TextComponent textComponent = Component.text()
                                    .content(Msg.format(" &7- &8[&a" + i + "&8] "))
                                    .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                            .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i))
                                            .build())
                                    .append(Component.text(" "))
                                    .append(Component.text().content(Msg.format("&8[&b&l✔&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Click to activate this nickname"))))
                                            .clickEvent(ClickEvent.suggestCommand(" /" + label + " activate " + i))
                                            .build())
                                    .append(Component.text(Msg.format(" &f" + nickLoop.getNickname())))
                                    .build();
                            player.sendMessage(textComponent);
                        }
                        //Active
                        if (nickLoop.getStatus().equalsIgnoreCase("Active")) {
                            TextComponent textComponent = Component.text()
                                    .content(Msg.format(" &b- &8[&b" + i + "&8] "))
                                    .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                            .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i))
                                            .build())
                                    .append(Component.text(" "))
                                    .append(Component.text().content(Msg.format("&8[&b&l-&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Click to deactivate this nickname"))))
                                            .clickEvent(ClickEvent.suggestCommand(" /" + label + " deactivate " + i))
                                            .build())
                                    .append(Component.text(Msg.format(" &f" + nickLoop.getNickname())))
                                    .build();
                            player.sendMessage(textComponent);
                        }
                        //Rejected
                        if (nickLoop.getStatus().equalsIgnoreCase("Rejected")) {
                            PlayerDataWrapper[] staff = new PlayerData(Filters.eq("uuid", nickLoop.getStatusBy())).get();
                            TextComponent textComponent = Component.text()
                                    .content(Msg.format(" &7- &8[&c" + i + "&8] "))
                                    .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                            .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i))
                                            .build())
                                    .append(Component.text(" "))
                                    .append(Component.text().content(Msg.format("&8[&7&l✔&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&7This nickname cannot be activated\n&7because it was rejected."))))
                                            .build())
                                    .append(Component.text(Msg.format(" &f" + nickLoop.getNickname() + ""))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Nickname rejected by " + staff[0].getIgn() + "\nCheck your &a/mail&7 for more information.")))))
                                    .build();
                            player.sendMessage(textComponent);
                        }
                        //Pending
                        if (nickLoop.getStatus().equalsIgnoreCase("Pending")) {
                            TextComponent textComponent = Component.text()
                                    .content(Msg.format(" &7- &8[&7" + i + "&8] "))
                                    .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                            .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i))
                                            .build())
                                    .append(Component.text(" "))
                                    .append(Component.text().content(Msg.format("&8[&7&l✔&8]"))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("This nickname cannot be activated\n&7because it must be accepted first"))))
                                            .build())
                                    .append(Component.text(Msg.format(" &f" + nickLoop.getNickname() + ""))
                                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Nickname has not yet been accepted.")))))
                                    .build();
                            player.sendMessage(textComponent);
                        }
                        i++;
                    }
                } else {
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Nicknames", ChatColor.WHITE, playerFile[0].getIgn()));
                    if (player.hasPermission("iso.mod")) {
                        int i = 0;
                        for (NicknameWrapper nickLoop : playerFile[0].getNicknames()) {
                            //Accepted
                            if (nickLoop.getStatus().equalsIgnoreCase("Accepted")) {
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &7- &8[&a" + i + "&8] "))
                                        .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                                .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i + " " + playerFile[0].getIgn()))
                                                .build())
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname())))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            //Active
                            if (nickLoop.getStatus().equalsIgnoreCase("Active")) {
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &b- &8[&b" + i + "&8] "))
                                        .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                                .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i + " " + playerFile[0].getIgn()))
                                                .build())
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname())))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            //Rejected
                            if (nickLoop.getStatus().equalsIgnoreCase("Rejected")) {
                                PlayerDataWrapper[] staff = new PlayerData(Filters.eq("uuid", nickLoop.getStatusBy())).get();
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &7- &8[&c" + i + "&8] "))
                                        .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                                .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i + " " + playerFile[0].getIgn()))
                                                .build())
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname() + ""))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Nickname rejected by " + staff[0].getIgn() + "\nCheck your &a/mail&7 for more information.")))))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            //Pending
                            if (nickLoop.getStatus().equalsIgnoreCase("Pending")) {
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &7- &8[&7" + i + "&8] "))
                                        .append(Component.text().content(Msg.format("&8[&4&l✖&8]"))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("&cClick to delete this nickname."))))
                                                .clickEvent(ClickEvent.suggestCommand(" /" + label + " delete " + i + " " + playerFile[0].getIgn()))
                                                .build())
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname() + ""))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Nickname has not yet been accepted.")))))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            i++;
                        }
                    } else {
                        int i = 0;
                        for (NicknameWrapper nickLoop : playerFile[0].getNicknames()) {
                            //Accepted
                            if (nickLoop.getStatus().equalsIgnoreCase("Accepted")) {
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &7- &8[&a" + i + "&8] "))
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname())))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            //Active
                            if (nickLoop.getStatus().equalsIgnoreCase("Active")) {
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &b- &8[&b" + i + "&8] "))
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname())))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            //Rejected
                            if (nickLoop.getStatus().equalsIgnoreCase("Rejected")) {
                                PlayerDataWrapper[] staff = new PlayerData(Filters.eq("uuid", nickLoop.getStatusBy())).get();
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &7- &8[&c" + i + "&8] "))
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname() + ""))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Nickname rejected by " + staff[0].getIgn() + "\nCheck your &a/mail&7 for more information.")))))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            //Pending
                            if (nickLoop.getStatus().equalsIgnoreCase("Pending")) {
                                TextComponent textComponent = Component.text()
                                        .content(Msg.format(" &7- &8[&7" + i + "&8] "))
                                        .append(Component.text(Msg.format(" &f" + nickLoop.getNickname() + ""))
                                                .hoverEvent(HoverEvent.showText(Component.text(Msg.format("Nickname has not yet been accepted.")))))
                                        .build();
                                player.sendMessage(textComponent);
                            }
                            i++;
                        }
                    }
                }

                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));

            } else if (args[0].equalsIgnoreCase("delete")) {
                PlayerData playerData;
                PlayerDataWrapper[] playerFile;
                NickRequest nickRequest;
                if (args[2] == null) {
                    playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
                    playerFile = playerData.get();
                    nickRequest = new NickRequest(Filters.eq("uuid", player.getUniqueId().toString()));
                } else {
                    if (!player.hasPermission("iso.mod")) {
                        player.sendMessage(Msg.errorMsg("permission"));
                        return true;
                    }
                    playerData = new PlayerData(Filters.eq("ignLower", args[2].toLowerCase()));
                    playerFile = playerData.get();
                    nickRequest = new NickRequest(Filters.eq("uuid", playerFile[0].getUuid()));
                }

                ArrayList<NicknameWrapper> nicknames = new ArrayList<>();

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname ID.", "You don't have a nickname *" + args[0] + "*."));
                    return true;
                }

                try {
                    playerFile[0].getNicknames().get(id);
                } catch(Exception e) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname ID.", "You don't have a nickname *" + args[0] + "*."));
                    return true;
                }

                int i = 0;
                for (NicknameWrapper nick : playerFile[0].getNicknames()) {
                    if (i != id) {
                        nicknames.add(nick);
                    }
                    i++;
                }

                playerData.update(Updates.set("nicknames", nicknames));

                if (nickRequest != null) {
                    nickRequest.delete();
                }

                player.sendMessage(Msg.successMsg("Nickname deleted.","Nickname *#" + id + "* has been removed."));

            } else if (args[0].equalsIgnoreCase("activate")) {
                PlayerData playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
                PlayerDataWrapper[] playerFile = playerData.get();

                ArrayList<NicknameWrapper> nicknames = new ArrayList<>();

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname ID.", "You don't have a nickname *" + args[0] + "*."));
                    return true;
                }

                try {
                    playerFile[0].getNicknames().get(id);
                } catch(Exception e) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname ID.", "You don't have a nickname *" + args[0] + "*."));
                    return true;
                }

                int i = 0;
                for (NicknameWrapper nick : playerFile[0].getNicknames()) {
                    if (i == id) {
                        nick.setStatus("Active");
                        nicknames.add(nick);
                    } else if (nick.getStatus().equalsIgnoreCase("Active")) {
                        nick.setStatus("Accepted");
                        nicknames.add(nick);
                    } else {
                        nicknames.add(nick);
                    }
                    i++;
                }

                playerData.update(Updates.set("nicknames", nicknames));

                player.sendMessage(Msg.successMsg("Nickname activated.","Nickname *#" + id + "* has been activated."));

            } else if (args[0].equalsIgnoreCase("deactivate") || args[0].equalsIgnoreCase("disabled")) {
                PlayerData playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
                PlayerDataWrapper[] playerFile = playerData.get();

                ArrayList<NicknameWrapper> nicknames = new ArrayList<>();

                int id;
                try {
                    id = Integer.parseInt(args[1]);
                } catch(NumberFormatException e) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname ID.", "You don't have a nickname *" + args[0] + "*."));
                    return true;
                }

                try {
                    playerFile[0].getNicknames().get(id);
                } catch(Exception e) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname ID.", "You don't have a nickname *" + args[0] + "*."));
                    return true;
                }

                int i = 0;
                for (NicknameWrapper nick : playerFile[0].getNicknames()) {
                    if (i == id) {
                        nick.setStatus("Accepted");
                        nicknames.add(nick);
                    } else if (nick.getStatus().equalsIgnoreCase("Active")) {
                        nick.setStatus("Accepted");
                        nicknames.add(nick);
                    } else {
                        nicknames.add(nick);
                    }
                    i++;
                }

                playerData.update(Updates.set("nicknames", nicknames));

                player.sendMessage(Msg.successMsg("Nickname deactivated.","Nickname *#" + id + "* has been deactivated."));

            } else if (args[0].equalsIgnoreCase("request")) {
                if (args.length == 1) {
                    player.sendMessage(Msg.errorMsg("unspecified", label + " request [nickname]"));
                    return true;
                }
                if (args.length == 3) {
                    player.sendMessage(Msg.errorMsg("Invalid nickname.","Nicknames cannot contain spaces."));
                    return true;
                }

                PlayerData playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
                PlayerDataWrapper[] playerFile = playerData.get();

                NickRequestWrapper[] nickname = new NickRequest(Filters.eq("uuid", String.valueOf(player.getUniqueId()))).get();
                if (nickname.length > 0) {
                    player.sendMessage(Msg.errorMsg("You already have a pending nickname request"));
                    return true;
                }

                double max = 1;
                if (player.hasPermission("iso.hero")) {
                    max = 3;
                }
                if (player.hasPermission("iso.titan")) {
                    max = 4;
                }
                if (player.hasPermission("iso.elite")) {
                    max = 6;
                }

                if (playerFile[0].getNicknames().size() >= max) {
                    player.sendMessage(Msg.errorMsg("Nickname limit reached.", "You can only have 1 nickname request at a time."));
                    return true;
                }
                String requestedNickname = args[1];

                if (requestedNickname.contains("wenke") && !player.getName().equals("joshwenke")) {
                    player.sendMessage(Msg.errorMsg("Nickname forbidden.", "Your nickname can not contain wenke."));
                    return true;
                }

                if (!player.hasPermission("iso.hero")) {
                    if (requestedNickname.contains("&")) {
                        player.sendMessage(Msg.errorMsg("Your donor rank does not allow color codes in nicknames."));
                        player.sendMessage(Msg.errorMsg("","Type */upgrade* to remove this limitation."));
                        return true;
                    }
                }

                if (requestedNickname.contains("&0")) {
                    player.sendMessage(Msg.errorMsg("Invalid color.", "Black is hard to read."));
                    return true;
                }
                if (requestedNickname.contains("&5")) {
                    player.sendMessage(Msg.errorMsg("Invalid color.", "Dark Purple is a developer color."));
                    return true;
                }
                if (requestedNickname.contains("&4") || requestedNickname.contains("&c")) {
                    player.sendMessage(Msg.errorMsg("Invalid color.", "Red is a staff color."));
                    return true;
                }

                if (requestedNickname.contains("&m") || requestedNickname.contains("&n") || requestedNickname.contains("&l") || requestedNickname.contains("&o")) {
                    if (!player.hasPermission("iso.elite")) {
                        player.sendMessage(Msg.errorMsg("Text formatting is reserved for Elites only."));
                        player.sendMessage(Msg.errorMsg("", "Use */upgrade* to remove this limitation."));
                        return true;
                    }
                }

                NickRequest nickRequest = new NickRequest(Filters.eq("uuid", player.getUniqueId().toString()));
                long time = System.currentTimeMillis();

                //queue nick
                nickRequest.create(player, time, requestedNickname);

                //playerdata nick
                playerFile[0].getNicknames().add(new NicknameWrapper("Pending", requestedNickname, System.currentTimeMillis(), 0, "none"));
                playerData.replace(playerFile[0]);

                //send player a success message for their request
                player.sendMessage(Msg.successMsg("Your nickname request \"" + requestedNickname + "&a\" has been submitted."));
                player.sendMessage(Msg.successMsg("","When it's reviewed, you'll receive */mail*."));
            } else if (args[0].equalsIgnoreCase("accept") || args[0].equalsIgnoreCase("approve")) {
                //make sure the player is staff
                if (!(player.hasPermission("iso.mod"))) {
                    player.sendMessage(Msg.errorMsg("permission", ""));
                    return true;
                }

                //if a player is not specified don't move on
                if (args.length == 3) {
                    player.sendMessage(Msg.errorMsg("unspecified", label + " " +  args[0] + " [player]"));
                    return true;
                }

                //grab the correct player file for the request based on player ign
                PlayerData playerData = new PlayerData(Filters.eq("ignLower", args[1].toLowerCase()));
                PlayerDataWrapper[] playerFileTwo = playerData.get();

                //if the player file does not exist let the staff member know
                if(playerFileTwo.length == 0) {
                    player.sendMessage(Msg.errorMsg("connected", ""));
                    return true;
                }

                NickRequest nickRequest = new NickRequest(Filters.eq("uuid", playerFileTwo[0].getUuid()));
                NickRequestWrapper[] nicknameRequest = nickRequest.get();
                if(nicknameRequest.length == 0) {
                    player.sendMessage(Msg.errorMsg("No pending nickname.", "This player has not requested a nickname."));
                    return true;
                }

                int i = 0;
                for (NicknameWrapper nick : playerFileTwo[0].getNicknames()) {
                    if (nick.getNickname().equals(nicknameRequest[0].getNickname())) {
                        nick.setStatusBy(player.getUniqueId().toString());
                        nick.setStatusOn(System.currentTimeMillis());
                        nick.setStatus("Accepted");

                        Bson update = Updates.set("nicknames." + i, nick);
                        playerData.update(update);
                    }
                    i++;
                }

                nickRequest.delete();

                player.sendMessage(Msg.successMsg("Nickname accepted.", "You have accepted " + args[1] + "'s nickname request."));
                new SendMail(playerData, "&fYour nickname \'" + nicknameRequest[0].getNickname() + "\" has been accepted by &o" + player.getName() + "&f. You may begin using it with &a /" + label + " activate " + nicknameRequest.length + "&f, or see a list of all accepted nicknames with &a /" + label + " list&f.");
            } else if (args[0].equalsIgnoreCase("reject") || args[0].equalsIgnoreCase("deny")) {
                //make sure the player is staff
                if (!(player.hasPermission("iso.mod"))) {
                    player.sendMessage(Msg.errorMsg("permission", ""));
                    return true;
                }

                //if a player is not specified don't move on
                if (args.length == 2) {
                    player.sendMessage(Msg.errorMsg("unspecified", label + " " +  args[0] + " [player] [reason]"));
                    return true;
                }

                //grab the correct player file for the request based on player ign
                PlayerData playerData = new PlayerData(Filters.eq("ignLower", args[1].toLowerCase()));
                PlayerDataWrapper[] playerFileTwo = playerData.get();

                //if the player file does not exist let the staff member know
                if(playerFileTwo.length == 0) {
                    player.sendMessage(Msg.errorMsg("connected", ""));
                    return true;
                }

                NickRequest nickRequest = new NickRequest(Filters.eq("uuid", playerFileTwo[0].getUuid()));
                NickRequestWrapper[] nicknameRequest = nickRequest.get();
                if(nicknameRequest.length == 0) {
                    player.sendMessage(Msg.errorMsg("No pending nickname.", "This player has not requested a nickname."));
                    return true;
                }


                String message = "";
                for (int i = 2; i < args.length; i++) {
                    message = message + " " + args[i];
                }

                int i = 0;
                for (NicknameWrapper nick : playerFileTwo[0].getNicknames()) {
                    if (nick.getNickname().equals(nicknameRequest[0].getNickname())) {
                        nick.setStatusBy(player.getUniqueId().toString());
                        nick.setStatusOn(System.currentTimeMillis());
                        nick.setStatus("Rejected");

                        Bson update = Updates.set("nicknames." + i, nick);
                        playerData.update(update);
                    }
                    i++;
                }

                nickRequest.delete();

                player.sendMessage(Msg.successMsg("Nickname rejected.", "You have rejected " + args[1] + "'s nickname request."));
                new SendMail(playerData, "&fYour nickname \'" + nicknameRequest[0].getNickname() + "\" has been rejected by &o" + player.getName() + "&f for the following reason:" + message);
            }
        }
        return true;
    }
}
