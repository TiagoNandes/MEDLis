package com.example.medlis;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import static java.nio.file.Paths.get;


public class Perfil extends AppCompatActivity {

    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // show The Image in a ImageView

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);
        final ConstraintLayout menu = findViewById(R.id.header);
        final TextView logout = findViewById(R.id.logOut);
        final TextView nomeText = findViewById(R.id.nomeText);
        final ImageView profilePic = findViewById(R.id.profilePic);
        final ImageView settings = findViewById(R.id.settings);
        final Button badgesList = findViewById(R.id.badgesList);
        final Button btnEditMed = findViewById(R.id.btnEditMed);
        final Button btnEditProfile = findViewById(R.id.btnEditProfile);

        menu.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Perfil.this, menu.class);
                startActivity(q1);
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Perfil.this, SettingsActivity.class);
                startActivity(q1);
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(Perfil.this, RegisterType.class);
                startActivity(q1);
            }
        });
        badgesList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Perfil.this, BadgesList.class);
                startActivity(q1);
            }
        });
        btnEditMed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Perfil.this, ManageMedication.class);
                startActivity(q1);
            }
        });
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Perfil.this, EditProfile.class);
                startActivity(q1);
            }
        });
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            nomeText.setText(email);
            userID = user.getUid();

            DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    nomeText.setText(documentSnapshot.getString("name"));
                    //profilePic.
                }
            });


            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();
           // Uri imgUri=Uri.parse("android.resource://my.package.name/"+R.drawable.image);
            profilePic.setImageURI(null);
            profilePic.setImageURI(photoUrl);
            Log.e("NEWS", "NOT WORKING"+photoUrl);
           // new DownloadImageTask((ImageView) findViewById(R.id.profilePic))
             //       .execute(photoUrl.toString());
            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            String uid = user.getUid();
        }
        // [END get_user_profile]
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}
