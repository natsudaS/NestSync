package com.natsu.nestsync;

import java.util.HashMap;
import java.util.UUID;

public class Item {
    final private String uID;
    private String itemName;

    public Item(String name) {
        uID = UUID.randomUUID().toString();
        itemName = name;
    }

    public String getuID() {
        return uID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        itemName = name;
    }
}

