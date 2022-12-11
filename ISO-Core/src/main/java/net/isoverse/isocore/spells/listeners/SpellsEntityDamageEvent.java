package net.isoverse.isocore.spells.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class SpellsEntityDamageEvent implements Listener {

    @EventHandler
    public void DamageEvent(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (SpellPluginMessageListener.getGod()) {
                event.setCancelled(true);
            }

            SpellPluginMessageListener.getRocket().forEach(r -> {
                if (r.equals(player)) {
                    event.setCancelled(true);
                }
            });

        }
    }

}
