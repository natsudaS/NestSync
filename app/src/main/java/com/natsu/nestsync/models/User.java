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
    private String email;
    private HashMap nestLists;
    private NestList sampleNestList;

    //database connection
    private FirebaseAuth fAuth = FirebaseAuth.getInstance();
    private FirebaseUser fUser = fAuth.getCurrentUser();
    private DatabaseReference userDataRef = FirebaseDatabase.getInstance().getReference().child("users").child(fUser.getUid());

    public User() {
        nestLists = new HashMap<String, Boolean>();
    }

    public void writeNewUser(String name, String email) {
        Log.i(TAG, "writeNewUser() called");
        this.name = name;
        this.email = email;
        sampleNestList = new NestList();
        sampleNestList.setNestListTitle("Sample");
        sampleNestList.writeNewList(fUser.getUid());
        nestLists.put(sampleNestList.getNestListUUID(), true);
        userDataRef.setValue(this);
    }

    public String getUsername(){return name;}
    public String getEmail(){return email;}
    public HashMap getNestLists(){return nestLists;}
}
