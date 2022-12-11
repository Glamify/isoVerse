package net.isoverse.isocore.tickets.commands;


import com.mongodb.client.model.Filters;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.isoverse.isocore.ISOCore;
import net.isoverse.isocore.jda.JDAManager;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.tickets.TicketMongoFunctions;
import net.isoverse.isocore.tickets.TicketWrapper;
import net.isoverse.isocore.utills.GUIWrapper;
import net.isoverse.isocore.utills.Msg;
import org.bson.conversions.Bson;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.io.IOException;
import java.util.*;

import static com.mongodb.client.model.Updates.set;
import static net.isoverse.isocore.utills.Msg.ellipsis;

public class Ticket implements CommandExecutor {
    public static Map<UUID, String> waitingForContent = new HashMap<UUID,String>(); //uuid of player, reason
    public static Map<UUID, String> waitingForContentReply = new HashMap<UUID,String>(); //uuid of player, ticket is

    public static void onContentRecieved(Player player, String content) throws IOException { //then run command
        TicketWrapper ticket = TicketWrapper.createTicket(player, waitingForContent.get(player.getUniqueId()), content);
        TicketMongoFunctions.addTicket(ticket);
        player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + " Your ticket was submitted. " + ChatColor.GRAY + "Use " + ChatColor.WHITE + "/ticket list" + ChatColor.GRAY + " to check for replies, or check "  + ChatColor.WHITE + "/discord" + ChatColor.GRAY + ".");

        waitingForContent.remove(player.getUniqueId());
    }

    public static void onContentRecievedReply(Player player, String content) throws IOException { //then run command
        String ticketId = waitingForContentReply.get(player.getUniqueId());

        TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", ticketId));

        List<String> comments = new ArrayList<>();

        if(!(ticket[0].comments.size() == 0)){
            for(int i = 0; i < ticket[0].comments.size(); i++) {
                comments.add(ticket[0].comments.get(i));
            }
        }

        comments.add(content);

        Bson update = set("comments", comments);

        TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", ticketId), update);

        Guild iso = JDAManager.getClient().getGuildById(Objects.requireNonNull(ISOCore.getInstance().getConfig().getString("GUILD_ID")));

        assert iso != null;
        TextChannel channel = iso.getTextChannelById(ticket[0].discChannelId);

        assert channel != null;
        channel.sendMessage(content + " (Sent by: " + player.getName() + ")").queue();

        player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + " Your ticket reply was submitted. " + ChatColor.GRAY + "Use " + ChatColor.WHITE + "/ticket list" + ChatColor.GRAY + " to check for replies.");

        waitingForContentReply.remove(player.getUniqueId());
    }


    public static void startWaitForContent(Player player, String category){

        waitingForContent.put(player.getUniqueId(),category);
        player.sendMessage(Msg.noticeMsg("Type your message in chat to submit your ticket", ""));
    }

    public static void startWaitForContentReply(Player player, String ticketId){
        waitingForContentReply.put(player.getUniqueId(), ticketId);
        player.sendMessage(Msg.noticeMsg("Type your message in chat to submit your ticket", ""));
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //make sure the command sender is a player, not console
        if(sender instanceof Player player) {

            //test for args, if none open main gui
            if (args.length == 0) {
                //create base gui for /ticket with no args
                GUIWrapper gui = new GUIWrapper(player, 1, "Tickets", Material.BLACK_STAINED_GLASS_PANE);


                gui.addButton((clickType) -> {

                    player.performCommand("ticket create");
                }, Material.CRAFTING_TABLE, ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Create a Ticket", new String[]{ChatColor.WHITE + "Were you griefed? Need help",ChatColor.WHITE + "from a staff member?"},3);

                gui.addButton((clickType) -> {
                    player.performCommand("ticket list");
                }, Material.WRITABLE_BOOK, ChatColor.AQUA + "" + ChatColor.UNDERLINE + "List My Tickets", new String[]{ChatColor.WHITE + "Check for updates on your",ChatColor.WHITE + "existing ticket or add a reply."}, 5);
                return true;
            }

            //create a new ticket
            if (args[0].equalsIgnoreCase("create")) {
                GUIWrapper gui = new GUIWrapper(player, 1, "New Ticket", Material.BLACK_STAINED_GLASS_PANE);

                gui.addButton((clickType) -> {
                    startWaitForContent(player,"grief");
                    gui.close();

                }, Material.TNT,  ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Grief Report",
                        new String[]{ChatColor.GRAY + "My personal property was",
                                ChatColor.GRAY + "damaged or stolen.",
                                " ",
                                ChatColor.RED + "Stand directly at the grief",
                                ChatColor.RED + "and look at it before submitting!",
                                " ",
                                ChatColor.GREEN + "Click to submit only if you ",
                                ChatColor.GREEN + "are standing at the grief."},1);

                gui.addButton((clickType) -> {
                    startWaitForContent(player,"question");
                    gui.close();

                }, Material.REDSTONE_TORCH, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Question",
                        new String[]{ChatColor.GRAY + "I have a question about isoVerse",
                                " ",
                                ChatColor.RED + "You will get a more prompt",
                                ChatColor.RED + "answer on our " + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "/discord" + ChatColor.RESET + " " + ChatColor.RED + "server!",
                                " ",
                                ChatColor.GREEN + "Click to submit your question."}, 2);

                gui.addButton((clickType) -> {
                    startWaitForContent(player,"purchase");
                            gui.close();

                }, Material.SUNFLOWER, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Store Purchase Problem",
                        new String[]{ChatColor.GRAY + "My isoVerse store purchase",
                                ChatColor.GRAY + "(rank/crate) did not activate.",
                                " ",
                                ChatColor.RED + "Include exact time of purchase,",
                                ChatColor.RED + "amount spent, and item bought.",
                                " ",
                                ChatColor.GREEN + "Click to submit and include all",
                                ChatColor.GREEN + "of the details listed above."}, 3);

                gui.addButton((clickType) -> {
                    startWaitForContent(player,"bug");
                            gui.close();
                }, Material.LAVA_BUCKET, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Bug Report",
                        new String[]{ChatColor.GRAY + "I found a glitch or problem",
                                ChatColor.GRAY + "with a command or feature.",
                                " ",
                                ChatColor.RED + "Include exactly what you did",
                                ChatColor.RED + "and the full error message.",
                                " ",
                                ChatColor.GREEN + "Click to submit and include all",
                                ChatColor.GREEN + "of the details listed above."}, 4);

                gui.addButton((clickType) -> {
                    startWaitForContent(player,"feature");
                            gui.close();
                }, Material.PAINTING, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Feature Request",
                        new String[]{ChatColor.GRAY + "I have an idea that may",
                                ChatColor.GRAY + "help improve isoVerse.",
                                " ",
                                ChatColor.GREEN + "Click to submit your idea."}, 5);

                gui.addButton((clickType) -> {
                    startWaitForContent(player,"report");
                            gui.close();
                }, Material.POISONOUS_POTATO, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Report a Player",
                        new String[]{ChatColor.GRAY + "Another player was",
                                ChatColor.GRAY + "breaking the " + ChatColor.GREEN + "" + ChatColor.UNDERLINE + "/rules",
                                " ",
                                ChatColor.RED + "Include the full username",
                                ChatColor.RED + "exactly what they did, when",
                                ChatColor.RED + "it happened, and the rule broken.",
                                " ",
                                ChatColor.GREEN + "Click to submit and include all",
                                ChatColor.GREEN + "of the details listed above."}, 6);

                gui.addButton((clickType) -> {
                            startWaitForContent(player,"other");
                            gui.close();
                        }, Material.ANVIL, ChatColor.AQUA +  "" + ChatColor.UNDERLINE + "Other",
                        new String[]{ChatColor.GRAY + "My request doesn't fit",
                                ChatColor.GRAY + "into any other category.",
                                " ",
                                ChatColor.GREEN + "Click to submit a ticket.",}, 7);
                return true;
            }

            //length for lore
            int trunacateLength = 25;

            //pull a list of tickets  (staff all tickets pulled, non staff only their tickets pulled)
            if(args[0].equalsIgnoreCase("list")){
                if(player.hasPermission("iso.mod")){
                    //is staff so pull all tickets
                    TicketWrapper[] tickets = TicketMongoFunctions.getTickets(Filters.eq("status","open"));

                    double pages = 1;

                    if(tickets.length > 54){
                        pages = (tickets.length / 54);
                        if(pages < Math.round(pages) + .5){
                            pages = Math.round(pages) + 1;
                        } else {
                            pages = Math.round(pages);
                        }
                    }


                    GUIWrapper gui = new GUIWrapper(player, 6,"Open Tickets", Material.BLACK_STAINED_GLASS_PANE,(int) pages);
                    for(int i = 0; i < tickets.length; i++){
                        String[] lore;

                        if(tickets[i].comments.size()==0){

                            PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", tickets[i].uuid)).get();


                            lore = new String[3];

                            lore[0] = ChatColor.GRAY + "Player: " + ChatColor.WHITE + playerData[0].getIgn();
                            lore[1] = ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(tickets[i].content,trunacateLength);
                            lore[2] = ChatColor.GRAY + "Category: " + ChatColor.WHITE + tickets[i].category;

                        }else if(tickets[i].comments.size() < 3){

                            PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", tickets[i].uuid)).get();

                            lore = new String[tickets[i].comments.size()+4];

                            lore[0] = ChatColor.GRAY + "Player: " + ChatColor.WHITE + playerData[0].getIgn();
                            lore[1] = ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(tickets[i].content,trunacateLength);
                            lore[2] = ChatColor.GRAY + "Category: " + ChatColor.WHITE + tickets[i].category;
                            lore[3] = ChatColor.GRAY + "Last comments: ";
                            for(int j = 0; j < tickets[i].comments.size(); j++){
                                lore[j+4] = ChatColor.DARK_AQUA + " " + ellipsis(tickets[i].comments.get(j),trunacateLength);
                            }

                        }else{

                            PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", tickets[i].uuid)).get();

                            lore = new String[7];

                            lore[0] = ChatColor.GRAY + "Player: " + ChatColor.WHITE + playerData[0].getIgn();
                            lore[1] = ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(tickets[i].content,trunacateLength);
                            lore[2] = ChatColor.GRAY + "Category: " + ChatColor.WHITE + tickets[i].category;
                            lore[3] = ChatColor.GRAY + "Last comments: ";
                            for(int j = 0; j < 3; j++){
                                lore[j+4] = ChatColor.DARK_AQUA + " " + ellipsis(tickets[i].comments.get(tickets[i].comments.size()-(3-j)),trunacateLength);
                            }
                        }
                        int finalI = i;
                        gui.addButton((clickType) -> {
                            player.performCommand("ticket info " + tickets[finalI].serverIdNum);
                        }, Material.PAPER, ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Ticket #" + tickets[i].serverIdNum, lore, i);
                    }
                    return true;
                }

                //they are not staff so pull only their tickets
                TicketWrapper[] tickets = TicketMongoFunctions.getTickets(Filters.eq("uuid",player.getUniqueId().toString()));

                double pages = 1;

                if(tickets.length > 54){
                    pages = (tickets.length / 54);
                    if(pages < Math.round(pages) + .5){
                        pages = Math.round(pages) + 1;
                    } else {
                        pages = Math.round(pages);
                    }
                }

                GUIWrapper gui = new GUIWrapper(player, 6,"My Open Tickets", Material.BLACK_STAINED_GLASS_PANE,(int) pages);

                for(int i = 0; i < tickets.length; i++){
                    String[] lore;

                    if(tickets[i].comments.size()==0){

                        PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", tickets[i].uuid)).get();

                        lore = new String[2];

                        lore[0] = ChatColor.GRAY + "Player: " + ChatColor.WHITE + playerData[0].getIgn();
                        lore[1] = ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(tickets[i].content,trunacateLength);

                    }else if(tickets[i].comments.size() < 3){

                        PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", tickets[i].uuid)).get();

                        lore = new String[tickets[i].comments.size()+3];

                        lore[0] = ChatColor.GRAY + "Player: " + ChatColor.WHITE + playerData[0].getIgn();
                        lore[1] = ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(tickets[i].content,trunacateLength);
                        lore[2] = ChatColor.GRAY + "Last comments: ";
                        for(int j = 0; j<tickets[i].comments.size(); j++){
                            lore[j+3] = ChatColor.WHITE + ellipsis(tickets[i].comments.get(j),trunacateLength);
                        }

                    }else{

                        PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", tickets[0].uuid)).get();

                        lore = new String[6];

                        lore[0] = ChatColor.GRAY + "Player: " + ChatColor.WHITE + playerData[0].getIgn();
                        lore[1] = ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(tickets[i].content,trunacateLength);
                        lore[2] = ChatColor.GRAY + "Last comments: ";
                        for(int j = 0; j < 3; j++){
                            lore[j+3] = ChatColor.WHITE + ellipsis(tickets[i].comments.get(tickets[i].comments.size()-(3-j)),trunacateLength);
                        }
                    }
                    int finalI = i;
                    gui.addButton((clickType) -> {
                        player.performCommand("ticket info " + tickets[finalI].serverIdNum);
                    }, Material.PAPER, ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Ticket #" + tickets[i].playerIdNum, lore, i);
                } //remember to look for player vs server id numbers



                return true;
            }

            //close a ticket
            if(args[0].equalsIgnoreCase("close")){
                //grab ticket based on server id number
                TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                if(ticket.length == 0){
                    player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + " No ticket has ID #" + args[1] + ".");
                    return true;
                }

                PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", ticket[0].uuid)).get();

                //check for staff or ownership
                if (!(player.hasPermission("iso.mod")) && !(playerData[0].getIgn().equalsIgnoreCase(player.getName()))){
                    player.sendMessage("You cannot edit this ticket.");
                    return true;
                }

                player.sendMessage(Msg.successMsg("Ticket Closed.", "Ticket *#" + args[1] + "* has been closed."));

                Bson update = set("status", "closed");
                TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);

                try {
                    Guild guild = JDAManager.getClient().getGuildById(Objects.requireNonNull(ISOCore.getInstance().getConfig().getString("GUILD_ID")));

                    assert guild != null;

                    TextChannel channel = guild.getTextChannelById(ticket[0].discChannelId);

                    assert channel != null;
                    channel.delete().queue();

                } catch (NullPointerException e) {
                    return true;
                }
                return true;

            }

            //open a ticket
            if(args[0].equalsIgnoreCase("open")){
                //check for staff
                if(player.hasPermission("iso.mod")){
                    //re-open based on server id number the staff member put in
                    TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                    if(ticket.length == 0){
                        player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + " No ticket has ID #" + args[1] + ".");
                        return true;
                    }

                    Bson update = set("status", "open");

                    TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);
                    player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + " Ticket #" + args[1] + " re-opened.");
                    return true;
                }

                TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                if(ticket.length == 0){
                    player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + " No ticket has ID #" + args[1] + ".");
                    return true;
                }

                PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", ticket[0].uuid)).get();

                if(playerData[0].getIgn().equalsIgnoreCase(String.valueOf(player.name()))){
                    Bson update = set("status", "open");

                    TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);
                    player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + " Your ticket #" + args[1] + " has been re-opened.");
                    return true;
                }
            }


            //flag a ticket
            if(args[0].equalsIgnoreCase("flag")){
                //check for staff
                if(player.hasPermission("iso.mod")){
                    //close based on server id number the staff member put in
                    TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                    if(ticket.length == 0){
                        player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "No ticket has ID #" + args[1] + ".");
                        return true;
                    }

                    Bson update = set("flagged", "true");

                    TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);
                    player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + "Ticket #" + args[1] + " has been flagged.");
                    return true;
                }

                //they aren't staff so send error unknown command message
                player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "Invalid syntax. " + ChatColor.GRAY + "Type " + ChatColor.WHITE + "/help" + ChatColor.GRAY + " for help.");
                return true;
            }

            //unflag a ticket
            if(args[0].equalsIgnoreCase("unflag")){
                //check for staff
                if(player.hasPermission("iso.mod")){
                    //close based on server id number the staff member put in
                    TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                    if(ticket.length == 0){
                        player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "No ticket has ID #" + args[1] + ".");
                        return true;
                    }

                    Bson update = set("flagged", "false");

                    TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);
                    player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + "Ticket #" + args[1] + " has been un-flagged.");
                    return true;
                }

                //they aren't staff so send error unknown command message
                player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "Invalid syntax. " + ChatColor.GRAY + "Type " + ChatColor.WHITE + "/help" + ChatColor.GRAY + " for help.");
                return true;
            }

            //un-claim a ticket
            if(args[0].equalsIgnoreCase("unclaim")){
                //check for staff
                if(player.hasPermission("iso.mod")){
                    //unclaim based on server id number the staff member put in
                    TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                    if(ticket.length == 0){
                        player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "No ticket has ID #" + args[1] + ".");
                        return true;
                    }

                    //make sure they have claimed it
                    if(ticket[0].claimed.equalsIgnoreCase(player.getName())){
                        Bson update = set("claimed", "unclaimed");

                        TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);
                        player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + "Ticket #" + args[1] + " has been un-claimed.");
                        return true;
                    }

                    //if not send error message
                    player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "You can only un-claim a ticket you have claimed.");
                    return true;
                }

                //they aren't staff so send error unknown command message
                player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "Invalid syntax. " + ChatColor.GRAY + "Type " + ChatColor.WHITE + "/help" + ChatColor.GRAY + " for help.");
                return true;
            }

            //claim a ticket
            if(args[0].equalsIgnoreCase("claim")){
                //check for staff
                if(player.hasPermission("iso.mod")){
                    //unclaim based on server id number the staff member put in
                    TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));
                    if(ticket.length == 0){
                        player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "No ticket has ID #" + args[1] + ".");
                        return true;
                    }

                    //make sure they have claimed it
                    if(ticket[0].claimed.equalsIgnoreCase("unclaimed")){
                        Bson update = set("claimed", player.getName());

                        TicketMongoFunctions.updateTicket(Filters.eq("serveridnum", args[1]), update);
                        player.sendMessage(ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + "Ticket #" + args[1] + " has been claimed.");
                        return true;
                    }

                    //if not send error message
                    player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "You can only claim a ticket that isn't claimed.");
                    return true;
                }

                //they aren't staff so send error unknown command message
                player.sendMessage(ChatColor.DARK_RED + "\u2715" + ChatColor.RED + "Invalid syntax. " + ChatColor.GRAY + "Type " + ChatColor.WHITE + "/help" + ChatColor.GRAY + " for help.");
                return true;
            }

            //view info on a ticket
            if(args[0].equalsIgnoreCase("info")){
                //grab requested ticket
                TicketWrapper[] ticket = TicketMongoFunctions.getTickets(Filters.eq("serveridnum", args[1]));

                PlayerDataWrapper[] playerData = new PlayerData(Filters.eq("uuid", ticket[0].uuid)).get();

                //if player is not staff and they dont own the ticket send error message
                if(!player.hasPermission("iso.mod")){
                    if(!playerData[0].getIgn().equalsIgnoreCase(player.getName())){
                        player.sendMessage(Msg.errorMsg("You can't view information of tickets you didn't create.", ""));
                        return true;
                    }
                }
                //create gui
                GUIWrapper gui = new GUIWrapper(player, 3,"Ticket List", Material.BLACK_STAINED_GLASS_PANE);

                //if they are staff or do own the ticket then create a gui menu for that ticket
                gui.addButton((clickType) -> {
                    player.sendMessage(ChatColor.AQUA + ticket[0].content);
                }, Material.BOOK, ChatColor.AQUA + "" + ChatColor.UNDERLINE + "Ticket: #" + ticket[0].serverIdNum, new String[] {ChatColor.GRAY + "Submitted by: " + ChatColor.WHITE + playerData[0].getIgn() + " (" + ticket[0].playerIdNum + ")", ChatColor.GRAY + "Message: " + ChatColor.WHITE + ellipsis(ticket[0].content, trunacateLength), " ", ChatColor.AQUA + "Click to view full message."}, 4);

                if(ticket[0].status.equalsIgnoreCase("open")){
                    gui.addButton((clickType) -> {
                        player.performCommand("ticket close " + ticket[0].serverIdNum);
                        gui.close();
                        player.performCommand("ticket info " + ticket[0].serverIdNum);
                    }, Material.LIME_WOOL, ChatColor.WHITE + "Ticket Status: " + ChatColor.DARK_GREEN + "\u2714" + ChatColor.GREEN + " Open", new String[] {" "}, 22);
                } else {
                    gui.addButton((clickType) -> {

                    }, Material.RED_WOOL, ChatColor.WHITE + "Ticket Status: " + ChatColor.DARK_RED + "\u2715" + ChatColor.RED + " Closed", new String[] {" "}, 22);
                }

                if(ticket[0].flagged.equalsIgnoreCase("false")){
                    gui.addButton((clickType) -> {
                        player.performCommand("ticket flag " + ticket[0].serverIdNum);
                        gui.close();
                        player.performCommand("ticket info " + ticket[0].serverIdNum);
                    }, Material.LEVER, "Flagged for Moderators", new String[] {" "}, 24);
                } else {
                    gui.addButton((clickType) -> {
                        player.performCommand("ticket unflag " + ticket[0].serverIdNum);
                        gui.close();
                        player.performCommand("ticket info " + ticket[0].serverIdNum);
                    }, Material.REDSTONE_TORCH, "Flagged for Administrators", new String[] {" "}, 24);
                }

                if(ticket[0].claimed.equalsIgnoreCase("unclaimed")){
                    gui.addButton((clickType) -> {
                        player.performCommand("ticket claim " + ticket[0].serverIdNum);
                        gui.close();
                        player.performCommand("ticket info " + ticket[0].serverIdNum);
                    }, Material.BUCKET, "This ticket is not claimed.", new String[] {" "}, 26);
                } else {
                    gui.addButton((clickType) -> {
                        player.performCommand("ticket unclaim " + ticket[0].serverIdNum);
                        gui.close();
                        player.performCommand("ticket info " + ticket[0].serverIdNum);
                    }, Material.WATER_BUCKET, "This ticket is claimed by: " + ticket[0].claimed, new String[] {" "}, 26);
                }

                gui.addButton((clickType) -> {

                }, Material.HOPPER, "Ticket Category: " + ticket[0].category, new String[] {" "}, 20);

                gui.addButton((clickType) -> {
                    gui.close();

                    if (ISOCore.getInstance().getConfig().getString("SERVER").equalsIgnoreCase(ticket[0].server)){
                        player.teleport(Location.deserialize(ticket[0].location));
                        player.sendMessage("teleported to ticket location.");
                        return;
                    }
                    player.sendMessage("You are in the wrong server for this. Please go to " + ticket[0].server + " to preform this action.");

                }, Material.COMPASS, "Ticket Location: " + ticket[0].location, new String[] {"Click to teleport."}, 18);

                gui.addButton((clickType) -> {
                    player.performCommand("ticket list");
                }, Material.BARRIER, "Back", new String[] {" "}, 8);


                //set replies array
                int u = 0;
                String[] replies = new String[ticket[0].comments.size() + 2];
                for(int i = 0; i < ticket[0].comments.size(); i++){
                    replies[i] = ellipsis(ticket[0].comments.get(i), trunacateLength);
                    u = i + 1;
                }

                replies[u] = " ";
                replies[u + 1] = ChatColor.AQUA + "Left click to view replies, Right click to add a reply";

                gui.addButton((clickType) -> {

                    if(clickType == ClickType.RIGHT){

                        startWaitForContentReply(player, ticket[0].serverIdNum);

                    } else {
                        for(int i = 0; i < ticket[0].comments.size(); i++){
                            player.sendMessage(ChatColor.AQUA + ticket[0].comments.get(i));
                        }
                    }
                }, Material.MAP, "Ticket Replies: ", replies, 0);

            }

            return true;
        }
        return true;
    }

}
