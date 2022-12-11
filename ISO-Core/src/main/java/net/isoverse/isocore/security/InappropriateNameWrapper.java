package net.isoverse.isocore.security;

public class InappropriateNameWrapper {

    private String ign;
    private String staff;
    private long time;

    public InappropriateNameWrapper() {
    }

    public InappropriateNameWrapper(String ign, String staff, long time) {
        this.ign = ign;
        this.staff = staff;
        this.time = time;
    }

    public String getIgn() {
        return ign;
    }

    public void setIgn(String ign) {
        this.ign = ign;
    }

    public String getStaff() {
        return staff;
    }

    public void setStaff(String staff) {
        this.staff = staff;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
