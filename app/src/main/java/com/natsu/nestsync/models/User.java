package com.natsu.nestsync.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

//maybe obsolete due to firebase stuff
public class User {
    //public String uuid;
    private String name;
    private HashMap nestLists;
    private NestList sampleNestList;

    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseUser fUser = fAuth.getCurrentUser();
    private DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference().child("users");

    public User () {
        //Default required for DataSnapshot.getValue(User.class)
    }
    public User (String name) {
        this.name = name;
        nestLists = new HashMap<String,Boolean>();
        sampleNestList = new NestList(fUser.getUid(),"Sample");
        sampleNestList.writeNewList();
        nestLists.put(sampleNestList.getNestListUUID(),true);
    }

    public void writeNewUser(){
        Log.i(TAG, "writeNewUser() called");
        userDataRef.child(fUser.getUid()).setValue(this);
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
