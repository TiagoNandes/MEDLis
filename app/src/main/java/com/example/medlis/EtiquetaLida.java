package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class EtiquetaLida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] idMed = {""};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etiqueta_lida);



        int timeout = 2000; // make the activity visible for 4 seconds

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                finish();
                Bundle extras = getIntent().getExtras();
                if (extras != null) {
                    idMed[0] = extras.getString("userMedId");
                    //The key argument here must match that used in the other activity

                    Intent medication = new Intent(EtiquetaLida.this, Medication.class);
                    medication.putExtra("userMedId", idMed[0]);
                    startActivity(medication);
                }
            }
        }, timeout);
    }
}
