package com.sanbong.model;

/**
 * Created by Diep_Chelsea on 20/07/2016.
 */
public class Pitch {
    private  String id;
    private  String ownerId;
    private  String money;
    private  String money2;
    private  String money3;

    private  String name;
    private  String location;
    private  String ownerName;
    private  String ownerPhone;

    public Pitch(String id, String ownerId, String money, String name, String location, String ownerName, String ownerPhone) {
        this.id = id;
        this.ownerId = ownerId;
        this.money = money;
        this.money2 = money2;
        this.money3 = money3;
        this.name = name;
        this.location = location;
        this.ownerName = ownerName;
        this.ownerPhone = ownerPhone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getMoney() {
        return money;
    }

    public void setmoney(String money) {
        this.money= money;
    }

    public String getMoney2() {
        return money2;
    }

    public void setMoney2(String money2) {
        this.money2 = money2;
    }

    public String getMoney3() {
        return money3;
    }

    public void setMoney3(String money3) {
        this.money3 = money3;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }
}
