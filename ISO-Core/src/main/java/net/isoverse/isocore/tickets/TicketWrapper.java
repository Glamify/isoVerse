package net.isoverse.isocore.tickets;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.mongo.MongoManager;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TicketWrapper {
    public String uuid;//players uuid
    @BsonProperty("playeridnum")
    public String playerIdNum;//id of players total tickets, get by looping through all the players tickets start at 0 or 1? //zero or 1 can you call? alright
    @BsonProperty("serveridnum")
    public String serverIdNum;//id for total server tickets, get by size of ticket collection at time of creation
    public String status = "open";
    public String claimed = "unclaimed";
    public String content;//message player sends to create a ticket
    public String category;//chosen by player durring creation
    public List<String> comments = new ArrayList<String>();
    public String flagged = "false";
    public String server;//no default, server the player is on when creating a ticket
    public Map<String, Object> location;//no default here, location of ticket creation, coordinates in game
    @BsonProperty("discchannelid")
    public String discChannelId = "none";

    public static TicketWrapper createTicket(Player player, String category, String content){
        TicketWrapper ticket = new TicketWrapper();
        ticket.uuid = String.valueOf(player.getUniqueId());
        ticket.server = ISOCore.getInstance().getConfig().getString("SERVER");
        ticket.location = player.getLocation().serialize();
        ticket.category = category;
        ticket.content = content;
        ticket.playerIdNum = String.valueOf(TicketMongoFunctions.getTicketsLength(Filters.eq("uuid", ticket.uuid)) + 1);
        ticket.serverIdNum = String.valueOf(MongoManager.getTickets().countDocuments() + 1);
        return ticket;
    }
}
