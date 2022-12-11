package net.isoverse.isoproxy.chat;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isoproxy.mongo.MongoFunctions;
import net.isoverse.isoproxy.utills.Msg;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatServerSwitchListener implements Listener {

    @EventHandler
    public void onSwitch(ServerSwitchEvent event) {

        PlayerDataWrapper[] playerFile = MongoFunctions.getPlayerFile(Filters.eq("uuid", String.valueOf(event.getPlayer().getUniqueId())));

        boolean playerFound = true;
        if (playerFile.length == 0) {
            playerFound = false;
        }

        if (playerFound) {
            if (event.getFrom() != null) {
                if (!playerFile[0].isVanished()) {
                    ProxyServer.getInstance().broadcast(Msg.color("&8[&3&l\u2771&8] &7" + event.getPlayer().getName() + " switched to " + event.getPlayer().getServer().getInfo().getName()));
                }
                playerFile[0].setServer(event.getPlayer().getServer().getInfo().getName());
                playerFile[0].setLastSeen(System.currentTimeMillis());
                MongoFunctions.replace(Filters.eq("uuid", String.valueOf(event.getPlayer().getUniqueId())), playerFile[0]);
            }
        }
    }
}