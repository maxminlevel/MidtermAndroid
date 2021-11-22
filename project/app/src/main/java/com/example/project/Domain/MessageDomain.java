package com.example.project.Domain;

public class MessageDomain {
    private String pic;
    private String username;
    private String uptime;
    private String newestMessage;

    public MessageDomain(String pic, String username, String uptime, String newestMessage) {
        this.pic = pic;
        this.username = username;
        this.uptime = uptime;
        this.newestMessage = newestMessage;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUptime() {
        return uptime;
    }

    public void setUptime(String uptime) {
        this.uptime = uptime;
    }

    public String getNewestMessage() {
        return newestMessage;
    }

    public void setNewestMessage(String newestMessage) {
        this.newestMessage = newestMessage;
    }
}
