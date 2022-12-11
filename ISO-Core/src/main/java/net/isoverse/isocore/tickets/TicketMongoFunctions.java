package net.isoverse.isocore.tickets;

import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.jda.JDAManager;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import org.bson.conversions.Bson;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;


public class TicketMongoFunctions {

    static FileConfiguration config = ISOCore.getInstance().getConfig();

    public static long getTicketsLength(Bson filter){
        return MongoManager.getTickets().countDocuments(filter);
    }

    public static TicketWrapper[] getTickets(Bson filter) {
        FindIterable<TicketWrapper> iterable;
        if(filter !=null ){
            iterable = MongoManager.getTickets().find(filter);
        }else{
            iterable = MongoManager.getTickets().find();
        }

        List<TicketWrapper> list = new ArrayList<TicketWrapper>();
        for(TicketWrapper ticket : iterable) {
            list.add(ticket);
        }
        return list.toArray(new TicketWrapper[0]);
    }

    public static void updateTicket(Bson filter, Bson parameterToChange){
        MongoManager.getTickets().findOneAndUpdate(filter, parameterToChange);
    }

    public static void addTicket(TicketWrapper ticket) {

        PlayerDataWrapper[] player = new PlayerData(Filters.eq("uuid", String.valueOf(ticket.uuid))).get();

        String ign = player[0].getIgn();

        Location location = Location.deserialize(ticket.location);

        EmbedBuilder eb = new EmbedBuilder();

        eb.setTitle("New Ticket", null);

        eb.setColor(Color.CYAN);

        eb.setAuthor("isoBot", "https://isoVerse.net", "https://isoverse.net/images/logo-square-transparent.png");

        eb.setDescription(ign + " created a new ticket");

        eb.addField("Message:", ticket.content, false);
        eb.addField("Player ID: ", ticket.playerIdNum, true);
        eb.addField("Server ID:", ticket.serverIdNum, true);
        eb.addField("Status: ", ticket.status, true);
        eb.addField("Category: ", ticket.category, true);
        eb.addField("Server: ", ticket.server, true);
        eb.addField("World: ", location.getWorld().toString(), false);
        eb.addField("X: ", String.valueOf(location.getX()), true);
        eb.addField("Y: ", String.valueOf(location.getY()), true);
        eb.addField("Z: ", String.valueOf(location.getZ()), true);
        eb.addField(" ", "Please utilize https://www.youtube.com/ or https://imgur.com/ to upload videos or images.", false);

        Guild guild = JDAManager.getClient().getGuildById(Objects.requireNonNull(config.getString("GUILD_ID")));

        assert guild != null;

        if(!player[0].getDiscordLink().equalsIgnoreCase("unlinked")){

            User user = JDAManager.getClient().getUserById(player[0].getDiscordLink());
            assert user != null;
            Member member = guild.getMember(user);

            assert member != null;

            PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid",ticket.uuid)).get();

            guild.createTextChannel(playerData[0].getIgn())
                    .setParent(guild.getCategoryById(Objects.requireNonNull(config.getString("TICKET_CATEGORY"))))
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("887730280755003422")), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("887730192930451577")), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("1022856158815461407")), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .addPermissionOverride(member, EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .queue(channel -> {
                        channel.sendMessage("@here").queue();
                        channel.sendMessageEmbeds(eb.build()).queue();
                        ticket.discChannelId = channel.getId();
                        MongoManager.getTickets().insertOne(ticket);
                    });
        } else {
            PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid",ticket.uuid)).get();

            guild.createTextChannel(playerData[0].getIgn())
                    .setParent(guild.getCategoryById(Objects.requireNonNull(config.getString("TICKET_CATEGORY"))))
                    .addPermissionOverride(guild.getPublicRole(), null, EnumSet.of(Permission.VIEW_CHANNEL))
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("887730280755003422")), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("887730192930451577")), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .addPermissionOverride(Objects.requireNonNull(guild.getRoleById("1022856158815461407")), EnumSet.of(Permission.VIEW_CHANNEL, Permission.MESSAGE_SEND, Permission.MESSAGE_HISTORY), null)
                    .queue(channel -> {
                        channel.sendMessage("@here").queue();
                        channel.sendMessageEmbeds(eb.build()).queue();
                        ticket.discChannelId = channel.getId();
                        MongoManager.getTickets().insertOne(ticket);
                    });
        }
    }

}
