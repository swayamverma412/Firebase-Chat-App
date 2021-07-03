package com.swayam.chatter.Modelclass;

public class User {
    String uid;
    String name;
    String email;
    String imageuri;

    public User() {

    }

    public User(String uid, String name, String email, String imageuri) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.imageuri = imageuri;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageuri() {
        return imageuri;
    }

    public void setImageuri(String imageuri) {
        this.imageuri = imageuri;
    }
}
