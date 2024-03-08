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
    //class vars needed for user
    private String name;
    private HashMap nestLists;
    private NestList sampleNestList;

    //database connection
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseUser fUser = fAuth.getCurrentUser();
    private DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference().child("users").child(fUser.getUid());

    public User() {
        //needed for firebase
    }

    public User(String name) {
        this.name = name;
        nestLists = new HashMap<String, Boolean>();
        sampleNestList = new NestList();
        sampleNestList.setNestListTitle("Sample");
        sampleNestList.writeNewList(fUser.getUid());
        nestLists.put(sampleNestList.getNestListUUID(), true);
    }

    public void writeNewUser() {
        Log.i(TAG, "writeNewUser() called");
        userDataRef.setValue(this);
    }

    public String getUsername(){return name;}
    public HashMap getNestLists(){return nestLists;}
}
