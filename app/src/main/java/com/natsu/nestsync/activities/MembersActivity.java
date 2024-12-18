package com.natsu.nestsync.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natsu.nestsync.ListAdapter;
import com.natsu.nestsync.MemberAdapter;
import com.natsu.nestsync.OnRecyclerItemClickListener;
import com.natsu.nestsync.R;
import com.natsu.nestsync.models.Item;

import java.util.ArrayList;

public class MembersActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    ArrayList posMembers,actMembers,memIds;
    Button toListBtn;
    DatabaseReference dataRef;
    MemberAdapter memAdapt;
    RecyclerView recView;
    String listID,userID;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_members);

        listID = getIntent().getStringExtra("listID");
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recView = findViewById(R.id.memList);
        toListBtn = findViewById(R.id.toListButton);

        dataRef = FirebaseDatabase.getInstance().getReference();

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        posMembers = new ArrayList<>();
        actMembers = new ArrayList<>();
        memIds = new ArrayList<>();
        memAdapt = new MemberAdapter((Context) this, posMembers, memIds, listID, (OnRecyclerItemClickListener) this);
        recView.setAdapter(memAdapt);

        Log.i(TAG, "listID: "+listID+", userID: "+userID);

        //query to display possible members
        dataRef.child("users").child(userID).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                memIds.clear();
                posMembers.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    String memID = snapshot1.getKey();
                    String name = snapshot1.getValue().toString();
                    memIds.add(memID);
                    posMembers.add(name);

                    //query to get whether a list is already shared with a particular friend
                    dataRef.child("nestLists").child(listID).child("memberIDs").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                String id = snapshot1.getKey();
                                for(int i=0;i<memIds.size();i++){
                                    if(id.equals(memIds.get(i))){
                                        actMembers.add(id);
                                    }
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MembersActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                memAdapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MembersActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

        toListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "listId passed: "+ listID);
                Intent intent = new Intent(getApplicationContext(),ListActivity.class);
                intent.putExtra("listID",listID);
                startActivity(intent);
            }
        });
    }

    //shared activated
    @Override
    public void onRecItemClick(int pos, String id) {
        dataRef.child("nestLists").child(listID).child("memberIDs").child(id).setValue(true);
        dataRef.child("users").child(id).child("nestLists").child(listID).setValue(true);
    }

    //shared deactivated
    @Override
    public void onRecItemBtnClick(int pos, String id) {
        dataRef.child("nestLists").child(listID).child("memberIDs").child(id).removeValue();
        dataRef.child("users").child(id).child("nestLists").child(listID).removeValue();
    }

    @Override
    public void onRecItemTextClick(String id, String text) {

    }
}
