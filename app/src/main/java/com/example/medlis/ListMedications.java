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
import java.util.List;

public class ListMedications extends AppCompatActivity {

    RecyclerView rvMedications;
    private ArrayList<MedicationClass> medications;

    private NewAdapterRecycler medicationAdapter;

    FirebaseFirestore fstore;
    private static final String TAG = "TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_medications);

        rvMedications = findViewById(R.id.rvMedications);
        final ConstraintLayout goBack = findViewById(R.id.constraintLayout1);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rvMedications.setLayoutManager(llm);

        rvMedications.setHasFixedSize(true);


        medications = new ArrayList<MedicationClass>();

        CollectionReference medicineReference = fstore.getInstance().collection("Alert");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

       fstore.getInstance().collection("Users").document(user.getUid()).collection("User_med")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                Log.d(TAG, document.getId() + " => " + document.getData());
                                String idMed = String.valueOf(document.getId());

                                String description = String.valueOf(document.getData().get("dosage_description"));

                                String dosage_hours = String.valueOf(document.getData().get("dosage_hours"));

//                                Timestamp expiry_date = (Timestamp) document.getData().get("expiry_date");
                                String expiry_date = String.valueOf(document.getData().get("expiry_date"));

//                                Log.d("TAG", expiry_date.toString());
                                String id_medicine = String.valueOf(document.getData().get("id_medicine"));

                                int remaining_quantity = Integer.valueOf(String.valueOf(document.getData().get("remaining_quantity")));

                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                String id_user = user.getUid();
//
//                                String id_userMed = String.valueOf(document.getData().get("id_userMed"));

                                //    private String description;
                                //    private String dosage_hours;
                                //    private Timestamp expiry_date;
                                //    private String id_medicine;
                                //    private String remaining_quantity;
                                //    private String id_user;

                                medications.add(
                                        new MedicationClass(idMed, description, dosage_hours, expiry_date, id_medicine, remaining_quantity, id_user));





                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }

                        Log.d("MEDICATIONS RECENTE", medications.toString());
                        medicationAdapter = new NewAdapterRecycler(medications);
                        rvMedications.setAdapter(medicationAdapter);
                    }

                });

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(ListMedications.this, menu.class);
                startActivity(q1);
            }
        });

    }


}