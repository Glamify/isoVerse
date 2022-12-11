package net.isoverse.isocore.mongo;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.chat.mail.MailWrapper;
import net.isoverse.isocore.chat.nicknames.NickRequestWrapper;
import net.isoverse.isocore.chat.nicknames.NicknameWrapper;
import net.isoverse.isocore.playerdata.OldPlayerDataWrapper;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.playerdata.SpellWrapper;
import net.isoverse.isocore.security.InappropriateNameWrapper;
import net.isoverse.isocore.stats.StatsManager;
import net.isoverse.isocore.tickets.TicketWrapper;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Updates.set;

//https://github.com/mongodb/mongo-java-driver-reactivestreams/blob/master/examples/tour/src/main/tour/SubscriberHelpers.java
public class MongoManager {

    private static MongoCollection<TicketWrapper> tickets;
    private static MongoCollection<PlayerDataWrapper> playerData;
//    public static MongoCollection<OldPlayerDataWrapper> oldPlayerData;
    private static MongoCollection<NickRequestWrapper> nickRequests;
    private static MongoCollection<InappropriateNameWrapper> InappropriateNames;

    public static void init() {

        ConnectionString connString = new ConnectionString(
                "mongodb://iso:Rs8gYGdLf7aQX6DFoUxezpVX@127.0.0.1:27017/?authSource=admin&readPreference=primary&ssl=false&directConnection=true"
        );
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()
                ));

        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient mongoClient = MongoClients.create(settings);
        MongoDatabase database = mongoClient.getDatabase("IsoGlobalData").withCodecRegistry(pojoCodecRegistry);
        tickets = database.getCollection("Tickets", TicketWrapper.class);
        playerData = database.getCollection("PlayerData", PlayerDataWrapper.class);
//        oldPlayerData = database.getCollection("OldPlayerData", OldPlayerDataWrapper.class);
        nickRequests = database.getCollection("NicknameRequests", NickRequestWrapper.class);
        InappropriateNames = database.getCollection("InappropriateNames", InappropriateNameWrapper.class);

    }


    public static MongoCollection<TicketWrapper> getTickets() {
        return tickets;
    }

    public static MongoCollection<PlayerDataWrapper> getPlayerData() {
        return playerData;
    }

    public static MongoCollection<NickRequestWrapper> getNickRequests() {
        return nickRequests;
    }

    public static MongoCollection<InappropriateNameWrapper> getInappropriateNames() {
        return InappropriateNames;
    }

    public static void add() {
        //convert();
        playerData.updateMany(Filters.empty(), Updates.unset("reply"));
        playerData.updateMany(Filters.empty(), set("replyingTo", "none"));
        playerData.updateMany(Filters.empty(), set("vanished", false));
    }

    /*
    public static void convert() {
        FindIterable<OldPlayerDataWrapper> iterable = oldPlayerData.find(Filters.empty());
        for (OldPlayerDataWrapper player : iterable) {
            try {
                List<MailWrapper> mail = new ArrayList<>();
                List<String> blank = new ArrayList<>();
                List<NicknameWrapper> nicknames = new ArrayList<>();

                PlayerDataWrapper playerDataWrapper = new PlayerDataWrapper(
                        player.uuid,
                        player.ign,
                        player.ignLower,
                        Long.valueOf(Math.max(0, Math.round(Double.parseDouble(player.joined)))),
                        "Offline",
                        Long.valueOf(Math.max(0, Math.round(Double.parseDouble(player.seen)))),
                        Long.valueOf(Math.max(0, Math.round(player.playTime))),
                        player.server,
                        0,
                        player.levelColor,
                        StatsManager.getLevelData(player.xp.intValue()).getLevel(),
                        player.xp.intValue(),
                        0,
                        player.rewardsDay.longValue(),
                        Boolean.valueOf(player.hasLinked),
                        player.discId,
                        player.linkCode,
                        "None",
                        false,
                        blank,
                        mail,
                        blank,
                        nicknames,
                        new SpellWrapper(player.rollEyes, player.lol, player.smite, player.rocket, player.blind, player.umbrella, player.luck, player.slap, player.frost, player.fly, player.god, player.heal, player.cry, player.xpBooster, player.survivalMoneyBooster, 0)
                );
                MongoManager.getPlayerData().insertOne(playerDataWrapper);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

     */

}
