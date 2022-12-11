package net.isoverse.isocore.utills;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.isoverse.isocore.ISOCore;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.messaging.PluginMessageRecipient;

import java.util.Random;

public class Msg {

    private static int textWidth(String str) {
        return (int) (str.length());
    }

    public static String ellipsis(final String text, int length) {
        if (text.length()>length) {
            return text.substring(0, length - 3) + "...";
        } else {
            return text;
        }
    }

    public static String format(String info) {
        return ChatColor.translateAlternateColorCodes('&', "&7" + info.replaceAll("\\*(.+?)\\*", "&f$1&7"));
    }


    public static String list(String reason) {
        return ChatColor.translateAlternateColorCodes('&', "&3&l❱ &b" + reason + ":");
    }

    public static String list(String reason, String main) {
        return ChatColor.translateAlternateColorCodes('&', "&3&l❱ &b" + reason + ": &7(" + main + "&7)");
    }

    public static String errorMsg(String reason) {
        return errorMsg(reason, "");
    }

    public static String errorMsg(String reason, String main) {
        if (reason.equalsIgnoreCase("system")) {
            reason = "System disabled.";
            main = "This system is currently disabled.";

        } else if (reason.equalsIgnoreCase("vanished")) {
            reason = "System disabled.";
            main = "This system is disabled while vanished.";

        } else if (reason.equalsIgnoreCase("unvanished")) {
            reason = "System disabled.";
            main = "This system is disabled when not vanished.";

        } else if (reason.equalsIgnoreCase("console")) {
            reason = "Invalid sender.";
            main = "This command can not be executed by console.";

        } else if (reason.equalsIgnoreCase("player")) {
            reason = "Invalid sender.";
            main = "This command can not be executed by a player.";

        } else if (reason.equalsIgnoreCase("connected")) {
            reason = "Invalid player.";
            main = "That player has not yet joined isoVerse.";

        } else if (reason.equalsIgnoreCase("syntax")) {
            reason = "Invalid syntax.";
            main = "Try */" + main + "*.";

        } else if (reason.equalsIgnoreCase("unspecified")) {
            reason = "Unspecified Player.";
            main = "Try */" + main + "*.";

        } else if (reason.equalsIgnoreCase("cooldown")) {
            reason = "Command on cooldown.";
            main = "You must wait *" + main + "*.";

        } else if (reason.equalsIgnoreCase("offline")) {
            reason = "Invalid player.";
            main = "That player is not on isoVerse right now.";

        } else if (reason.equalsIgnoreCase("reboot")) {
            reason = "System disabled.";
            main = "This system is disabled before a reboot.";

        } else if (reason.equalsIgnoreCase("world")) {
            reason = "Invalid world.";
            main = "This command is not available in *" + main + "*.";

        } else if (reason.equalsIgnoreCase("spells") || reason.equalsIgnoreCase("spell")) {
            reason = "You need to collect this spell from crates to use it.";

        } else if (reason.equalsIgnoreCase("permission")) {
            reason = "You don't have permission for that.";

        } else if (reason.equalsIgnoreCase("upgrade")) {
            reason = "Only " + main + "'s can use this. Type */upgrade* for access!";
        }

        if (main == "" || main == null) {
            return ChatColor.translateAlternateColorCodes('&', "&4\u2715 &c" + reason.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        } else if (reason == "" || reason == null) {
            return ChatColor.translateAlternateColorCodes('&', "&4\u2715 &7" + main.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        } else {
            return ChatColor.translateAlternateColorCodes('&', "&4\u2715 &c" + reason.replaceAll("\\*(.+?)\\*", "&f$1&7") + " &7" + main.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        }
    }

    public static String noticeMsg(String reason) {
        return noticeMsg(reason, "");
    }

    public static String noticeMsg(String reason, String main) {
        if (main == "" || main == null) {
            return ChatColor.translateAlternateColorCodes('&', "&3&l\u2503 &b" + reason.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        } else if (reason == "" || reason == null) {
            return ChatColor.translateAlternateColorCodes('&', "&3&l\u2503 &7" + main.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        } else {
            return ChatColor.translateAlternateColorCodes('&', "&3&l\u2503 &b" + reason.replaceAll("\\*(.+?)\\*", "&f$1&7") + " &7" + main.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        }
    }

    public static String successMsg(String reason) {
        return successMsg(reason, "");
    }

    public static String successMsg(String reason, String main) {
        if (main == "" || main == null) {
            return ChatColor.translateAlternateColorCodes('&', "&2\u2714 &a" + reason.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        } else if (reason == "" || reason == null) {
            return ChatColor.translateAlternateColorCodes('&', "&2\u2714 &7" + main.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        } else {
            return ChatColor.translateAlternateColorCodes('&', "&2\u2714 &a" + reason.replaceAll("\\*(.+?)\\*", "&f$1&7") + " &7" + main.replaceAll("\\*(.+?)\\*", "&f$1&7"));
        }

    }

    public static String generateString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 10) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }

    public static void rawGlobal(String message) {
        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("RawMessage");
        out.writeUTF(message);

        ((PluginMessageRecipient) Bukkit.getServer().getOnlinePlayers().toArray()[0]).sendPluginMessage(ISOCore.getInstance(), "iso:chat", out.toByteArray());
    }

    public static String genLine(ChatColor primaryColor, ChatColor secondaryColor) {
        return genLine(primaryColor, secondaryColor, null, "", null, "");
    }

    public static String genLine(ChatColor primaryColor, ChatColor secondaryColor, ChatColor headerColor, String header) {
        return genLine(primaryColor, secondaryColor, headerColor, header, null, "");
    }

    public static String genLine(ChatColor primaryColor, ChatColor secondaryColor, ChatColor headerColor, String header, ChatColor subHeaderColor, String subHeader) {
        int length = 52;
        if (header != "") {
            length = length - header.length();
            length = length - subHeader.length();
            length = length - 2;
            if (subHeader != "") {
                subHeader = " &7> " + subHeaderColor + subHeader;
                length = length - 2;
            }
            header = "&8[" + headerColor + "&l" + header + subHeader + "&8]";
        }
        String main = "";
        int section = 1;
        for (int i = length / 2 - 1; i >= 0; i--) {
            if (section == 1) {
                main = main + primaryColor + "-";
                section = 2;
            } else {
                main = main + secondaryColor + "-";
                section = 1;
            }
        }
        return ChatColor.translateAlternateColorCodes('&', main + header + main);
    }

}
