package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        final ImageButton tag = findViewById(R.id.tag);
        final ImageButton profile = findViewById(R.id.profile);

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
    }
}
