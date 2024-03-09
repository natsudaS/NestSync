package com.natsu.nestsync.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.natsu.nestsync.FriendsAdapter;
import com.natsu.nestsync.HomeAdapter;
import com.natsu.nestsync.OnRecyclerItemClickListener;
import com.natsu.nestsync.R;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    ArrayList<String> friendNames, friendIDs;
    Button backToMenuBtn;
    EditText addFriendEdit;
    FloatingActionButton addFriendBtn;
    FriendsAdapter friendAdapt;
    RecyclerView recView;

    DatabaseReference dataRef;
    FirebaseUser fUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendslist);

        backToMenuBtn = findViewById(R.id.toMenuButton);
        addFriendEdit = findViewById(R.id.addUserEditText);
        addFriendBtn = findViewById(R.id.addUserButton);
        recView = findViewById(R.id.friendsList);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dataRef = FirebaseDatabase.getInstance().getReference().child("users");

        recView.hasFixedSize();
        recView.setLayoutManager(new LinearLayoutManager(this));
        friendNames = new ArrayList<>();
        friendIDs = new ArrayList<>();
        friendAdapt = new FriendsAdapter((Context) this, friendNames, friendIDs, (OnRecyclerItemClickListener) this);
        recView.setAdapter(friendAdapt);

        backToMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
            }
        });

        //query to display users friends
        dataRef.child(fUser.getUid()).child("friends").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                friendNames.clear();
                friendIDs.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String friendID = snapshot1.getKey();
                    friendIDs.add(friendID);

                    dataRef.child(fUser.getUid()).child("friends").child(friendID).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String friendName = dataSnapshot.getValue(String.class);
                            friendNames.add(friendName);
                            friendAdapt.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(FriendsActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                friendAdapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String friendID = addFriendEdit.getText().toString();
                dataRef.child(friendID).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            String name = snapshot.getValue(String.class);
                            dataRef.child(fUser.getUid()).child("username").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        String username = snapshot.getValue(String.class);
                                        // Write data to both user's friends list
                                        dataRef.child(fUser.getUid()).child("friends").child(friendID).setValue(name);
                                        dataRef.child(friendID).child("friends").child(fUser.getUid()).setValue(username);
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Handle error
                                }
                            });
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
            }
        });
    }

    @Override
    public void onRecItemClick(int pos, String id) {

    }

    //delete friend
    @Override
    public void onRecItemBtnClick(int pos, String id) {
        dataRef.child(id).child("friends").child(fUser.getUid()).removeValue();
        dataRef.child(fUser.getUid()).child("friends").child(id).removeValue();
    }

    @Override
    public void onRecItemTextClick(String id, String text) {

    }
}
