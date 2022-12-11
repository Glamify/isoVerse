package net.isoverse.isocore.vanish.events;

import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.vanish.VanishManager;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class VanishJoinEvent implements Listener {

    @EventHandler
    public void vanishJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (VanishManager.getVanishState(player)) {
            VanishManager.setVanishState(player, true);
        }

        for (int i = 0; i < VanishManager.getVanish().size(); i++) {
            player.hidePlayer(ISOCore.getInstance(), VanishManager.getVanish().get(i));
        }

        new BukkitRunnable() {
            double state = 0;
            @Override
            public void run() {
                if (!player.isOnline()) {
                    Bukkit.getScheduler().cancelTask(getTaskId());
                    VanishManager.getVanish().remove(player);
                } else {
                    if (VanishManager.getVanish().contains(player)) {
                        if (state == 0) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&4>&8>&8> &fYou are currently vanished &8<&8<&4<")));
                            state = 1;
                        } else if (state == 1) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&8>&4>&8> &cYou are currently vanished &8<&4<&8<")));
                            state = 2;
                        } else if (state == 2) {
                            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(ChatColor.translateAlternateColorCodes('&', "&8>&8>&4> &fYou are currently vanished &4<&8<&8<")));
                            state = 0;
                        }
                    }
                }
            }
        }.runTaskTimer(ISOCore.getInstance(), 0, 20);

    }
}
