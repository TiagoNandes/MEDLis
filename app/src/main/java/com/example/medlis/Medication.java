package com.example.medlis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Locale;

public class Medication extends AppCompatActivity {

    private static final String TAG = "TAG";
    FirebaseFirestore fstore;

    private TextToSpeech mTTs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication);
        final ImageButton textSpeech = (ImageButton) findViewById(R.id.toSpeech);

        mTTs = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(new Locale("pt", "pt"));
                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Log.e("TTS", "Language not supported");

                    } else {
                        textSpeech.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });
        textSpeech.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textToSpeech();
            }
        });
        /*findViewById(R.id.imageButton5).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("TAG", "LOLL");

                textToSpeech();
            }
        });*/


        Bundle extras = getIntent().getExtras();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userID = user.getUid();
        if (extras != null) {
            String userMed_id = extras.getString("userMedId");
            Log.d(TAG, "IN MED" + extras.getString("userMedId"));
            String value = "";
            //The key argument here must match that used in the other activity
            DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID).collection("User_med").document(userMed_id);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    String hours = documentSnapshot.getString("dosage_hours");

                    String description = documentSnapshot.getString("dosage_description");
                    TextView posologyText = findViewById(R.id.med_posology);
                    posologyText.setText("De " + hours + " em " + hours + " horas. " + "\n" + description);
                    //profilePic.
                    if (documentSnapshot.getString("id_medicine") != null) {
                        writeMed(documentSnapshot.getString("id_medicine"));

                    }

                }
            });

        }
    }

    private void writeMed(String medId) {
        final TextView medicationName = findViewById(R.id.medName);
        DocumentReference medicineReference = fstore.getInstance().collection("Medicine").document(medId);

        medicineReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                medicationName.setText(documentSnapshot.getString("name"));
                TextView leaflet = findViewById(R.id.textView20);
                leaflet.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Intent q1 = new Intent(Medication.this, LeafletWebview.class);
                        q1.putExtra("url", documentSnapshot.getString("leaflet"));
                        startActivity(q1);
                    }
                });
            }
        });
    }

    private void textToSpeech() {
        TextView name =  (TextView) findViewById(R.id.medName);
        TextView description =  (TextView) findViewById(R.id.med_posology);
        String text = name.getText().toString()+ description.getText().toString();
        Log.d("TAG", "chegou");
        mTTs.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    protected void onDestroy() {
        if (mTTs != null) {
            mTTs.stop();
            mTTs.shutdown();
        }

        super.onDestroy();
    }
}
