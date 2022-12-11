package net.isoverse.isoproxy.chat;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isoproxy.ISOProxy;
import net.isoverse.isoproxy.mongo.MongoFunctions;
import net.isoverse.isoproxy.utills.Msg;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.InheritanceNode;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

public class ChatJoinListener implements Listener {

    @EventHandler
    public void onConnect(PostLoginEvent event) {

        PlayerDataWrapper[] playerFile = MongoFunctions.getPlayerFile(Filters.eq("uuid", String.valueOf(event.getPlayer().getUniqueId())));

        boolean playerFound = true;
        if (playerFile.length == 0) {
            playerFound = false;
        }

        if (playerFound) {
            if (!playerFile[0].isVanished()) {
                ProxyServer.getInstance().broadcast(Msg.color("&8[&a+&8] &7" + event.getPlayer().getName()));
            }
            playerFile[0].setStatus("Online");
            playerFile[0].setServer("Hub");
            playerFile[0].setLastSeen(System.currentTimeMillis());

            User user = ISOProxy.getLuckPerms().getPlayerAdapter(ProxiedPlayer.class).getUser(event.getPlayer());
            Set<String> groups = user.getNodes(NodeType.INHERITANCE).stream()
                    .map(InheritanceNode::getGroupName)
                    .collect(Collectors.toSet());

            ArrayList<String> ranks = new ArrayList<>();
            for (String s : groups) {
                ranks.add(s);
            }

            playerFile[0].setRanks(ranks);

            if (!playerFile[0].getIgn().equalsIgnoreCase(event.getPlayer().getName())) {
                playerFile[0].setIgn(event.getPlayer().getName());
                playerFile[0].setIgnLower(event.getPlayer().getName().toLowerCase());
            }
            MongoFunctions.replace(Filters.eq("uuid", String.valueOf(event.getPlayer().getUniqueId())), playerFile[0]);
        } else {
            ProxyServer.getInstance().broadcast(Msg.color("&8[&a+&8] &b&oWelcome to the isoVerse Network, " + event.getPlayer().getName()));

        }
    }
}
