package net.isoverse.isocore.spells.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class SpellsFoodLevelChangeEvent implements Listener {

    @EventHandler
    public void foodLevel(FoodLevelChangeEvent event) {
        if (event.getEntity() instanceof Player) {
            if (SpellPluginMessageListener.getGod()) {
                event.setCancelled(true);
            }
        }
    }

}
