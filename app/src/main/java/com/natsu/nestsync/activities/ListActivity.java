package com.natsu.nestsync.activities;

import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.common.net.InternetDomainName;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natsu.nestsync.ListAdapter;
import com.natsu.nestsync.R;
import com.natsu.nestsync.models.Item;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ArrayList<Item> items;
    DatabaseReference mDatabaseref;
    EditText title;
    ListAdapter listAdapt;
    RecyclerView recView;
    String listID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listID = getIntent().getStringExtra("listID");
        title = findViewById(R.id.editTitle);
        recView = findViewById(R.id.itemsList);

        mDatabaseref = FirebaseDatabase.getInstance().getReference().child("nestLists");

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList();
        listAdapt = new ListAdapter(this,items);
        recView.setAdapter(listAdapt);

        if (listID == null){
            //direkt neues nestList objekt, titel entspricht titel eingabe, items erstellte items
            //speicherung in datenbank on save
        } else {
            //query to display current nestList title
            mDatabaseref.child("listID").child("nestListTitle").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nestListTitle = dataSnapshot.getValue(String.class);
                    title.setText(nestListTitle);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Handle errors if any
                }
            });
            //query to display current nestList items
            mDatabaseref.child("listID").child("items").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    items.clear();
                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                        String itemID = snapshot1.getKey();

                        mDatabaseref.child("listID").child("items").child(itemID).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Item item = dataSnapshot.getValue(Item.class);
                                items.add(item); // Add the name of the nestList instead of its UUID
                                listAdapt.notifyDataSetChanged(); // Notify RecyclerView adapter of the data change
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle errors if any
                            }
                        });
                    }
                    listAdapt.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}
