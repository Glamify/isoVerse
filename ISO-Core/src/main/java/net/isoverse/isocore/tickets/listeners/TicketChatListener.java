package net.isoverse.isocore.tickets.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.tickets.commands.Ticket;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.io.IOException;

public class TicketChatListener implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncChatEvent event){
        if(Ticket.waitingForContent.containsKey(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(ISOCore.getInstance(), () -> {
                if(Ticket.waitingForContent.containsKey(event.getPlayer().getUniqueId())){ //check static map for if player is in status of waiting to say content
                    try {
                        Ticket.onContentRecieved(event.getPlayer(), PlainTextComponentSerializer.plainText().serialize(event.message())); //if he is, activate function in ticket
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        if(Ticket.waitingForContentReply.containsKey(event.getPlayer().getUniqueId())){
            event.setCancelled(true);
            Bukkit.getScheduler().runTask(ISOCore.getInstance(), () -> {
                if(Ticket.waitingForContentReply.containsKey(event.getPlayer().getUniqueId())){ //check static map for if player is in status of waiting to say content
                    try {
                        Ticket.onContentRecievedReply(event.getPlayer(), PlainTextComponentSerializer.plainText().serialize(event.message())); //if he is, activate function in ticket
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
