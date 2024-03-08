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
    private HashMap memberIDs;

    //database connection
    private DatabaseReference nestListDataRef = FirebaseDatabase.getInstance().getReference().child("nestLists");
    private DatabaseReference usersDataRef = FirebaseDatabase.getInstance().getReference().child("users");

    public NestList(){
        //needed for firebase
        nestListUUID = "nestList_" + UUID.randomUUID().toString();
        nestListTitle = "Untitled";
        memberIDs = new HashMap();
        items = new ArrayList();
    }

    //publics
    public void writeNewList(String userid){
        Log.i(TAG, "writeNewList() called");
        if(nestListTitle.equals("Sample")){
            item = new Item("sample Item");
            items.add(item);
        }
        memberIDs.put(userid, true);
        nestListDataRef.child(nestListUUID).setValue(this);
        addNestListToUser(userid);
    }

    //publics
    public String getNestListUUID() {return nestListUUID;}
    public String getNestListTitle(){return nestListTitle;}
    public void setNestListTitle(String title){nestListTitle=title;}
    public HashMap getMemberIDs(){return memberIDs;}
    public List getItems(){return items;}

    //public void updateNestListTitle(String nestListID, String title){}

    public void addMemberToNestList(String nestListID, String userid){
        nestListDataRef.child(nestListID).child("memberIDs").child(userid).setValue(true);
    }

    //privates
    private void addNestListToUser(String userid){
        usersDataRef.child(userid).child("nestLists").child(nestListUUID).setValue(true);
    }


}
