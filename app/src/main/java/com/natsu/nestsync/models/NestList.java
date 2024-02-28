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
    private HashMap members;
    private List items;

    //database connection
    private DatabaseReference nestListDataRef = FirebaseDatabase.getInstance().getReference().child("nestLists");

    public NestList(){
        //needed for firebase
    }

    public NestList(String userID, String title) {
        nestListTitle = title;
        members = new HashMap<String,Boolean>();
        members.put(userID,true);
        item = new Item("sample");
        items = new ArrayList<Item>();
        items.add(item);
    }

    public void writeNewList(){
        Log.i(TAG, "writeNewList() called");
        nestListUUID = nestListTitle + UUID.randomUUID().toString();
        nestListDataRef.child(nestListUUID).setValue(this);
    }

    public String getNestListUUID() {return nestListUUID;}

    public String getNestListTitle(){return nestListTitle;}

    public List getItems(){return items;}

    /*public List getMemberNames() {
        List memberNames = new ArrayList<>();
        for (Object i : members.keySet()) {
            memberNames.add(i);
        }
        return memberNames;
    }*/
}
