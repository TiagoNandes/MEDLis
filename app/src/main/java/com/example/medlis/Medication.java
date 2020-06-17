package com.example.medlis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Medication extends AppCompatActivity {

    private static final String TAG = "TAG";
    FirebaseFirestore fstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_medication);
        Bundle extras = getIntent().getExtras();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID= user.getUid();
        if (extras != null) {
            String userMed_id =extras.getString("userMedId");
            Log.d(TAG, "IN MED"+extras.getString("userMedId"));
            String value="";
            //The key argument here must match that used in the other activity
            DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID).collection("User_med").document(userMed_id);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    String hours =documentSnapshot.getString("dosage_hours");

                    String description =documentSnapshot.getString("dosage_description");
                    TextView posologyText = findViewById(R.id.med_posology);
                    posologyText.setText("De "+hours+ " em "+hours+" horas. "+"\n"+description);
                    //profilePic.
                    if(documentSnapshot.getString("id_medicine")!= null){
                        writeMed(documentSnapshot.getString("id_medicine"));

                    }

                }
            });

        }
    }
    private void writeMed(String medId){
        final TextView medicationName = findViewById(R.id.medName);
        DocumentReference medicineReference = fstore.getInstance().collection("Medicine").document(medId);

        medicineReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                medicationName.setText("De "+documentSnapshot.getString("dosage_description")+" em "+documentSnapshot.getString("dosage_description")+"horas."+"\n"+documentSnapshot.getString("dosage_description"));
                //profilePic.
            }
        });
    }
}
