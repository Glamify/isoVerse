package net.isoverse.isocore.security;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public class ReasonType {

    public static String process(String type, String reason) {
        if (type.equals("Reason")) {
            if (reason.contains("swear") || reason.contains("cuss") || reason.contains("Filter") || reason.contains("Bypass") || reason.contains("cursing") || reason.contains("curse")) {
                if (type.equalsIgnoreCase("reason")) {
                    return "Filter Bypass";
                }
                if (type.equalsIgnoreCase("description")) {
                    return "This server is for all ages, so bypassing our chat filter is not allowed. There are a wide range of ages that play on this server. Please do not do it again.";
                }
                if (type.equalsIgnoreCase("repeat")) {
                    return "I understand that I bypassed the filter, and I promise not to do it again";
                }
            }
            if (reason.contains("advert") || reason.contains("ads")) {
                return "Advertising";
            }
            if (reason.contains("spam")) {
                return "Spamming";
            }
            if (reason.contains("grief")) {
                return "Griefing";
            }
            if (reason.contains("disrespect") || reason.contains("respect") || reason.contains("rudeness")) {
                return "Disrespect";
            }
            if (reason.contains("hack") || reason.contains("mod") || reason.contains("illegal") || reason.contains("modifications") || reason.contains("cheating") || reason.contains("x-ray") || reason.contains("xray") || reason.contains("flight") || reason.contains("fly") || reason.contains("killaura") || reason.contains("bhop") || reason.contains("speed") || reason.contains("antislow")) {
                return "Illegal Modifications";
            }
            if (reason.contains("Discrimination") || reason.contains("Discriminate") || reason.contains("Homophob") || reason.contains("Nazi") || reason.contains("racism") || reason.contains("racist") || reason.contains("race")) {
                return "Discrimination";
            }
            if (reason.contains("Innapriorite") || reason.contains("Behavior")  || reason.contains("IB") ) {
                return "Inappropriate Behavior";
            }
            if (reason.contains("exploit")) {
                return "Expliot Abuse";
            }
            if (reason.contains("minimod")) {
                return "Minimodding";
            }
            if (reason.contains("bull") || reason.contains("harassment")) {
                return "Harassment";
            }
            if (reason.contains("language") || reason.contains("foreign") || reason.contains("english")) {
                return "Foreign Language";
            }
        }
        return null;
    }
}

