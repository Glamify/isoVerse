package net.isoverse.isocore.vanish;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import org.bson.conversions.Bson;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

import static com.mongodb.client.model.Updates.set;

public class VanishManager {

    private static ArrayList<Player> vanish = new ArrayList<>();
    public static ArrayList<Player> getVanish() {
        return vanish;
    }

    public static void setVanishState(Player player, boolean status) {
        PlayerData playerData = new PlayerData(Filters.eq("uuid", String.valueOf(player.getUniqueId())));

        Bson update = set("vanished", status);
        playerData.update(update);

        if (status) {
            player.setMetadata("vanished", new FixedMetadataValue(ISOCore.getInstance(), true));
            getVanish().add(player);
        } else {
            player.removeMetadata("vanished", ISOCore.getInstance());
            getVanish().remove(player);
        }

        for (Player target : Bukkit.getOnlinePlayers()) {
            if (status) {
                target.hidePlayer(ISOCore.getInstance(), player);
            } else {
                target.showPlayer(ISOCore.getInstance(), player);
            }
        }

    }

    public static boolean getVanishState(Player player) {
        PlayerDataWrapper[] playerFile = new PlayerData(Filters.eq("uuid", String.valueOf(player.getUniqueId()))).get();

        if (playerFile[0].isVanished()) {
            return true;
        }
        return false;
    }

}
