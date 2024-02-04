package com.natsu.nestsync;

import java.util.HashMap;
import java.util.UUID;

public class NestList {
    final private String uID;
    private String nestListName;
    private HashMap items;

    public NestList(String name) {
        uID = UUID.randomUUID().toString();
        nestListName = name;
        items = new HashMap<String,Item>();
    }

    public String getuID() {
        return uID;
    }

    public String getNestListName() {
        return nestListName;
    }

    public void setNestListName(String name) {
        nestListName = name;
    }

    public HashMap getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.put(item.getuID(), item);
    }

    public void removeItem (Item item) {
        items.remove(item.getuID());
    }
}
