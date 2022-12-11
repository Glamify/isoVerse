package net.isoverse.isocore.playerdata;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.isoverse.isocore.mongo.MongoManager;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class PlayerData {

    Bson filter;

    public PlayerData(Bson filter) {
        this.filter = filter;
    }

    public PlayerDataWrapper[] get() {
        FindIterable<PlayerDataWrapper> iterable;
        if (this.filter != null ) {
            iterable = MongoManager.getPlayerData().find(this.filter);
        } else {
            iterable = MongoManager.getPlayerData().find();
        }

        List<PlayerDataWrapper> list = new ArrayList<>();
        for(PlayerDataWrapper playerFile : iterable) {
            list.add(playerFile);
        }

        return list.toArray(new PlayerDataWrapper[0]);
    }

    //main function to update a player file
    public void update(Bson parameterToChange){
        MongoManager.getPlayerData().findOneAndUpdate(this.filter, parameterToChange);
    }

    public void replace(PlayerDataWrapper playerFile) {
        MongoManager.getPlayerData().findOneAndReplace(this.filter, playerFile);
    }

    public void delete() {
        MongoManager.getPlayerData().deleteOne(this.filter);
    }
}
