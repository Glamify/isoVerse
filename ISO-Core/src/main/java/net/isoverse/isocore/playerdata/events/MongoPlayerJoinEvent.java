package net.isoverse.isocore.playerdata.events;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.chat.mail.MailWrapper;
import net.isoverse.isocore.chat.nicknames.NickRequestWrapper;
import net.isoverse.isocore.chat.nicknames.NicknameWrapper;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.playerdata.SpellWrapper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class MongoPlayerJoinEvent implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void joinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerDataWrapper[] playerFile = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString())).get();

        List<String> ranks = new ArrayList<>();
        if (player.hasPermission("iso.owner")) {
            ranks.add("Owner");
        }
        if (player.hasPermission("iso.admin")) {
            ranks.add("Administrator");
        }
        if (player.hasPermission("iso.dev")) {
            ranks.add("Developer");
        }
        if (player.hasPermission("iso.mod")) {
            ranks.add("Moderator");
        }
        if (player.hasPermission("iso.builder")) {
            ranks.add("Builder");
        }
        if (player.hasPermission("iso.elite")) {
            ranks.add("Elite");
        }
        if (player.hasPermission("iso.titan")) {
            ranks.add("Titan");
        }
        if (player.hasPermission("iso.ultra")) {
            ranks.add("Ultra");
        }
        if (player.hasPermission("iso.hero")) {
            ranks.add("Hero");
        }
        if (player.hasPermission("iso.pro")) {
            ranks.add("Pro");
        }
        ranks.add("Member");

        if (playerFile.length == 0) {

            List<MailWrapper> mail = new ArrayList<>();
            List<String> blocked = new ArrayList<>();
            List<NicknameWrapper> nicknames = new ArrayList<>();

            try {
                PlayerDataWrapper playerDataWrapper = new PlayerDataWrapper(
                        player.getUniqueId().toString(),
                        player.getName(),
                        player.getName().toLowerCase(),
                        System.currentTimeMillis(),
                        "Online",
                        System.currentTimeMillis(),
                        0,
                        ISOCore.getInstance().getConfig().getString("SERVER"),
                        0,
                        "&8",
                        0,
                        0,
                        0,
                        0,
                        false,
                        "unlinked",
                        "unlinked",
                        null,
                        false,
                        ranks,
                        mail,
                        blocked,
                        nicknames,
                        new SpellWrapper(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0)

                );
                MongoManager.getPlayerData().insertOne(playerDataWrapper);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
