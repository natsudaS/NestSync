package com.natsu.nestsync.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.natsu.nestsync.R;

public class LoginActivity extends AppCompatActivity {
    //declare vars
    Button nLogBtn;
    EditText nMail, nPswd;
    TextView linkToReg;
    FirebaseAuth nAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // find elements
        nLogBtn = findViewById(R.id.loginBtn);
        linkToReg = findViewById(R.id.linkToReg);
        nMail = findViewById(R.id.editLogMail);
        nPswd = findViewById(R.id.editLogPassword);

        nAuth = FirebaseAuth.getInstance();

        // event handling
        nLogBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = nMail.getText().toString().trim();
                String pswd = nPswd.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    nMail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(pswd)) {
                    nPswd.setError("Password is required.");
                    return;
                }

                if (pswd.length() < 8) {
                    nPswd.setError("Password must be 8 characters or longer.");
                    return;
                }

                //authenticate user with firebase
                nAuth.signInWithEmailAndPassword(email, pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(LoginActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        linkToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });
    }
}