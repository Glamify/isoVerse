package net.isoverse.isocore.chat.nicknames;

public class NicknameWrapper {
    private String status;
    private String nickname;
    private long requestedOn;
    private long statusOn;
    private String statusBy;

    /*
     * Required by Mongo to not have a complete stroke and aneurysm asynchronously.
     */
    @SuppressWarnings("unused")
    public NicknameWrapper() {}

    public NicknameWrapper(String status, String nickname, long requestedOn, long statusOn, String statusBy) {
        this.status = status;
        this.nickname = nickname;
        this.requestedOn = requestedOn;
        this.statusOn = statusOn;
        this.statusBy = statusBy;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(long requestedOn) {
        this.requestedOn = requestedOn;
    }

    public long getStatusOn() {
        return statusOn;
    }

    public void setStatusOn(long statusOn) {
        this.statusOn = statusOn;
    }

    public String getStatusBy() {
        return statusBy;
    }

    public void setStatusBy(String statusBy) {
        this.statusBy = statusBy;
    }
}
