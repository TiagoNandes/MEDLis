package com.example.medlis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.io.IOException;
import java.net.MalformedURLException;

public class Badge extends AppCompatActivity {
    FirebaseFirestore fstore;
    ImageView badgeImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_badge);
        final ImageButton textSpeech = (ImageButton) findViewById(R.id.toSpeech);
        final ConstraintLayout goBack = findViewById(R.id.header);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String userMed_id = extras.getString("BadgeId");
            Log.d("TAG", "IN MED" + extras.getString("BadgeId"));
            String value = "";

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String userID = user.getUid();
            //The key argument here must match that used in the other activity
            DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID).collection("User_badge").document(userMed_id);

            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    final TextView date = findViewById(R.id.textView32);
                    Timestamp date1 = (Timestamp) documentSnapshot.get("unblock_date");
                    date.setText(String.valueOf(date1.toDate().toString()));
                    //profilePic.
                    if (documentSnapshot.getString("id_badge") != null) {
                        writeBadge(documentSnapshot.getString("id_badge"));

                    }

                }
            });
            goBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent q1 = new Intent(Badge.this, BadgesList.class);
                    startActivity(q1);
                }
            });
        }
    }
    private void writeBadge(String medId) {
        final TextView badgeName = findViewById(R.id.textView30);
        final TextView badgeDescription = findViewById(R.id.textView36);


        badgeImage = findViewById(R.id.imageView14);
        DocumentReference medicineReference = fstore.getInstance().collection("Badge").document(medId);

        medicineReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                badgeName.setText(documentSnapshot.getString("title"));
                badgeDescription.setText(documentSnapshot.getString("description"));

                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                if (documentSnapshot.get("image") != null) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 8;
                    try {
                        java.net.URL url = new java.net.URL((String) documentSnapshot.get("image"));


                        Bitmap bmp = null;

                        bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

                        badgeImage.setImageBitmap(bmp);


                    } catch (MalformedURLException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

    }
}