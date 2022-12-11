package net.isoverse.isoproxy.security;

import com.mongodb.client.model.Filters;
import net.isoverse.isoproxy.mongo.MongoFunctions;
import net.isoverse.isoproxy.utills.Msg;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.LoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class SecurityLoginEvent implements Listener {

    @EventHandler
    public void onConnection(LoginEvent event) {

        if (event.isCancelled()) {
            return;
        }

        String reason = "&4\u2715 &cYou are currently blocked from isoVerse! &4\u2715\n&7Reason:&f Inappropriate Username\n\n&7Please change your username, before trying to connect again\n&7If you believe your username has been falsely blocked, visit &b&nisoverse.net/appeal";
        if (MongoFunctions.getInappropriateNames().find(Filters.eq("ign", event.getConnection().getName().toLowerCase())).first() != null) {
            event.setCancelled(true);
            event.setCancelReason(Msg.color(reason));
        }
    }
}
