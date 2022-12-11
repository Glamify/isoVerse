package net.isoverse.isoproxy.spells;

import net.isoverse.isocore.spells.SpellType;
import net.isoverse.isoproxy.utills.Msg;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class SpellPluginMessageListener implements Listener {

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) throws IOException {
        if (event.getTag().equalsIgnoreCase("iso:spells")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            SpellType type = SpellType.valueOf(in.readUTF());
            String player = in.readUTF();
            String target = in.readUTF();
            String[] messages = type.getMessage();

            if (type.getMessage().length > 0) {
                if (target == null) {
                    BaseComponent[] message = Msg.color("&e⚡ &3" + messages[new Random().nextInt(messages.length)].replaceAll("%player%", player));
                    ProxyServer.getInstance().broadcast(message);
                    if (type.isSpigotReturn()) {
                        sendToBukkit(ProxyServer.getInstance().getPlayer(player).getServer().getInfo(), type.name(), player);
                    }
                } else {
                    BaseComponent[] message = Msg.color("&e⚡ &3" + messages[new Random().nextInt(messages.length)].replaceAll("%player%", player).replaceAll("%target%", target));
                    ProxyServer.getInstance().broadcast(message);
                    if (type.isSpigotReturn()) {
                        sendToBukkit(ProxyServer.getInstance().getPlayer(player).getServer().getInfo(), type.name(), player, target);
                    }
                }
            }
        }
    }

    private void sendToBukkit(ServerInfo server, String channel, String player) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(channel);
            out.writeUTF(player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Note the "Return". It is the channel name that we registered in our Main class of Bungee plugin.
        server.sendData("iso:spells", stream.toByteArray());

    }

    private void sendToBukkit(ServerInfo server, String channel, String player, String target) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(channel);
            out.writeUTF(player);
            out.writeUTF(target);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Note the "Return". It is the channel name that we registered in our Main class of Bungee plugin.
        server.sendData("iso:spells", stream.toByteArray());

    }
}