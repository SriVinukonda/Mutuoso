package com.BigO.mutuoso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText e1,e2;
    private Button b1,b2;
    private FirebaseAuth mAuth;
    private FirebaseAuth auth;
    private String u,p,temp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = findViewById(R.id.e1);
        e2 = findViewById(R.id.e2);
        b1 = findViewById(R.id.b1);
        auth = FirebaseAuth.getInstance();
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                try
                {
                    u = e1.getText().toString().trim();
                    p = e2.getText().toString().trim();
                    login();
                }
                catch(Exception e)
                {
                    Toast.makeText(MainActivity.this, "Enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, SignUp.class);
                startActivity(i);
            }
        });
    }
    private void login()
    {
        auth.signInWithEmailAndPassword(u,p)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            firebase_user.user = mAuth.getCurrentUser();
                            updateUI(firebase_user.user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user)
    {
        if(user != null)
        {
            String[] list = u.split("@",2);
            temp = list[0];
            Toast.makeText(MainActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(MainActivity.this, HelpMapsActivity.class);
            startActivity(i);
        }
    }
}
