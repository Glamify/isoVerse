package net.isoverse.isocore.stats.commands;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.stats.StatsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class AddXPCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            PlayerData playerData = new PlayerData(Filters.eq("ign", args[0]));
            PlayerDataWrapper[] playerFile = playerData.get();

            if (playerFile.length == 0) {
                sender.sendMessage("Player not found");
                return true;
            }
            Player player = Bukkit.getPlayerExact(args[0]);
            if (player == null) {
                sender.sendMessage("Player offline");
                return true;
            }
            StatsManager.xp(player, "add", Integer.valueOf(args[1]));
        }
        return true;
    }
}