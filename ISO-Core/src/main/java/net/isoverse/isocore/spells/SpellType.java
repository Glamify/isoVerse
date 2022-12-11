package net.isoverse.isocore.spells;

import org.bukkit.entity.Player;

public enum SpellType {
    BLIND(true, "%player% blinded %target%", "%player% gave %target% blindness", "%player% made %target% blind"),
    CRY(true, "%player% sobs uncontrollably", "%player% cries like a baby"),
    EYEROLL(true, "%player% rolls their eyes at %target%", "%player% rolled their eyes at %target%", "%player%'s eyes rolled at %target%"),
    FLY(true, "%player% magically gave everyone wings", "%player% unleashed their magical flight abilities for everyone"),
    FROST(true, "%target% felt shivers down their spine"),
    GOD(true, "%player% unleashed their inner wizard, and made you invincible"),
    HEAL(true, "%player% unleashed raw healing power upon everyone", "%player% magically healed everyone"),
    LOL(true, "%player% laughed out loud", "%player% laughed so hard they knocked their coffee over."),
    LUCK(true, "%player% felt extra lucky today", "%player% is feeling lucky", "%player% became very lucky"),
    MONEYBOOSTER(true, "%player% activated a Survival money booster! Shop transactions with traders in the Spawn will earn more money for 10 minutes."),
    ROCKET(true, "%player% launched in the sky", "%player% rocketed up in space", "%player% lifted off to the moon"),
    SLAP(true, "%player% slapped %target%", "%player% slapped %target% in the face", "%player% slapped %target%"),
    SMITE(true, "%player% struck lightning at %target%", "%player% smited %target%", "%player% threw a lightning bolt at %target%"),
    UMBRELLA(true, "%player% stopped the bad weather"),
    XPBOOSTER(true, "%player% activated an XP boost! Earn double isoVerse XP for 10 minutes");

    boolean spigotReturn;
    String[] messages;
    SpellType(Boolean spigotReturn, String... messages) {
        this.spigotReturn = spigotReturn;
        this.messages = messages;
    }

    public boolean isSpigotReturn() {
        return this.spigotReturn;
    }

    public String[] getMessage() {
        return this.messages;
    }
}