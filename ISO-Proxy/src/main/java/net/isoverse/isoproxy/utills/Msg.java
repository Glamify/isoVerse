package net.isoverse.isoproxy.utills;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Msg {



    public static BaseComponent[] color(String message) {

        //regex to test for hex and bukkit color codes
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);

        //build a text component
        ComponentBuilder componentBuilder = new ComponentBuilder("");

        int lastEnd=0;
        ChatColor lastColor = ChatColor.RESET;
        while (matcher.find()) {
            String before = message.substring(lastEnd, matcher.start());
            String color = message.substring(matcher.start(), matcher.end());
            lastEnd = matcher.end();
            componentBuilder.append("").color(lastColor);

            componentBuilder.append(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',before),ChatColor.RESET), ComponentBuilder.FormatRetention.ALL);
            lastColor = ChatColor.of(color.substring(1));
        }
        String before = message.substring(lastEnd);
        componentBuilder.append("").color(lastColor);
        componentBuilder.append(TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&',before),ChatColor.RESET), ComponentBuilder.FormatRetention.ALL);

        return componentBuilder.create();
    }


}
