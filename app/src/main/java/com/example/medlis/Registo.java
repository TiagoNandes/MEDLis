package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Registo extends AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        final Button continuar = findViewById(R.id.continuar);
        final TextView login = findViewById(R.id.login);
        EditText mEmail =  (EditText) findViewById(R.id.email);
        EditText mPhone =  (EditText) findViewById(R.id.phone);
        EditText mName =  (EditText) findViewById(R.id.name);
        continuar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Registo.this, Register2.class);
                q1.putExtra("name", mName.getText().toString());
                q1.putExtra("phone", mPhone.getText().toString());
                q1.putExtra("email", mEmail.getText().toString());
                startActivity(q1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Registo.this, login.class);
                startActivity(q1);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

}
