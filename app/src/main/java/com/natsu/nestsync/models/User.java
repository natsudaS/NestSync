package com.natsu.nestsync.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.UUID;

//maybe obsolete due to firebase stuff
public class User {
    //public String uuid;
    private String name;
    private HashMap nestLists;
    private String sampleNestList;

    //FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();

    public User () {
        //Default required for DataSnapshot.getValue(User.class)
    }
    public User (String name) {
        //this.uuid = fUser.getUid();
        this.name = name;
        nestLists = new HashMap<String,Boolean>();
        sampleNestList = new NestList(this.name,"Sample").getNestListTitle();
        nestLists.put(sampleNestList,true);
    }

    public void writeNewUser(){
        Log.i(TAG, "writeNewUser() called");
    }
    //public String getUserID(){ return uuid; }
    public String getName() {
        return name;
    }

    //public void setName(String name) {this.name = name;}


    public HashMap getNestLists() {
        return nestLists;
    }
}
