package com.natsu.nestsync;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HomeActivity extends AppCompatActivity {
    // declare vars
    Button logoutBtn,testButton;
    EditText testData;
    FirebaseAuth oAuth;
    FirebaseUser user;
    DatabaseReference rootDatabaseref;
    String name;
    TextView greeting;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // find elements
        logoutBtn = findViewById(R.id.logoutButton);
        testButton = findViewById(R.id.testButton);
        testData = findViewById(R.id.testData);
        greeting = findViewById(R.id.textView2);
        rootDatabaseref = FirebaseDatabase.getInstance().getReference();
        // rootDatabaseref = FirebaseDatabase.getInstance().getReference().child("MyData"); for making it a child of mydata
        // referencing approach rather then embedding approach
        oAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
            String email = user.getEmail();
        }


        greeting.setText(name);
        // event handling
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout(v);
            }
        });

        testButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String data = testData.getText().toString();
                rootDatabaseref.setValue(data);
            }
        });
    }

    public void logout (View v) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }
}

