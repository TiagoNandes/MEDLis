package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class RegisterType extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);
        final ImageButton email = findViewById(R.id.email);
        //final ImageButton facebook = findViewById(R.id.buttonFacebookLogin2);

        final TextView login = findViewById(R.id.login);

        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, Registo.class);
                startActivity(q1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, login.class);
                startActivity(q1);
            }
        });

        /*facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, FacebookLogin.class);
                startActivity(q1);
            }
        });*/
    }
}
