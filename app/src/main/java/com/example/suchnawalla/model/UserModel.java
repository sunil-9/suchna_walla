package com.example.suchnawalla.model;

public class UserModel {
    String name, email, uid,phone;


    public UserModel(String name, String email, String uid) {
        this.name = name;
        this.email = email;
        this.uid = uid;
    }

    public UserModel(String name, String email, String uid, String phone) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.phone = phone;
    }

    public UserModel() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
