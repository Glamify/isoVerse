package net.isoverse.isocore.chat.mail;

import org.bson.codecs.pojo.annotations.BsonProperty;

public class MailWrapper {

    private String server;
    @BsonProperty("receiveddate")
    private long receivedDate;
    private boolean status;
    @BsonProperty("statusdate")
    private long statusDate;
    private String message;

    public MailWrapper() {}

    public MailWrapper(String server, long receivedDate, boolean status, long statusDate, String message) {
        this.server = server;
        this.receivedDate = receivedDate;
        this.status = status;
        this.statusDate = statusDate;
        this.message = message;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public void setReceivedDate(long receivedDate) {
        this.receivedDate = receivedDate;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setStatusDate(long statusDate) {
        this.statusDate = statusDate;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getServer() {
        return server;
    }

    public long getReceivedDate() {
        return receivedDate;
    }

    public boolean isStatus() {
        return status;
    }

    public long getStatusDate() {
        return statusDate;
    }

    public String getMessage() {
        return message;
    }

}
