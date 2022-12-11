package net.isoverse.isocore.chat.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.isoverse.isocore.ISOCore;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.IOException;

public class TextChatListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerChat(AsyncChatEvent event) throws IOException {
        Player player = event.getPlayer();
        if (event.isCancelled()) {
            return;
        }
        event.setCancelled(true);

        String msg = PlainTextComponentSerializer.plainText().serialize(event.message());

        if (player.hasPermission("iso.mod")) {
            if (msg.startsWith("@")) {
                final ByteArrayDataOutput out = ByteStreams.newDataOutput();
                out.writeUTF("StaffMessage");
                out.writeUTF(event.getPlayer().getName());
                out.writeUTF(msg.replaceFirst("@", ""));
                player.sendPluginMessage(ISOCore.getInstance(), "iso:chat", out.toByteArray());
                return;
            }
        }

        final ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("ChatMessage");
        out.writeUTF(event.getPlayer().getName());
        out.writeUTF(msg);

        player.sendPluginMessage(ISOCore.getInstance(), "iso:chat", out.toByteArray());

    }
}
