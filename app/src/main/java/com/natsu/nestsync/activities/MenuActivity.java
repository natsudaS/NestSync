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

        nameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = nameEdit.getText().toString();
                dataRef.child("users").child(fUser.getUid()).child("username").setValue(newName);
            }
        });

        mailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEmail = mailEdit.getText().toString();
                fUser.verifyBeforeUpdateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User email address updated.");
                            dataRef.child("users").child(fUser.getUid()).child("email").setValue(newEmail);
                            mailEdit.setText(fUser.getEmail());
                        }
                    }
                });
            }
        });

        /*pswdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String oldPswd = oldPswdEdit.getText().toString();
                String newPswd = newPswdEdit.getText().toString();

                if (TextUtils.isEmpty(oldPswd)){
                    oldPswdEdit.setError("Old Password is required.");
                    return;
                }

                if (TextUtils.isEmpty(newPswd)){
                    newPswdEdit.setError("New Password is required.");
                    return;
                }

                if (newPswd.length() < 8){
                    newPswdEdit.setError("Password must be 8 characters or longer.");
                    return;
                }

                if (newPswd.equals(oldPswd)){
                    newPswdEdit.setError("Passwords must differ.");
                    return;
                }
                fUser.updatePassword(newPswd).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");

                        }
                    }
                });
            }
        });*/

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
