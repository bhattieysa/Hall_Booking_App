package com.example.hallbooking;

public class FavoritesModel {
    String uid,hallname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHallname() {
        return hallname;
    }

    public void setHallname(String hallname) {
        this.hallname = hallname;
    }

    public FavoritesModel(String uid, String hallname) {
        this.uid = uid;
        this.hallname = hallname;
    }

    public FavoritesModel() {
    }
}
