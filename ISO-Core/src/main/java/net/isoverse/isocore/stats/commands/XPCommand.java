package net.isoverse.isocore.stats.commands;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.stats.LevelData;
import net.isoverse.isocore.stats.StatsManager;
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

public class XPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {

            PlayerData playerData;
            PlayerDataWrapper[] playerFile;
            String prefix;
            if (args.length == 1) {
                playerData = new PlayerData(Filters.eq("ign", args[0]));
                playerFile = playerData.get();

                if (playerFile.length == 0) {
                    player.sendMessage(Msg.errorMsg("connected"));
                    return true;
                }

                prefix = playerFile[0].getIgn() + " is";
            } else {
                playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
                playerFile = playerData.get();
                prefix = "You are";

            }

            if (args.length == 2) {
                if (!playerFile[0].getIgn().equalsIgnoreCase(player.getName())) {
                    player.sendMessage(Msg.errorMsg("You can't change another player's level color."));
                    return true;
                }

                String colorName = null;
                String color = playerFile[0].getLevelColor();
                if (args[1].equalsIgnoreCase("darkGray")) {
                    if (color.equalsIgnoreCase("&8")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Dark Gray";
                    color = "&8";
                } else if (args[1].equalsIgnoreCase("lightGray")) {
                    if (color.equalsIgnoreCase("&7")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Light Gray";
                    color = "&7";
                }  else if (args[1].equalsIgnoreCase("darkAqua")) {
                    if (color.equalsIgnoreCase("&3")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Dark Aqua";
                    color = "&3";
                }  else if (args[1].equalsIgnoreCase("darkGreen")) {
                    if (color.equalsIgnoreCase("&2")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Dark Green";
                    color = "&2";
                }  else if (args[1].equalsIgnoreCase("darkPurple")) {
                    if (color.equalsIgnoreCase("&5")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Dark Purple";
                    color = "&5";
                }  else if (args[1].equalsIgnoreCase("lightBlue")) {
                    if (color.equalsIgnoreCase("&9")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Light Blue";
                    color = "&9";
                }  else if (args[1].equalsIgnoreCase("gold")) {
                    if (color.equalsIgnoreCase("&6")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Gold";
                    color = "&6";
                }  else if (args[1].equalsIgnoreCase("yellow")) {
                    if (color.equalsIgnoreCase("&e")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Yellow";
                    color = "&e";
                }  else if (args[1].equalsIgnoreCase("darkRed")) {
                    if (color.equalsIgnoreCase("&4")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    player.sendMessage(Msg.noticeMsg("Level color updated.", "Your level color is now black."));
                    colorName = "Dark Red";
                    color = "&4";
                }  else if (args[1].equalsIgnoreCase("black")) {
                    if (color.equalsIgnoreCase("&0")) {
                        player.sendMessage(Msg.errorMsg("Invalid color.", "You already have this level color selected."));
                        return true;
                    }
                    colorName = "Black";
                    color = "&0";
                }

                if (color == null) {
                    player.sendMessage(Msg.errorMsg("Invalid color.", "That color could not be found."));
                    return true;
                }

                player.sendMessage(Msg.noticeMsg("Level color updated.", "Your level color is now *" + colorName + "*."));

                playerFile[0].setLevelColor(color);
                playerData.replace(playerFile[0]);

                return true;

            }

            LevelData data = StatsManager.getLevelData(playerFile[0].getXp());

            float levelPercentage = (float) data.getExpRemainder() / data.getExpNextLevel();
            float barSize = 10 - Math.max(0, Math.min(0.999F, levelPercentage));
            float highlighted = Math.max(0, Math.min(0.999F, levelPercentage)) * 10;

            int level = playerFile[0].getLevel();
            String levelColor = playerFile[0].getLevelColor();

            long nextXP = data.getExpNextLevel() - data.getExpRemainder();
            int nextLevel = playerFile[0].getLevel()+1;

            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "XP"));
            if (playerFile[0].getLevel() == 100) {
                player.sendMessage(Msg.format("                   &b&k|||||  &f" + prefix + " &fa &nlevel 100!&r   &b&k|||||"));
            } else {
                player.sendMessage(Msg.format("            &f" + prefix + " a &nlevel " + level + "&7 with " + levelColor + playerFile[0].getXp() + "&fxp."));
                player.sendMessage(Msg.format("                   " + levelColor + nextXP + "&7xp to advance to &flevel " + nextLevel + "&7."));
                player.sendMessage(Msg.format("                         &8[&7" + playerFile[0].getLevel() + " " + bar(barSize, highlighted, levelColor) + " &7" + nextLevel + "&8]"));
            }

            if (playerFile[0].getIgn().equalsIgnoreCase(player.getName())) {
                String available = "&b> Click to activate";
                String unavailable = "&4✕ &cLocked";

                TextComponent darkGray;
                TextComponent lightGray;
                TextComponent darkAqua;
                TextComponent darkGreen;
                TextComponent darkPurple;
                TextComponent lightBlue;
                TextComponent gold;
                TextComponent yellow;
                TextComponent darkRed;
                TextComponent black;

                //Dark Gray
                darkGray = Component.text(Msg.format("&8[■]"))
                        .hoverEvent(HoverEvent.showText(Component.text(Msg.format(Msg.format(available)))
                                .clickEvent(ClickEvent.runCommand("/xp " + player.getName() + " darkGray"))));

                //Light Gray
                if (level >= 15) {
                    lightGray = Component.text(Msg.format("&8[&7■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.runCommand("/xp " + player.getName() + " darkGray"));
                } else {
                    lightGray = Component.text(Msg.format("&8[&7■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Dark Aqua
                if (level >= 30) {
                    darkAqua = Component.text(Msg.format("&8[&3■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.runCommand("/xp " + player.getName() + " darkAqua"));
                } else {
                    darkAqua = Component.text(Msg.format("&8[&3■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Dark Green
                if (level >= 40) {
                    darkGreen = Component.text(Msg.format("&8[&2■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " darkGreen"));
                } else {
                    darkGreen = Component.text(Msg.format("&8[&2■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Dark Purple
                if (level >= 50) {
                    darkPurple = Component.text(Msg.format("&8[&5■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " darkPurple"));
                } else {
                    darkPurple = Component.text(Msg.format("&8[&5■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Light Blue
                if (level >= 60) {
                    lightBlue = Component.text(Msg.format("&8[&9■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " lightBlue"));
                } else {
                    lightBlue = Component.text(Msg.format("&8[&9■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Gold
                if (level >= 70) {
                    gold = Component.text(Msg.format("&8[&6■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " gold"));
                } else {
                    gold = Component.text(Msg.format("&8[&6■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Yellow
                if (level >= 80) {
                    yellow = Component.text(Msg.format("&8[&e■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " yellow"));
                } else {
                    yellow = Component.text(Msg.format("&8[&e■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Dark Red
                if (level >= 90) {
                    darkRed = Component.text(Msg.format("&8[&4■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " darkRed"));
                } else {
                    darkRed = Component.text(Msg.format("&8[&4■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }

                //Black
                if (level >= 100) {
                    black = Component.text(Msg.format("&8[&0■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(available))))
                            .clickEvent(ClickEvent.suggestCommand("/xp " + player.getName() + " black"));
                } else {
                    black = Component.text(Msg.format("&8[&0■&8]"))
                            .hoverEvent(HoverEvent.showText(Component.text(Msg.format(unavailable))));
                }
                TextComponent space = Component.text(" ");
                TextComponent base = Component.text().content(Msg.format("\n &3>&7 Change level color: "))
                        .append(darkGray)
                        .append(space)
                        .append(lightGray)
                        .append(space)
                        .append(darkAqua)
                        .append(space)
                        .append(darkGreen)
                        .append(space)
                        .append(darkPurple)
                        .append(space)
                        .append(lightBlue)
                        .append(space)
                        .append(gold)
                        .append(space)
                        .append(yellow)
                        .append(space)
                        .append(darkRed)
                        .append(space)
                        .append(black).build();
                player.sendMessage(base);
            }
            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
        }
        return true;
    }

    public String bar(float total, float highlighted, String color) {
        float main =  total - highlighted;
        String primary = "";
        String secondary = "";

        for (int i = 0; i < highlighted; i++) {
            primary += color + "■";
        }
        for (int i = 0; i < main; i++) {
            secondary += "&7■";
        }

        return ChatColor.translateAlternateColorCodes('&',  primary + secondary);


    }
}