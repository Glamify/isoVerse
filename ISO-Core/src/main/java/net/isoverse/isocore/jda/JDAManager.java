package net.isoverse.isocore.jda;


import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.isoverse.isocore.ISOCore;

import javax.security.auth.login.LoginException;
import java.util.EnumSet;

public class JDAManager {

    private static JDA client;
    private ISOCore plugin = ISOCore.getInstance();

    public String getDiscordToken() {
        String discordToken = plugin.getConfig().getString("DISCORD_TOKEN");
        if (discordToken == null) {
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            plugin.getLogger().severe("Please add a discord bot token in the config.yml");
            return null;
        }
        return discordToken;
    }

    public static JDA getClient() {
        return client;
    }

    public void registerJDA() {
        EnumSet<GatewayIntent> intents = EnumSet.of(

                GatewayIntent.GUILD_MESSAGES,
                GatewayIntent.GUILD_VOICE_STATES,
                GatewayIntent.GUILD_MEMBERS,
                GatewayIntent.GUILD_BANS,
                GatewayIntent.GUILD_WEBHOOKS,
                GatewayIntent.GUILD_INVITES,
                GatewayIntent.GUILD_PRESENCES,
                GatewayIntent.GUILD_EMOJIS,
                GatewayIntent.GUILD_MESSAGE_REACTIONS,
                GatewayIntent.GUILD_MESSAGE_TYPING,
                GatewayIntent.DIRECT_MESSAGES,
                GatewayIntent.DIRECT_MESSAGE_REACTIONS,
                GatewayIntent.DIRECT_MESSAGE_TYPING
        );

        try {
            client = JDABuilder.create(getDiscordToken(), intents).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }

}
