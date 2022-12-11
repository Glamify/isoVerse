package net.isoverse.isocore.stats;

public class LevelData {

    private int level;
    private long expRemainder;
    private long expNextLevel;

    public LevelData(int level, long expRemainder, long expNextLevel) {
        this.level = level;
        this.expRemainder = expRemainder;
        this.expNextLevel = expNextLevel;
    }

    public int getLevel() {
        return level;
    }

    public long getExpRemainder() {
        return expRemainder;
    }

    public long getExpNextLevel() {
        return expNextLevel;
    }
}
