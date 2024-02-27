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
    private String nestListUUID;
    private String nestListTitle;
    private Item item;
    private HashMap members;
    private HashMap items;

    private DatabaseReference nestListDataRef = FirebaseDatabase.getInstance().getReference().child("nestLists");

    public NestList(String userID, String title) {
        nestListTitle = title;
        members = new HashMap<String,Boolean>();
        members.put(userID,true);
        //item = new Item("sample");
        //items = new HashMap<String, Boolean>();
        //items.put(item.getUID(), true);
    }

    public void writeNewList(){
        Log.i(TAG, "writeNewList() called");
        nestListDataRef.child(this.createNestListUUID()).setValue(this);
    }

    public String createNestListUUID() {
        nestListUUID = nestListTitle + UUID.randomUUID().toString();
        return nestListUUID; }

    public String getNestListUUID() {
        return nestListUUID;
    }
    public String getNestListTitle() {
        return nestListTitle;
    }

    public void setNestListName(String title) {
        nestListTitle = title;
    }

    public List getMemberNames() {
        List memberNames = new ArrayList<>();
        for (Object i : members.keySet()) {
            memberNames.add(i);
        }
        return memberNames;
    }

    public void addMemberbyID(String userID){ members.put(userID, true); }

    public void removeMemberbyID(String userID){ members.remove(userID); }

    //public HashMap getItems() {return items;}

    //public void addItem(Item item) {items.put(item.getuID(), item);}

    //public void removeItem (Item item) {items.remove(item.getuID());}
}
