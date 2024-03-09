package com.natsu.nestsync.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natsu.nestsync.R;

public class MenuActivity extends AppCompatActivity {
    //vars
    Button homeBtn,nameBtn,mailBtn,pswdBtn,friendsBtn,delBtn;
    EditText nameEdit,mailEdit,oldPswdEdit,newPswdEdit;

    DatabaseReference dataRef;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //get elements
        homeBtn = findViewById(R.id.homeBtn);
        nameEdit = findViewById(R.id.nameEdit);
        nameBtn = findViewById(R.id.nameButton);
        mailEdit = findViewById(R.id.mailEdit);
        mailBtn = findViewById(R.id.mailBtn);
        friendsBtn = findViewById(R.id.friendsBtn);
        delBtn = findViewById(R.id.deleteBtn);

        //database connection
        dataRef = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fUser = FirebaseAuth.getInstance().getCurrentUser();

        //event handling
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            }
        });

        dataRef.child("users").child(fUser.getUid()).child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.getValue().toString();
                nameEdit.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        dataRef.child("users").child(fUser.getUid()).child("email").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String mail = snapshot.getValue().toString();
                mailEdit.setText(mail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEdit.getText().toString();
                dataRef.child("users").child(fUser.getUid()).child("username").setValue(newName);
                Toast.makeText(getApplicationContext(), "username updated!", Toast.LENGTH_SHORT).show();
            }
        });

        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = mailEdit.getText().toString();
                fUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d(TAG, "User email address updated: "+ fUser.getEmail());
                        dataRef.child("users").child(fUser.getUid()).child("email").setValue(newEmail);
                        Toast.makeText(getApplicationContext(), "mail updated only in realtime database", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        friendsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), FriendsActivity.class));
            }
        });

        delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Log.d(TAG, "User account deleted.");
                            dataRef.child("users").child(fUser.getUid()).child("nestLists").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot snapshot1: snapshot.getChildren()){
                                        String listID = snapshot1.getKey();
                                        dataRef.child("nestLists").child(listID).removeValue();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            dataRef.child("users").child(fUser.getUid()).removeValue();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }
                    }
                });
            }
        });
    }
}
