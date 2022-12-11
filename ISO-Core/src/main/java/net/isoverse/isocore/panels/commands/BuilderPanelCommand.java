package net.isoverse.isocore.panels.commands;

import net.isoverse.isocore.utills.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuilderPanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("iso.builder")) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Rank Info", ChatColor.WHITE, "Build Team"));
                player.sendMessage(Msg.format(" &fisoVerse &9Builders &fdesign our spawns and maps, and create in-game features for the community to enjoy. &7Builders are not staff members, and do not issue infractions or enforce rules."));
                player.sendMessage("");
                player.sendMessage(Msg.format(" &3> &7You cannot apply to be a Moderator."));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_BLUE, ChatColor.BLUE, "Builder Panel"));
                player.sendMessage(Msg.format("&f(This panel currently is non functional)\n"));
                player.sendMessage(Msg.format(" &9/" + label + " mode  &7- &fToggle builder mode"));
                player.sendMessage(Msg.format(" &9/" + label + " heads &7- &fAccess the head database"));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_BLUE));
                return true;
            }
        }
        return true;
    }
}
