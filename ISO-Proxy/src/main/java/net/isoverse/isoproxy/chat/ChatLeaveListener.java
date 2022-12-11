package net.isoverse.isoproxy.chat;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isoproxy.mongo.MongoFunctions;
import net.isoverse.isoproxy.utills.Msg;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.ServerDisconnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class ChatLeaveListener implements Listener {

    @EventHandler
    public void onDisconnect(ServerDisconnectEvent event) {

        PlayerDataWrapper[] playerFile = MongoFunctions.getPlayerFile(Filters.eq("uuid", String.valueOf(event.getPlayer().getUniqueId())));

        boolean playerFound = true;
        if (playerFile.length == 0) {
            playerFound = false;
        }
        if(event.getPlayer().getServer() == null) {
            if (playerFound) {
                if (!playerFile[0].isVanished()) {
                    ProxyServer.getInstance().broadcast(Msg.color("&8[&c-&8] &7" + event.getPlayer().getName()));
                }
                playerFile[0].setStatus("Offline");
                playerFile[0].setServer(event.getTarget().getName());
                playerFile[0].setLastSeen(System.currentTimeMillis());
                MongoFunctions.replace(Filters.eq("uuid", String.valueOf(event.getPlayer().getUniqueId())), playerFile[0]);

            }
        }
    }
}
