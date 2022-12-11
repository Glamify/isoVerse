package net.isoverse.isoproxy.chat;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.chat.nicknames.NicknameWrapper;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isoproxy.ISOProxy;
import net.isoverse.isoproxy.mongo.MongoFunctions;
import net.isoverse.isoproxy.utills.Msg;
import net.luckperms.api.model.user.User;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.*;
import java.util.Map;

public class ChatPluginMessageListener implements Listener {

    @EventHandler
    public void onPluginMessageReceived(PluginMessageEvent event) throws IOException {
        if (event.getTag().equalsIgnoreCase("iso:chat")) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(event.getData()));
            String type = in.readUTF();
            if (type.equalsIgnoreCase("ChatMessage")) {
                /*
                 * Get player, load them in mongo
                 */
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(in.readUTF());
                PlayerDataWrapper[] playerFile = MongoFunctions.getPlayerFile(Filters.eq("uuid", player.getUniqueId().toString()));

                /*
                 * Luckperms information
                 */
                User user = ISOProxy.getLuckPerms().getPlayerAdapter(ProxiedPlayer.class).getUser(player);
                String prefix = user.getCachedData().getMetaData().getPrefix();
                String level = playerFile[0].getLevelColor() + playerFile[0].getLevel();

                /*
                 * Get Nickname
                 */
                String name = player.getName();
                for (NicknameWrapper nick : playerFile[0].getNicknames()) {
                    if (nick.getStatus().equalsIgnoreCase("Active")) {
                        name = "~" + nick.getNickname();
                    }
                }

                String message = in.readUTF();

                /*
                 * Format and send message
                 */
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {

                    PlayerDataWrapper[] playerFile2 = MongoFunctions.getPlayerFile(Filters.eq("uuid", p.getUniqueId().toString()));

                    boolean send = true;
                    for (String blocked : playerFile2[0].getBlocked()) {
                        if (blocked.equals(player.getUniqueId().toString())) {
                            send = false;
                        }
                    }

                    if (send) {
                        p.sendMessage(Msg.color(level + " " + prefix + name + " &8\u00BB &f" + message));
                    }
                }

            }else if (type.equalsIgnoreCase("PrivateMessage")) {
                ProxiedPlayer sender = ProxyServer.getInstance().getPlayer(in.readUTF());
                ProxiedPlayer recipient = ProxyServer.getInstance().getPlayer(in.readUTF());
                String message = in.readUTF();

                if (recipient == null) {
                    return;
                }

                sender.sendMessage(Msg.color("&8[&5&oyou" + " &8\u00BB &7" + recipient + "&8]&d" + message));
                recipient.sendMessage(Msg.color("&8[&7" + sender.getName() + " &8\u00BB &5&oyou&8]&d" + message));

                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    if (p.hasPermission("iso.mod")) {
                        p.sendMessage(Msg.color("&8[&4" + sender.getName() + " &8\u00BB &7" + recipient + "&8]&c" + message));
                    }
                }

            } else if (type.equalsIgnoreCase("StaffMessage")) {
                ProxiedPlayer player = ProxyServer.getInstance().getPlayer(in.readUTF());
                String message = in.readUTF();
                for (ProxiedPlayer p : ProxyServer.getInstance().getPlayers()) {
                    if (p.hasPermission("iso.mod")) {
                        p.sendMessage(Msg.color("&8[&4Staff&8] &4" + player.getName() + " &8\u00BB &c" + message));
                    }
                }

            } else if (type.equalsIgnoreCase("RawMessage")) {
                String message = in.readUTF();
                ProxyServer.getInstance().broadcast(Msg.color(message));


            } else if (type.equalsIgnoreCase("command")) {
                String cmd = in.readUTF();
                Map<String, ServerInfo> servers = ProxyServer.getInstance().getServers();

                for (Map.Entry<String, ServerInfo> en : servers.entrySet()) { // Looping through each Server of Bungee.
                    String name = en.getKey();
                    ServerInfo all = ProxyServer.getInstance().getServerInfo(name);
                    sendToBukkit("command", cmd, all); // "command" is a sub-channel which will be used to determine the message is sent by this plugin.
                }


            } else if (type.equalsIgnoreCase("console")) {
                String cmd = in.readUTF();

                ProxyServer.getInstance().getPluginManager().dispatchCommand(ProxyServer.getInstance().getConsole(), cmd);
            }
        }
    }

    private void sendToBukkit(String channel, String message, ServerInfo server) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF(channel);
            out.writeUTF(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Note the "Return". It is the channel name that we registered in our Main class of Bungee plugin.
        server.sendData("iso:chat", stream.toByteArray());

    }

}
