package net.isoverse.isominigames.games.survivalgames;

import net.isoverse.isominigames.SoloGame;
import org.bukkit.ChatColor;

public class Spleef extends SoloGame {

    private static final String[] description =
            {
                    "Punch blocks to break them!",
                    "You gain hunger when breaking blocks.",
                    "Last player alive wins!"
            };

    public Spleef() {
        super("Spleef", description);

        DamagePvP = false;
        NightVision = true;
    }
}
