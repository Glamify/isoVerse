package net.isoverse.isocore.chat.nicknames;

import com.mongodb.client.FindIterable;
import net.isoverse.isocore.mongo.MongoManager;
import org.bson.conversions.Bson;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class NickRequest {

    Bson filter;

    public NickRequest(Bson filter) {
        this.filter = filter;
    }


    public NickRequestWrapper[] get() {

        //set base variable for the group
        FindIterable<NickRequestWrapper> iterable;

        //check to see if result is being filtered
        if (this.filter != null) {

            //find group based on filter, see getPlayerFile for a filter example
            iterable = MongoManager.getNickRequests().find(this.filter);
        } else {

            //find all group files in the database
            iterable = MongoManager.getNickRequests().find();
        }

        //set variable to make readable group file
        List<NickRequestWrapper> list = new ArrayList<>();
        for (NickRequestWrapper group : iterable) {
            list.add(group);
        }

        //return readable group array
        return list.toArray(new NickRequestWrapper[0]);
    }

    public void create(Player player, long time, String name) {
        MongoManager.getNickRequests().insertOne(new NickRequestWrapper(player.getUniqueId().toString(), time, name));
    }

    public void update(Bson parameterToChange) {
        MongoManager.getNickRequests().findOneAndUpdate(this.filter, parameterToChange);
    }

    public void delete() {
        MongoManager.getNickRequests().deleteOne(this.filter);
    }

}
