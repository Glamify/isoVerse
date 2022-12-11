package net.isoverse.isocore.chat.commands;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.utills.Msg;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageRecipient;


public class CommandExecuteCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            sender.sendMessage(Msg.errorMsg("player", ""));
            return true;
        }

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("command");
        out.writeUTF(String.join(" ", args));

        ((PluginMessageRecipient)Bukkit.getServer().getOnlinePlayers().toArray()[0]).sendPluginMessage(ISOCore.getInstance(), "iso:chat", out.toByteArray());

        return true;
    }
}
