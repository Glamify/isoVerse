package net.isoverse.isocore.playerdata;

import org.bson.codecs.pojo.annotations.BsonProperty;

import java.util.List;

public class OldPlayerDataWrapper {

    public String uuid;
    public String ign;
    @BsonProperty("ignlower")
    public String ignLower;
    public String joined;
    public String seen;
    public String server;
    public String status;
    @BsonProperty("levelcolor")
    public String levelColor;
    public Double xp;
    @BsonProperty("playtime")
    public Double playTime;
    @BsonProperty("haslinked")
    public String hasLinked;
    @BsonProperty("spelllast")
    public String spellLast;
    public Double hug;
    @BsonProperty("rolleyes")
    public Double rollEyes;
    public Double lol;
    public Double rocket;
    public Double blind;
    public Double umbrella;
    public Double luck;
    public Double slap;
    public Double frost;
    public Double smite;
    public Double fly;
    public Double god;
    @BsonProperty("rewardsday")
    public Double rewardsDay;
    @BsonProperty("rewardslast")
    public String rewardsLast;
    public Double level;
    @BsonProperty("linkcode")
    public String linkCode;
    @BsonProperty("discid")
    public String discId;
    public Double cry;
    public Double kiss;
    @BsonProperty("xpbooster")
    public Double xpBooster;
    @BsonProperty("survivalmoneybooster")
    public Double survivalMoneyBooster;
    public Double heal;
    public Double taunt;
    public List<String> roles;
    public List<String> blocked;
    public String nickname;
    @BsonProperty("requestednickname")
    public String requestedNickname;
    public List<String> messages;

}
