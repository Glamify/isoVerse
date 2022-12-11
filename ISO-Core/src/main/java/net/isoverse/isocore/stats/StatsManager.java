package net.isoverse.isocore.stats;

import com.mongodb.client.model.Filters;
import net.isoverse.isocore.chat.mail.SendMail;
import net.isoverse.isocore.playerdata.PlayerData;
import net.isoverse.isocore.playerdata.PlayerDataWrapper;
import net.isoverse.isocore.stats.rewards.ConsumerReward;
import net.isoverse.isocore.stats.rewards.Reward;
import net.isoverse.isocore.utills.Msg;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class StatsManager {

    private static final int MAXLEVEL = 100;
    private static int[] LEVELS = new int[100];
    private static final Map<Integer, List<Reward>> REWARDS = new HashMap<>();

    static {
        populateLevels();
        populateRewards();
    }

    public static int getLevel(int level) {
        return LEVELS[level];
    }

    private static void populateRewards() {
        //1-9
        addConsumerReward(1, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(2, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-emoji"); });
        addConsumerReward(3, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-spells"); });
        addConsumerReward(4, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(5, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 5!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-pets");
        });
        addConsumerReward(6, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(7, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare"); });
        addConsumerReward(8, player -> {
          new SendMail(new PlayerData(Filters.eq("uuid", player.getUniqueId().toString())), "By leveling up, you earned an isoVerse Store coupon! Use this coupon to get 10% off any crate. CODE: CRATE-30516");
          player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.YELLOW, "Level Reward"));
          player.sendMessage(Msg.format("  &6> &eBy leveling up, you earned an isoVerse Store coupon! &6<"));
          player.sendMessage(Msg.format("           &7Use this coupon to get &f10%% off any crate&7."));
          player.sendMessage(Msg.format("                   &8>&7>&f> &eCRATE-30516 &f<&7<&8<"));
          player.sendMessage(Msg.format("           &f&nThis has also been sent to your /mail"));
          player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.GOLD));
        });
        addConsumerReward(9, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        //10-19
        addConsumerReward(10, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 10. &7Everyone gets &3500 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 500 xp");
        });
        addConsumerReward(11, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-trails"); });
        addConsumerReward(12, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-spells"); });
        addConsumerReward(13, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-pets"); });
        addConsumerReward(14, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(15, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 15!");
            player.sendMessage("&3» &fYou unlocked the &7Light Gray &flevel color! &7Activate with &f/level&7.");
        });
        addConsumerReward(16, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(17, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-emoji"); });
        addConsumerReward(18, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(19, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-pets"); });
        //20-29
        addConsumerReward(20, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 20!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-disguise"); });
        addConsumerReward(21, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-spells"); });
        addConsumerReward(22, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-emoji"); });
        addConsumerReward(23, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-pets"); });
        addConsumerReward(24, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-spells"); });
        addConsumerReward(25, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 25. &7Everyone gets &31000 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 1000 xp");
        });
        addConsumerReward(26, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(27, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-disguise"); });
        addConsumerReward(28, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(29, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        //30-39
        addConsumerReward(30, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 30.");
            player.sendMessage(Msg.format("&3» &fYou unlocked the &3Aqua &flevel color! &7Activate with &f/level&7."));
        });
        addConsumerReward(31, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-emoji"); });
        addConsumerReward(32, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-disguise"); });
        addConsumerReward(33, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(34, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(35, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 35. &7Everyone gets &31000 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 1000 xp");
        });
        addConsumerReward(36, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-spells"); });
        addConsumerReward(37, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic"); });
        addConsumerReward(38, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(39, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-disguise"); });
        //40-49
        addConsumerReward(40, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 40.");
            player.sendMessage(Msg.format("&3» &fYou unlocked the &2Green &flevel color! &7Activate with &f/level&7."));
        });
        addConsumerReward(41, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(42, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-emoji"); });
        addConsumerReward(43, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 43. &7Everyone gets &31250 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 1250 xp");
        });
        addConsumerReward(44, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(45, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 45.");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-spells"); });
        addConsumerReward(46, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-pets"); });
        addConsumerReward(47, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-emoji"); });
        addConsumerReward(48, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 legendary"); });
        addConsumerReward(49, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-pets"); });
        //50
        addConsumerReward(50, player -> { player.sendMessage(Msg.format("&3» &fYou unlocked the &5Purple &flevel color! &7Activate with &f/level&7.")); });
        addConsumerReward(51, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(52, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-disguise"); });
        addConsumerReward(53, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-pets"); });
        addConsumerReward(54, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-trails"); });
        addConsumerReward(55, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-emoji"); });
        addConsumerReward(56, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(57,  player -> {
            new SendMail(new PlayerData(Filters.eq("uuid", player.getUniqueId().toString())), "By leveling up, you earned an isoVerse Store coupon! Use this coupon to get 10% off any crate. CODE: ISO-20163");
            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.YELLOW, "Level Reward"));
            player.sendMessage(Msg.format("  &6> &eBy leveling up, you earned an isoVerse Store coupon! &6<"));
            player.sendMessage(Msg.format("           &7Use this coupon to get &f10%% off any purchase&7."));
            player.sendMessage(Msg.format("                   &8>&7>&f> &eISO-20163 &f<&7<&8<"));
            player.sendMessage(Msg.format("           &f&nThis has also been sent to your /mail"));
            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.GOLD));
        });
        addConsumerReward(58, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(59, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-spells"); });
        //60-69
        addConsumerReward(60, player -> { player.sendMessage(Msg.format("&3» &fYou unlocked the &9Blue &flevel color! &7Activate with &f/level&7")); });
        addConsumerReward(61, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(62, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-emoji"); });
        addConsumerReward(63, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(64, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-emoji"); });
        addConsumerReward(65, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 95. &7Everyone gets &31500 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 1500 xp");
        });
        addConsumerReward(66, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-emoji"); });
        addConsumerReward(67, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-trails"); });
        addConsumerReward(68, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-trails"); });
        addConsumerReward(69, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-pets"); });
        //70-79
        addConsumerReward(70, player -> { player.sendMessage(Msg.format("&3» &fYou unlocked the &6Orange &flevel color! &7Activate with &f/level&7.")); });
        addConsumerReward(71, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 "); });
        addConsumerReward(72, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-disguise"); });
        addConsumerReward(73, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-pets"); });
        addConsumerReward(74, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-trails"); });
        addConsumerReward(75, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-pet"); });
        addConsumerReward(76, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 common-disguise"); });
        addConsumerReward(77, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-pets"); });
        addConsumerReward(78, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-trails"); });
        addConsumerReward(79, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        //80-89
        addConsumerReward(80, player -> { player.sendMessage(Msg.format("&3» &fYou unlocked the &eYellow &flevel color! &7Activate with &f/level&7.")); });
        addConsumerReward(81, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-disguise"); });
        addConsumerReward(82, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(83, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 legendary-spells"); });
        addConsumerReward(84, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-disguise"); });
        addConsumerReward(85, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(86, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 legendary-emoji"); });
        addConsumerReward(87, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-trails"); });
        addConsumerReward(88, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 legendary-pets"); });
        addConsumerReward(89, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-spell"); });
        //90-99
        addConsumerReward(90, player -> { player.sendMessage(Msg.format("&3» &fYou unlocked the &4Red &flevel color! &7Activate with &f/level&7")); });
        addConsumerReward(91, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-trails"); });
        addConsumerReward(92, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-emoji"); });
        addConsumerReward(93, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 legendary-trails"); });
        addConsumerReward(94, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-spells"); });
        addConsumerReward(95, player -> {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 95. &7Everyone gets &31500 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 1500 xp");
        });
        addConsumerReward(96, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 rare-spells"); });
        addConsumerReward(97, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 egendary-disguise"); });
        addConsumerReward(98, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 lepic-pets"); });
        addConsumerReward(99, player -> { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giverewards" + player.getName() + "1 epic-spells"); });
        //100
        addConsumerReward(100, player -> {
            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.GOLD, ChatColor.YELLOW, "Level Reward"));
            player.sendMessage(Msg.format(" &fCongratulations! You've reached the maximum level on isoVerse, from all of us here at isoVerse, thank you for dedicating so much time to our community."));
            player.sendMessage(Msg.format(" &7&o(Unless you're Glamify, in which case you've cheated.)"));
            player.sendMessage("");
            player.sendMessage(Msg.format(" &e> &fYou unlocked the &0Black &flevel color! &7Activate with &f/level&7."));
            player.sendMessage(Msg.genLine(ChatColor.DARK_GRAY, ChatColor.GOLD));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rawglobal &3» &b" + player.getName() + " reached level 100. &7Everyone gets &35000 XP&7!");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "commandexecute giverewards all 5000 xp");
        });
    }

    private static void addConsumerReward(int level, Consumer<Player> consumer) {
        addReward(level, new ConsumerReward(consumer));
    }
    
    private static void addReward(int level, Reward reward) {
        REWARDS.computeIfAbsent(level, k -> new ArrayList<>(2)).add(reward);
    }



    public static void populateLevels() {
        int[] levels = new int[MAXLEVEL];

        int expReq = 0;

        for (int i=0 ; i<10 ; i++)
        {
            expReq += 500;
            levels[i] = expReq;
        }

        for (int i=10 ; i<20 ; i++)
        {
            expReq += 1000;
            levels[i] = expReq;
        }

        for (int i=20 ; i<40 ; i++)
        {
            expReq += 2000;
            levels[i] = expReq;
        }

        for (int i=40 ; i<60 ; i++)
        {
            expReq += 3000;
            levels[i] = expReq;
        }

        for (int i=60 ; i<80 ; i++)
        {
            expReq += 4000;
            levels[i] = expReq;
        }

        for (int i=80 ; i<levels.length ; i++)
        {
            expReq += 5000;
            levels[i] = expReq;
        }

        LEVELS = levels;
    }


    public static LevelData getLevelData(long exp) {
        for (int i = 0; i < LEVELS.length; i++) {
            int req = LEVELS[i];

            //Has Experience, Level Up!
            if (exp >= req) {
                exp -= req;
                continue;
            }
            return new LevelData(i, exp, req);
        }
        return new LevelData(MAXLEVEL, -1, -1);
    }

    public static void xp(Player player, String type, int xp) {
        PlayerData playerData = new PlayerData(Filters.eq("uuid", player.getUniqueId().toString()));
        PlayerDataWrapper[] playerFile = playerData.get();

        if (type.equalsIgnoreCase("add")) {
            xp = playerFile[0].getXp() + xp;
        } else if (type.equalsIgnoreCase("remove")) {
            xp = playerFile[0].getXp() - xp;
        }
        playerFile[0].setXp(xp);
        player.sendActionBar(Component.text(Msg.format("&f+&3" + xp + "&7xp")));

        int newLevel = getLevelData(playerFile[0].getXp()).getLevel();
        if (playerFile[0].getLevel() < newLevel) {
            playerFile[0].setLevel(newLevel);
            player.sendMessage(Msg.format("&3» &bYou are now Level " + newLevel + "!"));

            List<Reward> levelRewards = REWARDS.get(newLevel);
            for (Reward reward : levelRewards) {
                reward.claim(player);

            }
        }
        playerData.replace(playerFile[0]);

    }

}
