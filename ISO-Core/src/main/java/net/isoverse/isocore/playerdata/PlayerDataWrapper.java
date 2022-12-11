package net.isoverse.isocore.playerdata;


import net.isoverse.isocore.chat.mail.MailWrapper;
import net.isoverse.isocore.chat.nicknames.NicknameWrapper;
import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class PlayerDataWrapper {
    private String uuid;
    private String ign;
    private String ignLower;
    private long firstJoinDate;
    private String status;
    private long lastSeen;
    private long playtime;
    private String server;
    private int gems;
    private String levelColor;
    private int level;
    private int xp;
    private long rewardsLast;
    private long rewardsDay;
    private boolean haslinked;
    private String discordLink;
    private String linkCode;
    private String replyingTo;
    private boolean vanished;
    private List<String> ranks;
    private List<String> blocked;
    private List<MailWrapper> mail;
    private List<NicknameWrapper> nicknames;
    private SpellWrapper spells;

    /*
     * Required by Mongo to not have a complete stroke and aneurysm asynchronously.
     */
    @SuppressWarnings("unused")
    public PlayerDataWrapper() {}

    public PlayerDataWrapper(String uuid, String ign, String ignLower, long firstJoinDate, String status, long lastSeen, long playtime, String server, int gems, String levelColor, int level, int xp, long rewardsLast, long rewardsDay, boolean haslinked, String discordLink, String linkCode, String replyingTo, boolean vanished, List<String> ranks, List<MailWrapper> mail, List<String> blocked, List<NicknameWrapper> nicknames, SpellWrapper spells) {
        this.uuid = uuid;
        this.ign = ign;
        this.ignLower = ignLower;
        this.firstJoinDate = firstJoinDate;
        this.status = status;
        this.lastSeen = lastSeen;
        this.playtime = playtime;
        this.server = server;
        this.gems = gems;
        this.levelColor = levelColor;
        this.level = level;
        this.xp = xp;
        this.rewardsLast = rewardsLast;
        this.rewardsDay = rewardsDay;
        this.haslinked = haslinked;
        this.discordLink = discordLink;
        this.linkCode = linkCode;
        this.replyingTo = replyingTo;
        this.vanished = vanished;
        this.ranks = ranks;
        this.mail = mail;
        this.blocked = blocked;
        this.nicknames = nicknames;
        this.spells = spells;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public String getIgnLower() {
        return ignLower;
    }

    public void setIgnLower(String ignLower) {
        this.ignLower = ignLower;
    }

    public long getFirstJoinDate() {
        return firstJoinDate;
    }

    public void setFirstJoinDate(long firstJoinDate) {
        this.firstJoinDate = firstJoinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public long getPlaytime() {
        return playtime;
    }

    public void setPlaytime(long playtime) {
        this.playtime = playtime;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public int getGems() {
        return gems;
    }

    public void setGems(int gems) {
        this.gems = gems;
    }

    public String getLevelColor() {
        return levelColor;
    }

    public void setLevelColor(String levelColor) {
        this.levelColor = levelColor;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getRewardsLast() {
        return rewardsLast;
    }

    public void setRewardsLast(long rewardsLast) {
        this.rewardsLast = rewardsLast;
    }

    public long getRewardsDay() {
        return rewardsDay;
    }

    public void setRewardsDay(long rewardsDay) {
        this.rewardsDay = rewardsDay;
    }

    public boolean isHaslinked() {
        return haslinked;
    }

    public void setHaslinked(boolean haslinked) {
        this.haslinked = haslinked;
    }

    public String getDiscordLink() {
        return discordLink;
    }

    public void setDiscordLink(String discordLink) {
        this.discordLink = discordLink;
    }

    public String getLinkCode() {
        return linkCode;
    }

    public void setLinkCode(String linkCode) {
        this.linkCode = linkCode;
    }

    public String getReplyingTo() {
        return replyingTo;
    }

    public void setReplyingTo(String replyingTo) {
        this.replyingTo = replyingTo;
    }

    public boolean isVanished() {
        return vanished;
    }

    public void setVanished(boolean vanished) {
        this.vanished = vanished;
    }

    public List<String> getRanks() {
        return ranks;
    }

    public void setRanks(List<String> ranks) {
        this.ranks = ranks;
    }

    public List<String> getBlocked() {
        return blocked;
    }

    public void setBlocked(List<String> blocked) {
        this.blocked = blocked;
    }

    public List<MailWrapper> getMail() {
        return mail;
    }

    public void setMail(List<MailWrapper> mail) {
        this.mail = mail;
    }

    public List<NicknameWrapper> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<NicknameWrapper> nicknames) {
        this.nicknames = nicknames;
    }

    public SpellWrapper getSpells() {
        return spells;
    }

    public void setSpells(SpellWrapper spells) {
        this.spells = spells;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
