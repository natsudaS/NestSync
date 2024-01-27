package com.natsu.nestsync;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    //declare vars (Quelle: https://www.youtube.com/watch?v=TwHmrZxiPA8&list=PLlGT4GXi8_8dDK5Y3KCxuKAPpil9V49rN&index=2)
    EditText mName, mMail, mPswd, mPswdConf;
    Button mRegBtn;
    FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //find elements
        mName = findViewById(R.id.editRegName);
        mMail = findViewById(R.id.editRegMail);
        mPswd = findViewById(R.id.editRegPassword);
        mPswdConf = findViewById(R.id.editRegPassword2);
        mRegBtn = findViewById(R.id.registerBtn);

        mAuth = FirebaseAuth.getInstance();

        mRegBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String email = mMail.getText().toString().trim();
                String pswd = mPswd.getText().toString().trim();
                String pswdConf = mPswdConf.getText().toString().trim();

                if (TextUtils.isEmpty(name)){
                    mName.setError("Name is required.");
                    return;
                }

                if (TextUtils.isEmpty(email)){
                    mMail.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(pswd)){
                    mPswd.setError("Password is required.");
                    return;
                }

                if (pswd.length() < 8){
                    mPswd.setError("Password is required.");
                    return;
                }

                if (TextUtils.isEmpty(pswdConf)){
                    mPswdConf.setError("Password is required.");
                    return;
                }

                /*if (pswd != pswdConf){
                    mPswdConf.setError("Passwords don't match");
                    return;
                }*/

                //register user in firebase
                mAuth.createUserWithEmailAndPassword(email,pswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this, "User created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
