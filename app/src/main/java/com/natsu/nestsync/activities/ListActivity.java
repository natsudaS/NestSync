package com.natsu.nestsync.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natsu.nestsync.ListAdapter;
import com.natsu.nestsync.OnRecyclerItemClickListener;
import com.natsu.nestsync.R;
import com.natsu.nestsync.models.Item;
import com.natsu.nestsync.models.NestList;

import java.util.ArrayList;

public class ListActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    ArrayList<String> itemIds;
    ArrayList<Item> items;
    Button backBtn,shareBtn;
    DatabaseReference mDatabaseref;
    EditText title, newItemText;
    FloatingActionButton addItemBtn;
    FirebaseUser fUser;
    ListAdapter listAdapt;
    NestList newList;
    RecyclerView recView;
    String listID;
    String userID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listview);

        listID = getIntent().getStringExtra("listID");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        backBtn = findViewById(R.id.backButton);
        shareBtn = findViewById(R.id.shareButton);
        title = findViewById(R.id.editTitle);
        recView = findViewById(R.id.itemsList);
        newItemText = findViewById(R.id.addItemEditText);
        addItemBtn = findViewById(R.id.addItemButton);

        mDatabaseref = FirebaseDatabase.getInstance().getReference().child("nestLists");

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        items = new ArrayList();
        itemIds = new ArrayList();
        listAdapt = new ListAdapter((Context) this, items, itemIds, (OnRecyclerItemClickListener) this);
        recView.setAdapter(listAdapt);

        if (listID.equals("0")){
            newList = new NestList();
            newList.writeNewList(userID);
            title.setText(newList.getNestListTitle());
            listID = newList.getNestListUUID();
            Toast.makeText(ListActivity.this, "new List created", Toast.LENGTH_SHORT).show();
        } else {
            //query to display current nestList title
            mDatabaseref.child(listID).child("nestListTitle").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String nestListTitle = dataSnapshot.getValue(String.class);
                    title.setText(nestListTitle);
                    Toast.makeText(ListActivity.this, "List opened: "+nestListTitle, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            });
        }

        //change title
        title.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    String newTitle = title.getText().toString();
                    mDatabaseref.child(listID).child("nestListTitle").setValue(newTitle);
                }
            }
        });

        //query to display current nestList items
        mDatabaseref.child(listID).child("items").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                items.clear();
                itemIds.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String itemID = snapshot1.getKey();
                    itemIds.add(itemID);
                    mDatabaseref.child(listID).child("items").child(itemID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Item item = dataSnapshot.getValue(Item.class);
                            items.add(item);
                            listAdapt.notifyDataSetChanged(); // Notify RecyclerView adapter of the data change
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(ListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                listAdapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        //add further items
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = newItemText.getText().toString();
                Item newItem = new Item();
                newItem.setItemName(text);
                newItem.writeNewItem(listID);
                newItemText.setText("");
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "userId passed: "+ userID);
                Intent intent = new Intent(getApplicationContext(),MembersActivity.class);
                intent.putExtra("listID",listID);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onRecItemClick(int pos, String id) {
    }

    //checkbox change
    @Override
    public void onRecItemBtnClick(int pos, String id) {
        Toast.makeText(this, "status changed" + pos + "with id: " + id, Toast.LENGTH_SHORT).show();
        mDatabaseref.child(listID).child("items").child(id).removeValue();
    }

    //name change on EditText
    @Override
    public void onRecItemTextClick(String id, String text){
        Toast.makeText(this, "Text changed with id: " + id, Toast.LENGTH_SHORT).show();
        mDatabaseref.child(listID).child("items").child(id).child("itemName").setValue(text);
    }
}
