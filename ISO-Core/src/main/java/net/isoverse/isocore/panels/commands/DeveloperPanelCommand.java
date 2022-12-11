package net.isoverse.isocore.panels.commands;

import net.isoverse.isocore.utills.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeveloperPanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("iso.dev")) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Rank Info", ChatColor.WHITE, "Development Team"));
                player.sendMessage(Msg.format("  &fisoVerse &5Developers &fcreate new features and improve isoVerse's user experience. They manage servers and work on behind-the-scenes tasks. Our team ensures that isoVerse runs smoothly. &7Developers are staff members, but generally do not issue infractions."));
                player.sendMessage("");
                player.sendMessage(Msg.format(" &3> &7You cannot apply to be a Moderator."));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_PURPLE, ChatColor.LIGHT_PURPLE, "Dev Panel"));
                player.sendMessage(Msg.format("&f(This panel currently is non functional)\n"));
                player.sendMessage(Msg.format(" &d/" + label + " info &7- &fView server info"));
                player.sendMessage(Msg.format(" &d/" + label + " git &7- &fRefresh the servers files from git"));
                if (player.hasPermission("iso.op")) {
                    player.sendMessage(Msg.format(" &d/" + label + " misc &7- &fMiscellaneous server settings"));
                    player.sendMessage(Msg.format(" &d/" + label + " zones &7- &fManage isoVerse zones"));
                } else {
                    player.sendMessage(Msg.format(" &d/" + label + " misc&c* &7- &fMiscellaneous server settings"));
                    player.sendMessage(Msg.format(" &d/" + label + " zones&c* &7- &fManage isoVerse zones"));
                }
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_PURPLE));
                return true;
            }
        }
        return true;
    }
}
