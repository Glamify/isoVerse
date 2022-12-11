package net.isoverse.isocore.panels.commands;

import net.isoverse.isocore.utills.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ModeratorPanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("iso.mod")) {
                if (label.equalsIgnoreCase("helper") || label.equalsIgnoreCase("hcp")) {
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Rank Info", ChatColor.WHITE, "Helper Team"));
                    player.sendMessage(Msg.format(" &fisoVerse &cHelpers &fare &cModerators&f in training, they are primarily responsible for enforcing rules and helping all players on isoVerse."));
                    player.sendMessage("");
                    player.sendMessage(Msg.format(" &3> &7Apply at &bisoverse.net/apply"));
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                    return true;
                } else {
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Rank Info", ChatColor.WHITE, "Moderation Team"));
                    player.sendMessage(Msg.format(" &fisoVerse &cModerators &fare primarily responsible for enforcing rules and helping all players on isoVerse."));
                    player.sendMessage("");
                    player.sendMessage(Msg.format(" &3> &7You cannot apply to be a Moderator."));
                    player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                    return true;
                }
            }
            if (args.length == 0) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_RED, ChatColor.RED, "Mod Panel"));
                player.sendMessage(Msg.format(" &f(This panel currently is non functional)\n"));
                player.sendMessage(Msg.format(" &c/queue &7- &fView the current moderator queue"));
                player.sendMessage(Msg.format(" &c/" + label + " security &7- &fIssue and view punishments"));
                player.sendMessage(Msg.format(" &c/" + label + " reports &7- &fView the report system"));
                player.sendMessage(Msg.format(" &c/" + label + " notes &7- &fView and add notes on an account"));
                player.sendMessage(Msg.format(" &c/" + label + " teleport &7- &fTeleport to and move players"));
                player.sendMessage(Msg.format(" &c/" + label + " player &7- &fView and kill players"));
                player.sendMessage(Msg.format(" &c/" + label + " vanish &7- &fVanish/unvanish"));
                player.sendMessage(Msg.format(" &c/" + label + " clearchat &7- &fClear chat"));
                player.sendMessage(Msg.format(" &c/" + label + " nick &7- &fManage and approve nicknames\""));
                player.sendMessage(Msg.format(" &c/" + label + " rollback &7- &fView rollback commands"));
                player.sendMessage(Msg.format(" &c/" + label + " profile &7- &fChange, reset, and view users"));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.RED));
                return true;
            }
        }
        return true;
    }
}
