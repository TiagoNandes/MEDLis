package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    RecyclerView rvAlbuns;
    private ArrayList<com.example.medlis.notifications.Notification> albuns;
    private MyAdapterRecycler albumAdapter;

    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);




        rvAlbuns = findViewById(R.id.rvNotifications);

        rvAlbuns.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvAlbuns.setLayoutManager(llm);



        albuns = new ArrayList<com.example.medlis.notifications.Notification>();
        albuns.add(
                new com.example.medlis.notifications.Notification("Nevermind", "Nirvana", 1));
        albuns.add(
                new com.example.medlis.notifications.Notification("Just Push Play", "Aerosmith", 2));
        albuns.add(
                new com.example.medlis.notifications.Notification("Slippery when wet", "Bon Jovi", 2));
        albuns.add(
                new com.example.medlis.notifications.Notification("Return to forever", "Scorpions", 3));
        albuns.add(
                new com.example.medlis.notifications.Notification("Master Of Puppets", "Metallica", 5));
        albumAdapter = new MyAdapterRecycler(albuns);
        rvAlbuns.setAdapter(albumAdapter);


    }

}
