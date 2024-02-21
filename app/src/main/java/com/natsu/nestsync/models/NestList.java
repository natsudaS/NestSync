package com.natsu.nestsync.models;

import com.natsu.nestsync.models.Item;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class NestList {
    private UUID nestListUUID;
    private String nestListTitle;
    private Item item;
    private HashMap members;
    private HashMap items;

    public NestList(String uuid, String title) {
        nestListUUID = UUID.randomUUID();
        nestListTitle = title;
        members = new HashMap<String,Boolean>();
        members.put(uuid,true);
        //item = new Item("sample");
        //items = new HashMap<String, Boolean>();
        //items.put(item.getUID(), true);
    }

    public UUID getNestListUUID() { return nestListUUID; } //maybe toString?

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

    public void addMemberbyID(String uuid){ members.put(uuid, true); }

    public void removeMemberbyID(String uuid){ members.remove(uuid); }

    //public HashMap getItems() {return items;}

    //public void addItem(Item item) {items.put(item.getuID(), item);}

    //public void removeItem (Item item) {items.remove(item.getuID());}
}
