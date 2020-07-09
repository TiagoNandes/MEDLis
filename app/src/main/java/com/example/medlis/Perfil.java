package com.example.medlis;

import androidx.annotation.NonNull;
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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

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
                    String photo ="";
                }
            });

// Reference to an image file in Cloud Storage
            StorageReference storageReference = FirebaseStorage.getInstance().getReference();

// ImageView in your Activity


            storageReference.child(userID+".jpeg").getBytes(Long.MAX_VALUE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    ImageView imageView = findViewById(R.id.profilePic);
                    // Use the bytes to display the image
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    imageView.setImageBitmap(bitmap);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle any errors

                 //   imageView.setImageResource(R.drawable.avatar_profile);


                }
            });

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            String uid = user.getUid();
        }
        // [END get_user_profile]
    }

}
