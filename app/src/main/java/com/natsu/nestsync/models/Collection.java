package com.natsu.nestsync.models;

import com.natsu.nestsync.models.NestList;

import java.util.HashMap;
import java.util.UUID;

public class Collection {
    //declare vars
    final private String uID;
    private String collectionName;
    private HashMap collectionLists;

    public Collection(String name) {
        uID = UUID.randomUUID().toString();
        collectionName = name;
        collectionLists = new HashMap<String, NestList>();
    }

    public String getUID() {
        return uID;
    }

    //public String getCollectionName() {return collectionName;}

    //public void setCollectionName(String name) {collectionName = name;}

    //public HashMap getCollectionLists() {return collectionLists;}

    //public void addNestList (NestList nestList) {collectionLists.put(nestList.getuID(), nestList);}

    //public void removeNestList (NestList nestList) {collectionLists.remove(nestList.getuID());}
}

