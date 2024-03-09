package com.natsu.nestsync.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.UUID;

public class Item {
    private String itemUUID;
    private String itemName;
    private Boolean itemStatus;

    private DatabaseReference iDataref = FirebaseDatabase.getInstance().getReference().child("nestLists");

    public Item() {
        itemUUID = "item_" + UUID.randomUUID().toString();
        itemName = "";
        itemStatus = false;
    }

    public void writeNewItem(String listID){
        Log.i(TAG, "writeNewItem() called to List: "+listID);
        iDataref.child(listID).child("items").child(itemUUID).setValue(this);
    }
    public String getItemUUID(){return itemUUID;}
    public String getItemName() {return itemName;}
    public void setItemName(String name){itemName=name;}
    public Boolean getItemStatus() {return itemStatus;}
}


