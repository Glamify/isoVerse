package net.isoverse.isocore.security.commands;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.mongodb.client.model.Filters;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.security.InappropriateNameWrapper;
import net.isoverse.isocore.utills.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class InappropriateNameCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("iso.mod")) {
            sender.sendMessage(Msg.errorMsg("permission"));
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Msg.errorMsg("syntax",  label + " [player]"));
            return true;
        }

        PlayerData playerData = new PlayerData(Filters.eq("ign", args[0]));
        PlayerDataWrapper[] playerFile = playerData.get();

        if (playerFile.length == 0) {
            sender.sendMessage(Msg.errorMsg("connected"));
            return true;
        }

        String staff;
        if (sender instanceof Player player) {
            staff = player.getUniqueId().toString();
        } else {
            staff = "isoBot";
        }


        if (MongoManager.getInappropriateNames().find(Filters.eq("ign", playerFile[0].getIgnLower())).first() == null) {
            MongoManager.getInappropriateNames().insertOne(new InappropriateNameWrapper(playerFile[0].getIgnLower(), staff, System.currentTimeMillis()));

            ByteArrayDataOutput out = ByteStreams.newDataOutput();
            out.writeUTF("KickPlayer");
            out.writeUTF(playerFile[0].getIgn());
            out.writeUTF(Msg.format("&4\u2715&c You are currently blocked from isoVerse! &4\u2715\n&7Reason:&f Inappropriate Username\n\n&7Please change your username then come back\n&7If you believe your username has been falsely blocked, visit &b&nisoverse.net/appeal"));

            Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
            player.sendPluginMessage(ISOCore.getInstance(), "BungeeCord", out.toByteArray());
            sender.sendMessage(Msg.successMsg("Username blocked.", "You have blocked " + args[0] + "."));

        } else {
            MongoManager.getInappropriateNames().deleteMany(Filters.eq("ign", playerFile[0].getIgnLower()));
            sender.sendMessage(Msg.successMsg("Username unblocked.", "You have unblocked " + args[0] + "."));
        }

        return true;
    }

}
