package net.isoverse.isocore.playerdata;

public class SpellWrapper {

    private double eyeRoll;
    private double lol;
    private double smite;
    private double rocket;
    private double blind;
    private double umbrella;
    private double luck;
    private double slap;
    private double frost;
    private double fly;
    private double god;
    private double heal;
    private double cry;
    private double xpBooster;
    private double moneyBooster;
    private long spellLast;

    /*
     * Required by Mongo to not have a complete stroke and aneurysm asynchronously.
     */
    @SuppressWarnings("unused")
    public SpellWrapper() {
    }

    public SpellWrapper(double eyeRoll, double lol, double smite, double rocket, double blind, double umbrella, double luck, double slap, double frost, double fly, double god, double heal, double cry, double xpBooster, double moneyBooster, long spellLast) {
        this.eyeRoll = eyeRoll;
        this.lol = lol;
        this.smite = smite;
        this.rocket = rocket;
        this.blind = blind;
        this.umbrella = umbrella;
        this.luck = luck;
        this.slap = slap;
        this.frost = frost;
        this.fly = fly;
        this.god = god;
        this.heal = heal;
        this.cry = cry;
        this.xpBooster = xpBooster;
        this.moneyBooster = moneyBooster;
        this.spellLast = spellLast;
    }

    public double getEyeRoll() {
        return eyeRoll;
    }

    public void setEyeRoll(double eyeRoll) {
        this.eyeRoll = eyeRoll;
    }

    public double getLol() {
        return lol;
    }

    public void setLol(double lol) {
        this.lol = lol;
    }

    public double getSmite() {
        return smite;
    }

    public void setSmite(double smite) {
        this.smite = smite;
    }

    public double getRocket() {
        return rocket;
    }

    public void setRocket(double rocket) {
        this.rocket = rocket;
    }

    public double getBlind() {
        return blind;
    }

    public void setBlind(double blind) {
        this.blind = blind;
    }

    public double getUmbrella() {
        return umbrella;
    }

    public void setUmbrella(double umbrella) {
        this.umbrella = umbrella;
    }

    public double getLuck() {
        return luck;
    }

    public void setLuck(double luck) {
        this.luck = luck;
    }

    public double getSlap() {
        return slap;
    }

    public void setSlap(double slap) {
        this.slap = slap;
    }

    public double getFrost() {
        return frost;
    }

    public void setFrost(double frost) {
        this.frost = frost;
    }

    public double getFly() {
        return fly;
    }

    public void setFly(double fly) {
        this.fly = fly;
    }

    public double getGod() {
        return god;
    }

    public void setGod(double god) {
        this.god = god;
    }

    public double getHeal() {
        return heal;
    }

    public void setHeal(double heal) {
        this.heal = heal;
    }

    public double getCry() {
        return cry;
    }

    public void setCry(double cry) {
        this.cry = cry;
    }

    public double getXpBooster() {
        return xpBooster;
    }

    public void setXpBooster(double xpBooster) {
        this.xpBooster = xpBooster;
    }

    public double getSurvivalMoneyBooster() {
        return moneyBooster;
    }

    public void setSurvivalMoneyBooster(double survivalMoneyBooster) {
        this.moneyBooster = survivalMoneyBooster;
    }

    public long getSpellLast() {
        return spellLast;
    }

    public void setSpellLast(long spellLast) {
        this.spellLast = spellLast;
    }
}
