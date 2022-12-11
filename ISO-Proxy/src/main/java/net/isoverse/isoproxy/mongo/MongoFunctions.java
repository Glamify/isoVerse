package net.isoverse.isoproxy.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.security.InappropriateNameWrapper;
import net.isoverse.isoproxy.utills.Msg;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//main class for running all connections to the mongo Database
public class MongoFunctions {

    //set base collection variables for playerData and chatGroups collections in the database
    public static MongoCollection<PlayerDataWrapper> playerData;
    public static MongoCollection<InappropriateNameWrapper> inappropriateNames;

    //function to initalize the DB connection, call in the "onEnable" function in "isoChat.java"
    public static void init(){

        //set the connection string for DB connections
        ConnectionString connString = new ConnectionString("mongodb://iso:Rs8gYGdLf7aQX6DFoUxezpVX@127.0.0.1:27017/?authSource=admin&readPreference=primary&ssl=false&directConnection=true");

        //set codec registry for converting mongo documents into POJO's
        CodecRegistry pojoCodecRegistry = org.bson.codecs.configuration.CodecRegistries.fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()
                ));

        //apply the connection string and codecs to the connection settings and build the settings package
        MongoClientSettings settings = MongoClientSettings.builder()
                .applyConnectionString(connString)
                .retryWrites(true)
                .codecRegistry(pojoCodecRegistry)
                .build();

        //activate connection to the db and create a mongo client for all mongo functions
        MongoClient mongoClient = MongoClients.create(settings);

        //get the correct database from inside the mongo client
        MongoDatabase database = mongoClient.getDatabase("IsoGlobalData").withCodecRegistry(pojoCodecRegistry);

        //define the playerData and chatGroups collections from within the database
        playerData = database.getCollection("PlayerData", PlayerDataWrapper.class);
        inappropriateNames = database.getCollection("InappropriateNames", InappropriateNameWrapper.class);

    }

    public static MongoCollection<InappropriateNameWrapper> getInappropriateNames() {
        return inappropriateNames;
    }

    //function to get a player file or files from the database
    public static PlayerDataWrapper[] getPlayerFile(Bson filter) {

        //set base variable for player file
        FindIterable<PlayerDataWrapper> iterable;

        //see if the result is being filtered
        if(filter != null ){

            //find one document based on the filter: example (Filters.eq("uuid", "1312461543724"))
            iterable = playerData.find(filter);
        }else{

            //grab every playerData document in the collection
            iterable = playerData.find();
        }

        //set final readable variable for player file
        List<PlayerDataWrapper> list = new ArrayList<PlayerDataWrapper>();
        for(PlayerDataWrapper playerFile : iterable) {
            list.add(playerFile);
        }

        //return an array of files that can be read by java functions
        return list.toArray(new PlayerDataWrapper[0]);
    }

    public static void replace(Bson filter, PlayerDataWrapper playerFile) {
        playerData.findOneAndReplace(filter, playerFile);
    }



}