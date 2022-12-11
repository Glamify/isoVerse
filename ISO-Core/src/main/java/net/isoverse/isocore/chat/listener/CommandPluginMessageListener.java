package net.isoverse.isocore.chat.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;

public class CommandPluginMessageListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {

        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));

        try {
            String sub = in.readUTF(); // Sub-Channel
            if (sub.equals("command")) { // As in bungee part we gave the sub-channel name "command", here we're checking it sub-channel really is "command", if it is we do the rest of code.
                String cmd = in.readUTF(); // Command we gave in Bungee part.
                System.out.println("Received a command message from BungeeCord, executing it.");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), cmd); // Executing the command!!

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}