package com.example.medlis;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class menu extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
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
                Intent q1 = new Intent(menu.this, ListMedications.class);
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






        Window window;
        window = menu.this.getWindow();

// clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

// finally change the color
        window.setStatusBarColor(ContextCompat.getColor(menu.this,R.color.topBar));
    }
}
