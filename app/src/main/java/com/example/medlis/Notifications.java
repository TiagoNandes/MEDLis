package com.example.medlis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

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

import java.util.ArrayList;

public class Notifications extends AppCompatActivity {

    RecyclerView rvAlbuns;
    private ArrayList<com.example.medlis.notifications.Notification> albuns;
    private MyAdapterRecycler albumAdapter;
    FirebaseFirestore fstore;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);



        final ConstraintLayout menu = findViewById(R.id.header);
        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Notifications.this, menu.class);
                startActivity(q1);
            }
        });




        rvAlbuns = findViewById(R.id.rvMedications);

        rvAlbuns.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvAlbuns.setLayoutManager(llm);


        albuns = new ArrayList<Notification>();


        CollectionReference medicineReference = fstore.getInstance().collection("Alert");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        fstore.getInstance().collection("Alert")
                .whereEqualTo("id_user", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                String title = String.valueOf(document.getData().get("title"));
                                String description = String.valueOf(document.getData().get("description"));
                                Timestamp date = (Timestamp) document.getData().get("alertDate");
                                Timestamp alertDate = (Timestamp) document.getData().get("alertDate");
                                boolean checkIntake = Boolean.parseBoolean(document.getData().get("checkIntake").toString());
                                String id_user = String.valueOf(document.getData().get("id_user"));

                                String id_userMed = String.valueOf(document.getData().get("id_userMed"));

                                albuns.add(
                                        new Notification(document.getId(),title, alertDate, checkIntake, description, id_user, id_userMed));

                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }


                        albumAdapter = new MyAdapterRecycler(albuns);
                        rvAlbuns.setAdapter(albumAdapter);


                    }

                });


    }



}
