package com.natsu.nestsync;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // button vars
        final Button goLoginButton = findViewById(R.id.goLogin);
        final Button goRegisterButton = findViewById(R.id.goRegister);

        //event handling buttons
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //public void onClick(View view) {goLoginButton.setText("Welt");}
            public void onClick(View view) {
                Intent gotoLogin= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(gotoLogin);
            }
        });

        goRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            //public void onClick(View view) {Toast.makeText(view.getContext(), "OK gedrückt", Toast.LENGTH_SHORT).show();}
            public void onClick(View view) {
                Intent gotoRegister = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(gotoRegister);
            }
        });
    }
}