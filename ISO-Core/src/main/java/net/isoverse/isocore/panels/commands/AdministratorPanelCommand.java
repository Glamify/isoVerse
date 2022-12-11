package net.isoverse.isocore.panels.commands;

import net.isoverse.isocore.utills.Msg;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AdministratorPanelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player player) {
            if (!player.hasPermission("iso.admin")) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA, ChatColor.AQUA, "Rank Info", ChatColor.WHITE, "Administration Team"));
                player.sendMessage(Msg.format(" &fisoVerse &4Administrators &fare in charge of their respective teams, whether the staff, development, or build team. Admins are in direct contact with the each other at all times."));
                player.sendMessage("");
                player.sendMessage(Msg.format(" &3> &7You cannot apply to be an Administrator."));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_AQUA));
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_RED, ChatColor.RED, "Admin Panel"));
                player.sendMessage(Msg.format("&f(This panel currently is non functional)\n"));
                player.sendMessage(Msg.format(" &c/" + label + " server &7- &fBroadcast, whitelist, or reboot servers"));
                player.sendMessage(Msg.format(" &c/" + label + " gems &7- &fView, add, and remove gems"));
                player.sendMessage(Msg.format(" &c/" + label + " location &7- &fManage local server locations"));
                player.sendMessage(Msg.format(" &c/" + label + " profile &7- &fChange, reset, and view users"));
                player.sendMessage(Msg.format(" &c/mod &7- &fAccess additional management commands"));
                player.sendMessage(Msg.format(" &d/dev &7- &fAccess additional server settings"));
                player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.DARK_RED));
                return true;
            }
        }
        return true;
    }
}
