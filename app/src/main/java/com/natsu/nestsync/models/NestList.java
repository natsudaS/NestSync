package com.natsu.nestsync.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.natsu.nestsync.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NestList {
    //class vars needed for nestList
    private String nestListUUID;
    private String nestListTitle;
    private Item item;
    private List items;

    //database connection
    private DatabaseReference nestListDataRef = FirebaseDatabase.getInstance().getReference().child("nestLists");
    private DatabaseReference usersDataRef = FirebaseDatabase.getInstance().getReference().child("users");

    public NestList(){
        //needed for firebase
    }

    private void addNestListToUser(String userid){
        usersDataRef.child(userid).child("nestLists").child(this.getNestListUUID()).setValue("true");
    }

    public void writeNewList(String userid, String title){
        Log.i(TAG, "writeNewList() called");
        nestListUUID = "nestList" + UUID.randomUUID().toString();
        setNestListTitle(title);
        if(nestListTitle.equals("Sample")){
            items = new ArrayList<>();
            item = new Item("sample Item");
            items.add(item);
        }
        nestListDataRef.child(nestListUUID).setValue(this);
        addNestListToUser(userid);
    }

    public String getNestListUUID() {return nestListUUID;}

    public String getNestListTitle(){return nestListTitle;}
    public void setNestListTitle(String title){
        nestListTitle = title;
    }
}
