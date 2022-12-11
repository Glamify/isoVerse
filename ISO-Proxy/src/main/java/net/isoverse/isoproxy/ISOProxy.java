package net.isoverse.isoproxy;

import net.isoverse.isoproxy.chat.ChatPluginMessageListener;
import net.isoverse.isoproxy.chat.ChatJoinListener;
import net.isoverse.isoproxy.chat.ChatLeaveListener;
import net.isoverse.isoproxy.chat.ChatServerSwitchListener;
import net.isoverse.isoproxy.mongo.MongoFunctions;
import net.isoverse.isoproxy.security.SecurityLoginEvent;
import net.isoverse.isoproxy.spells.SpellPluginMessageListener;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public final class ISOProxy extends Plugin {

    private static LuckPerms luckPerms;
    private static Configuration configuration;

    public void registerCommands() {
        //ProxyServer.getInstance().getPluginManager().registerCommand(this, new Mail("mail"));
    }

    public void registerEvents() {
        //Security
        getProxy().getPluginManager().registerListener(this, new SecurityLoginEvent());
        //Spells
        getProxy().getPluginManager().registerListener(this, new SpellPluginMessageListener());
        //Chat
        getProxy().getPluginManager().registerListener(this, new ChatPluginMessageListener());
        getProxy().getPluginManager().registerListener(this, new ChatJoinListener());
        getProxy().getPluginManager().registerListener(this, new ChatLeaveListener());
        getProxy().getPluginManager().registerListener(this, new ChatServerSwitchListener());
    }

    public void registerConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }

        //attempt to find the config "config.yml" in the isoChat folder
        File file = new File(getDataFolder(), "config.yml");

        //if the config does not exist create it
        if (!file.exists()) {

            //try catch for errors in creating config file
            try {

                //create a new file
                file.createNewFile();

                //load the file in as a .yml
                Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);

                //set config data, what variables will it have and their default value
                config.set("StaffSymbol", "@");
                config.set("MongoConnString", "mongodb://127.0.0.1:27017");
                config.set("PHPAPIPath", "D:/Programming/isoVerse/index.php");

                //save the config
                ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File(getDataFolder(), "config.yml"));

            } catch (IOException e){
                e.printStackTrace();
            }
        }

        //try to load in the config under the configuration variable so it can be used in any file in the plugin
        try {
            setConfiguration(ConfigurationProvider.getProvider(YamlConfiguration.class).load(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        registerConfig();
        MongoFunctions.init();
        registerCommands();
        registerEvents();
        setLuckPerms(LuckPermsProvider.get());

        getProxy().registerChannel("iso:chat");
        getProxy().registerChannel("iso:spells");
        // Plugin startup logic
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Configuration getConfiguration() {
        return configuration;
    }
    public static void setConfiguration(Configuration configuration) {
        ISOProxy.configuration = configuration;
    }

    public static LuckPerms getLuckPerms() {
        return luckPerms;
    }
    public static void setLuckPerms(LuckPerms luckPerms) {
        ISOProxy.luckPerms = luckPerms;
    }

}
