package net.isoverse.isocore.security;

public enum SecurityType {

    BAN("ban"),
    BLACKLIST("blacklist"),
    UNBLACKLIST("unblacklist"),
    UNBAN("unban"),
    MUTE("mute"),
    UNMUTE("unmute"),
    KICK("kick");

    private String name;

    SecurityType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
