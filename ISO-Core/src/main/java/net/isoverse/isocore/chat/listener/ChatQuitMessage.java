package net.isoverse.isocore.chat.listener;

import net.kyori.adventure.text.Component;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.IOException;

public class ChatQuitMessage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerChat(PlayerQuitEvent event) throws IOException {
        event.quitMessage(Component.text(""));
    }
}
