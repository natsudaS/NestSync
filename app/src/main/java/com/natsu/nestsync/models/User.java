package com.natsu.nestsync.models;

import com.google.firebase.database.DatabaseReference;

import java.util.UUID;

//maybe obsolete due to firebase stuff
public class User {
    private String name;
    private String email;

    public User () {
        //Default required for DataSnapshot.getValue(User.class)
    }
    public User (String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
