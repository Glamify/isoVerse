package net.isoverse.isocore.spells.listeners;

import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.spells.SpellType;
import net.isoverse.isocore.utills.Msg;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;

public class SpellPluginMessageListener implements PluginMessageListener {

    private static boolean god = false;
    private static ArrayList<Player> rocket = new ArrayList<>();
    public static ArrayList<Player> getRocket() {
        return rocket;
    }
    public static boolean getGod() {
        return god;
    }

    @Override
    public void onPluginMessageReceived(String channel, Player p, byte[] message) {
        DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
        try {
            SpellType type = SpellType.valueOf(in.readUTF());
            Player player = Bukkit.getPlayerExact(in.readUTF());
            Player target = Bukkit.getPlayerExact(in.readUTF());
            switch (type) {
                case BLIND:
                    target.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 3 * 20, 1));
                    break;
                case CRY:
                    player.getWorld().setStorm(true);
                    player.getWorld().setWeatherDuration(120 * 20);
                    break;
                case EYEROLL:
                    target.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 3 * 20, 1));
                    break;
                case FLY:
                    ArrayList<Player> flying = new ArrayList<>();
                    for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                        if (!allPlayers.getAllowFlight()) {
                            flying.add(allPlayers);
                            allPlayers.setAllowFlight(true);
                        }
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player flyingPlayers : flying) {
                                flyingPlayers.sendMessage(Msg.noticeMsg("","10 seconds of flight remaining!"));
                            }
                        }
                    }.runTaskLater(ISOCore.getInstance(), 60 * 20);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player flyingPlayers : flying) {
                                flyingPlayers.setAllowFlight(false);
                                flyingPlayers.sendMessage(Msg.noticeMsg("","Flight deactivated!"));
                            }
                        }
                    }.runTaskLater(ISOCore.getInstance(), 60 * 20);

                case FROST:
                    target.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 3 * 20, 1));
                    break;
                case GOD:
                    god = true;

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                                allPlayers.sendMessage(Msg.noticeMsg("","10 seconds of god mode remaining!"));
                            }
                        }
                    }.runTaskLater(ISOCore.getInstance(), 60 * 20);

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                           for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                               allPlayers.sendMessage(Msg.noticeMsg("", "God mode disabled!"));
                           }
                           god = false;
                        }
                    }.runTaskLater(ISOCore.getInstance(), 60 * 20);
                    break;
                case HEAL:
                    for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                        allPlayers.setHealth(20.0);
                    }
                    break;
                case LOL:
                    for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                        allPlayers.setHealth(allPlayers.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue() + 1);
                    }
                    break;
                case LUCK:
                    for (Player allPlayers : Bukkit.getOnlinePlayers()) {
                        allPlayers.addPotionEffect(new PotionEffect(PotionEffectType.LUCK, 3 * 20, 1));
                    }
                    break;
                case MONEYBOOSTER:
                    break;
                case ROCKET:
                    Vector velocity = target.getVelocity();
                    velocity.add(new Vector(0, 15.5, 0));
                    target.setVelocity(velocity);

                    rocket.add(player);
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            rocket.remove(target);
                        }
                    }.runTaskLater(ISOCore.getInstance(), 10 * 20);
                    break;
                case SLAP:
                    Vector velocity2 = target.getVelocity();
                    velocity2.add(new Vector(0, 1.5, 0));
                    target.setVelocity(velocity2);
                    break;
                case SMITE:
                    target.getWorld().strikeLightningEffect(target.getLocation());
                    break;
                case UMBRELLA:
                    player.getWorld().setStorm(false);
                    player.getWorld().setWeatherDuration(120 * 20);
                    break;
                case XPBOOSTER:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}