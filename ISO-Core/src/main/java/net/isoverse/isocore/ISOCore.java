package net.isoverse.isocore;

import net.isoverse.isocore.chat.commands.*;
import net.isoverse.isocore.chat.listener.ChatJoinListener;
import net.isoverse.isocore.chat.listener.ChatQuitMessage;
import net.isoverse.isocore.chat.listener.CommandPluginMessageListener;
import net.isoverse.isocore.chat.listener.TextChatListener;
import net.isoverse.isocore.chat.mail.commands.MailCommand;
import net.isoverse.isocore.chat.mail.commands.SendMailCommand;
import net.isoverse.isocore.chat.nicknames.commands.NicknameCommand;
import net.isoverse.isocore.panels.commands.AdministratorPanelCommand;
import net.isoverse.isocore.panels.commands.BuilderPanelCommand;
import net.isoverse.isocore.panels.commands.DeveloperPanelCommand;
import net.isoverse.isocore.panels.commands.ModeratorPanelCommand;
import net.isoverse.isocore.playerdata.events.MongoPlayerJoinEvent;
import net.isoverse.isocore.queue.QueueCommand;
import net.isoverse.isocore.security.commands.InappropriateNameCommand;
import net.isoverse.isocore.spells.command.*;
import net.isoverse.isocore.spells.listeners.SpellPluginMessageListener;
import net.isoverse.isocore.stats.commands.AddXPCommand;
import net.isoverse.isocore.stats.commands.SetLevelCommand;
import net.isoverse.isocore.stats.commands.XPCommand;
import net.isoverse.isocore.vanish.commands.VanishCommand;
import net.isoverse.isocore.jda.JDAManager;
import net.isoverse.isocore.mongo.MongoManager;
import net.isoverse.isocore.tickets.commands.Ticket;
import net.isoverse.isocore.tickets.listeners.TicketChatListener;
import net.isoverse.isocore.utills.listeners.InventoryClickListener;
import net.isoverse.isocore.vanish.events.VanishJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;


public class ISOCore extends JavaPlugin {

    private static ISOCore instance;

    public void registerCommands() {
        /*
         * Security
         */
        getCommand("inappropriatename").setExecutor(new InappropriateNameCommand());
        /*
         * Panels
         */
        getCommand("administrator").setExecutor(new AdministratorPanelCommand());
        getCommand("moderator").setExecutor(new ModeratorPanelCommand());
        getCommand("developer").setExecutor(new DeveloperPanelCommand());
        getCommand("builder").setExecutor(new BuilderPanelCommand());
        /*
         * Levels & XP
         */
        getCommand("setlevel").setExecutor(new SetLevelCommand());
        getCommand("addxp").setExecutor(new AddXPCommand());
        getCommand("xp").setExecutor(new XPCommand());
        /*
         * Spells
         */
        getCommand("blind").setExecutor(new BlindCommand());
        getCommand("cry").setExecutor(new CryCommand());
        getCommand("eyeroll").setExecutor(new EyeRollCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("frost").setExecutor(new FrostCommand());
        getCommand("god").setExecutor(new GodCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("lol").setExecutor(new LolCommand());
        getCommand("luck").setExecutor(new LuckCommand());
        getCommand("moneybooster").setExecutor(new MoneyBoosterCommand());
        getCommand("rocket").setExecutor(new RocketCommand());
        getCommand("slap").setExecutor(new SlapCommand());
        getCommand("smite").setExecutor(new SmiteCommand());
        /*
         * Chat
         */
        getCommand("rawglobal").setExecutor(new RawGlobalCommand());
        getCommand("commandexecute").setExecutor(new CommandExecuteCommand());
        getCommand("consoleexecute").setExecutor(new ConsoleExecuteCommand());
        getCommand("ignore").setExecutor(new IgnoreCommand());
        getCommand("nickname").setExecutor(new NicknameCommand());
        getCommand("message").setExecutor(new MessageCommand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("mail").setExecutor(new MailCommand());
        getCommand("sendmail").setExecutor(new SendMailCommand());
        /*
         * Tickets
         */
        getCommand("ticket").setExecutor(new Ticket());
        /*
         * Queue
         */
        getCommand("queue").setExecutor(new QueueCommand());
        /*
         * Vanish
         */
        getCommand("vanish").setExecutor(new VanishCommand());
    }

    public void registerEvents() {
        /*
         * BungeeCord Channel
         */
        getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        /*
         * Chat
         */
        getServer().getMessenger().registerOutgoingPluginChannel(this, "iso:chat");
        getServer().getMessenger().registerIncomingPluginChannel(this, "iso:chat", new CommandPluginMessageListener());
        getServer().getPluginManager().registerEvents(new TextChatListener(), this);
        getServer().getPluginManager().registerEvents(new ChatJoinListener(), this);
        getServer().getPluginManager().registerEvents(new ChatQuitMessage(), this);

        /*
         * Tickets
         */
        getServer().getPluginManager().registerEvents(new TicketChatListener(), this);

        /*
         * Utils
         */
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
        /*
         * Vanish
         */
        getServer().getPluginManager().registerEvents(new VanishJoinEvent(), this);
        /*
        * Playerdata
        */
        getServer().getPluginManager().registerEvents(new MongoPlayerJoinEvent(), this);
        /*
        * Spells
        */
        getServer().getMessenger().registerOutgoingPluginChannel(this, "iso:spells");
        getServer().getMessenger().registerIncomingPluginChannel(this, "iso:spells", new SpellPluginMessageListener());
    }

    @Override
    public void onEnable() {
        setInstance(this);
        registerCommands();
        registerEvents();
        new JDAManager().registerJDA();
        MongoManager.init();
        MongoManager.add();

    }

    @Override
    public void onDisable(){

    }

    public static ISOCore getInstance() {
        return instance;
    }

    private static void setInstance(ISOCore instance) {
        ISOCore.instance = instance;
    }

}