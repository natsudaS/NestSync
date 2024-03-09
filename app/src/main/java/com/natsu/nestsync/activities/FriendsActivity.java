package com.natsu.nestsync.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natsu.nestsync.R;

import java.util.ArrayList;
import java.util.List;

public class FriendsActivity extends AppCompatActivity {
    Button backToMenuBtn;
    EditText addFriendEdit;
    FloatingActionButton addFriendBtn;

    DatabaseReference dataRef;
    FirebaseUser fUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendslist);

        backToMenuBtn = findViewById(R.id.toMenuButton);
        addFriendEdit = findViewById(R.id.addUserEditText);
        addFriendBtn = findViewById(R.id.addUserButton);

        fUser = FirebaseAuth.getInstance().getCurrentUser();
        dataRef = FirebaseDatabase.getInstance().getReference().child("users");

        backToMenuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MenuActivity.class));
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
}
