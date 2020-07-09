package com.example.medlis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.medlis.badge.BadgeClass;
import com.example.medlis.notifications.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BadgesList extends AppCompatActivity {
    FirebaseFirestore fstore;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    CollectionReference NotificationReference = fstore.getInstance().collection("Alert");
    private static final String TAG = "TAG";

    RecyclerView rvAlbuns;
    private ArrayList<BadgeClass> albuns;
    private MyBadgesAdapterRecycler albumAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badges_list);

        final ConstraintLayout menu = findViewById(R.id.header);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(BadgesList.this, menu.class);
                startActivity(q1);
            }
        });

        //GET ALL BADGES
      /*  fstore.getInstance().collection("Alert")
               // .whereEqualTo("id_user", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //PARA CADA MEDALHA

                                NotificationReference
                                        .whereEqualTo("id_user", user.getUid())
                                        //where alert date
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task1) {
                                                if (task1.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document1 : task1.getResult()) {


                                                        Timestamp timestamp = (Timestamp) document1.getData().get("alertDate");
                                                        long difference =  printDifference(Timestamp.now().toDate(), timestamp.toDate());
                                                        Log.d("TAAAAAAAG", String.valueOf(difference));
                                                        for(int i=0; i<difference;i++){

                                                        }
                                                    }
                                                } else {
                                                    Log.d(TAG, "Error getting documents: ", task1.getException());
                                                }


                                                // albumAdapter = new MyAdapterRecycler(albuns);
                                                // rvAlbuns.setAdapter(albumAdapter);


                                            }

                                        });

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                       // albumAdapter = new MyAdapterRecycler(albuns);
                       // rvAlbuns.setAdapter(albumAdapter);


                    }

                });*/


        rvAlbuns = findViewById(R.id.recyclerViewBadge);

        rvAlbuns.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvAlbuns.setLayoutManager(llm);


        albuns = new ArrayList<BadgeClass>();


        CollectionReference medicineReference = fstore.getInstance().collection("Alert");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id_user= user.getUid();
        fstore.getInstance().collection("Users").document(id_user).collection("User_badge")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.e("LOOOL", (String) document.getData().get("id_badge"));
                                String id_badge = String.valueOf(document.getData().get("id_badge"));

                                Timestamp date= (Timestamp) document.getData().get("unblock_date");
                                albuns.add(
                                        new BadgeClass(document.getId(),id_badge, date));

                            }

                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                        albumAdapter = new MyBadgesAdapterRecycler(albuns);
                        rvAlbuns.setAdapter(albumAdapter);


                    }

                });

    }
    public long printDifference(Date startDate, Date endDate) {
        //milliseconds
        long different = endDate.getTime() - startDate.getTime();

        System.out.println("startDate : " + startDate);
        System.out.println("endDate : " + endDate);
        System.out.println("different : " + different);

        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

        long elapsedDays = different / daysInMilli;
        different = different % daysInMilli;

        long elapsedHours = different / hoursInMilli;
        different = different % hoursInMilli;

        long elapsedMinutes = different / minutesInMilli;
        different = different % minutesInMilli;

        long elapsedSeconds = different / secondsInMilli;

        return elapsedDays;
    }
}