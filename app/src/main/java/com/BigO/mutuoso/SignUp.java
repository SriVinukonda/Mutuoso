package com.BigO.mutuoso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Random;

public class SignUp extends AppCompatActivity {
    private EditText e1, e2, e3, e4, e5;
    private String name, age, email, pass, cpass;
    private Button b1;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        e1 = findViewById(R.id.signUpName);
        e2 = findViewById(R.id.signUpAge);
        e3 = findViewById(R.id.signUpEmail);
        e4 = findViewById(R.id.signUpPassword);
        e5 = findViewById(R.id.signUpPasswordConfirm);
        b1 = findViewById(R.id.signUpButton);
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                try
                {
                    name = e1.getText().toString().trim();
                    age  = e2.getText().toString().trim();
                    email = e3.getText().toString().trim();
                    pass = e4.getText().toString().trim();
                    cpass = e5.getText().toString().trim();
                    if(pass.equals(cpass)) {
                        signup();
                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(SignUp.this, "Enter Valid / Missing Details", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    void signup()
    {
        mAuth.createUserWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUp.this, "Successfully Created!", Toast.LENGTH_SHORT).show();
                           firebase_user.user = mAuth.getCurrentUser();
                           updateUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        finish();
    }
    void updateUser()
    {
        Random rand = new Random();
        String temp = name.substring(0,5) + rand.nextInt(1000);
        try {
            myRef.setValue(temp);
            HashMap<String, Object> result = new HashMap<>();
            result.put("email", email);
            result.put("age", age);
            result.put("name", name);
            myRef.child("users").child(temp).push().updateChildren(result);
        }catch (Exception e)
        {
            Toast.makeText(SignUp.this, "Failed", Toast.LENGTH_SHORT).show();
        }
    }
}