package com.natsu.nestsync;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Button goLoginButton = findViewById(R.id.goLogin);
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goLoginButton.setText("Welt");
            }
        });

        final Button goRegisterButton = findViewById(R.id.goRegister);
        goRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goRegisterButton.setText("Welt");
            }
        });
    }
}