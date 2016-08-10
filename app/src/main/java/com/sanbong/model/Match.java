package com.sanbong.model;

/**
 * Created by Diep_Chelsea on 13/07/2016.
 */
public class Match {
    private String hostID;
    private String time;
    private  String hostName;
    private  String stadium;
    private  String description;
    private  String location;
    private  String money;

    public Match(String id, String time, String hostName, String stadium, String description, String location, String money) {
        this.hostID = id;
        this.time = time;
        this.hostName = hostName;
        this.stadium = stadium;
        this.description = description;
        this.location = location;
        this.money = money;
    }
    public Match()
    {}

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getHostId() {
        return hostID;
    }

    public void setHostId(String id) {
        this.hostID = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Match{" +
                "hostID='" + hostID + '\'' +
                ", time='" + time + '\'' +
                ", hostName='" + hostName + '\'' +
                ", stadium='" + stadium + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", money='" + money + '\'' +
                '}';
    }
}
