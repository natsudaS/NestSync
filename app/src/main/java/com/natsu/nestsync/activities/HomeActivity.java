package com.natsu.nestsync.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.natsu.nestsync.HomeAdapter;
import com.natsu.nestsync.OnRecyclerItemClickListener;
import com.natsu.nestsync.R;
import com.natsu.nestsync.activities.MainActivity;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements OnRecyclerItemClickListener {
    // declare vars
    ArrayList<String> userNestLists;
    Button menuBtn,logoutBtn;
    FloatingActionButton addListBtn;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    DatabaseReference mDatabaseref;
    HomeAdapter homeAdapt;
    RecyclerView recView;
    String name;
    TextView greeting;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // find elements
        menuBtn = findViewById(R.id.menuButton);
        logoutBtn = findViewById(R.id.logoutButton);
        addListBtn = findViewById(R.id.addListButton);
        greeting = findViewById(R.id.textView2);
        recView = findViewById(R.id.datalist);

        mDatabaseref = FirebaseDatabase.getInstance().getReference();
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();

        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this));
        userNestLists = new ArrayList<>();
        homeAdapt = new HomeAdapter((Context) this, userNestLists, (OnRecyclerItemClickListener) this);
        recView.setAdapter(homeAdapt);

        //personalize the greeting
        mDatabaseref.child("users").child(fUser.getUid()).child("username").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                }
                else {
                    greeting.setText("Hallo, " + task.getResult().getValue());
                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
                }
            }
        });

        //query to display nestLists of current user
        mDatabaseref.child("users").child(fUser.getUid()).child("nestLists").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userNestLists.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                    String listID = snapshot1.getKey();

                    mDatabaseref.child("nestLists").child(listID).child("nestListTitle").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String nestListTitle = dataSnapshot.getValue(String.class);
                            userNestLists.add(nestListTitle); // Add the name of the nestList instead of its UUID
                            homeAdapt.notifyDataSetChanged(); // Notify RecyclerView adapter of the data change
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // Handle errors if any
                        }
                    });
                }
                homeAdapt.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //handling logout btn
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        //handling list adding btn
        addListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                intent.putExtra("listID","0");
                intent.putExtra("userID",fUser.getUid());
                startActivity(intent);
            }
        });

        //handling menu btn
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MenuActivity.class));
            }
        });
    }

    @Override
    public void onRecItemClick(int pos) {
        // Handle the click event here
        Toast.makeText(this, "Item clicked at position " + pos, Toast.LENGTH_SHORT).show();
    }

    //logout user
    public void logout (View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}

