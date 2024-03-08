package com.natsu.nestsync.models;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.util.Log;

import java.util.HashMap;
import java.util.UUID;

public class Item {
    private String itemName;
    private Boolean itemStatus;

    public Item (){}
    public Item(String name) {
        itemName = name;
        itemStatus = false;
    }

    public String getItemName() {return itemName;}

    public Boolean getItemStatus() {return itemStatus;}
}


