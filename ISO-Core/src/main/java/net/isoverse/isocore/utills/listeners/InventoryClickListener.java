package net.isoverse.isocore.utills.listeners;


import net.isoverse.isocore.utills.GUIWrapper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InventoryClickListener implements Listener {

    public static List<GUIWrapper> openGuis = new ArrayList<GUIWrapper>();
    @EventHandler
    public void onClickItem(InventoryClickEvent event) throws InterruptedException, IOException {
        for (int i=0;i<openGuis.size();i++) {
            GUIWrapper gui = openGuis.get(i);
            if(gui.inventory.equals(event.getClickedInventory())){
                gui.clickSlot(event.getSlot(), event.getClick());
                event.setCancelled(true);
            }
        }
    }
}
