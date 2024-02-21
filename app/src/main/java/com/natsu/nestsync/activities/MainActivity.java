package com.natsu.nestsync.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.natsu.nestsync.R;

public class MainActivity extends AppCompatActivity {
    //declare vars
    Button goLoginButton, goRegisterButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find elements
        goLoginButton = findViewById(R.id.goLogin);
        goRegisterButton = findViewById(R.id.goRegister);

        //event handling
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoLogin= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(gotoLogin);
            }
        });

        goRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(gotoRegister);
            }
        });
    }
}