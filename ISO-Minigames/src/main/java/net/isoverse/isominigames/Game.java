package net.isoverse.isominigames;

public class Game {

    /**
     * Game
     */
    private String type;
    private String[] description;


    /**
     * State
     */
    private GameState gameState;
    private long gameLiveTime;
    private long gameStateTime = System.currentTimeMillis();

    private boolean prepareCountdown = false;

    private int countdown = -1;
    private boolean countdownForce = false;

    /**
     * Gameplay flags
     */

    public boolean SpectatorAllowed = true;

    public boolean Damage = true;
    public boolean DamagePvP = true;
    public boolean DamagePvE = true;
    public boolean DamageEvP = true;
    public boolean DamageSelf = true;
    public boolean DamageFall = true;
    public boolean DamageTeamSelf = false;
    public boolean DamageTeamOther = true;

    public boolean TeleportsDisqualify = true;
    public boolean NightVision = false;

    /**
     *
     * @param type
     * @param description
     */
    public Game(String type, String[] description) {
        this.type = type;
        this.description = description;
    }

}
