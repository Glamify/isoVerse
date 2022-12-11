package net.isoverse.isocore.chat.nicknames;

import org.bukkit.entity.Player;

public class NickRequestWrapper {

    public String uuid;
    public long requestedOn;
    public String nickname;

    /*
     * Required by Mongo to not have a complete stroke and aneurysm asynchronously.
     */
    @SuppressWarnings("unused")
    public NickRequestWrapper() {
    }

    public NickRequestWrapper(String uuid, long requestedOn, String nickname) {
        this.uuid = uuid;
        this.requestedOn = requestedOn;
        this.nickname = nickname;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public long getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(long requestedOn) {
        this.requestedOn = requestedOn;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
