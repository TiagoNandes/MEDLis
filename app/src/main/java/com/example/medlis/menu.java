package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class menu extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final ImageButton tag = findViewById(R.id.tag);
        final ImageButton profile = findViewById(R.id.profile);
        final ImageButton medication = findViewById(R.id.medication);
        final ImageButton notifications = findViewById(R.id.notifications);

        tag.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(menu.this, LerEtiqueta.class);
                startActivity(q1);
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(menu.this, Perfil.class);
                startActivity(q1);
            }
        });
        medication.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(menu.this, Medication.class);
                startActivity(q1);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(menu.this, Notifications.class);
                startActivity(q1);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            Log.d(TAG, "true +" + user);
        } else {
            // No user is signed in
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(menu.this, "failed."+user,
                    Toast.LENGTH_SHORT).show();
            Intent q1 = new Intent(menu.this, RegisterType.class);
            startActivity(q1);
        }

    }
}
