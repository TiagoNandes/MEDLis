package com.example.medlis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class EditProfile extends AppCompatActivity {
    String editName="";
    String editPhone="";
    String editEmail ="";
    String editPassword ="";
    EditText mName;
    EditText mPhone;
    EditText mEmail;
    EditText mPassword;

    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final ConstraintLayout goBack = findViewById(R.id.header);

        mName = findViewById(R.id.editProfileName);
        mPhone = findViewById(R.id.editProfilePhone);
        mEmail = findViewById(R.id.editProfileEmail);
        mPassword = findViewById(R.id.editProfilePassword);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
            Uri photoUrl = user.getPhotoUrl();

            mName.setText(email);
            userID = user.getUid();

            DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID);
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    mName.setText(documentSnapshot.getString("name"));
                    mEmail.setText(email);
                    mPhone.setText(documentSnapshot.getString("phoneNumber"));
                    //mPassword.setText(documentSnapshot.getString("password"));
                    //profilePic.
                }
            });



        }
       final Button update1 = findViewById(R.id.EditProfileSavebtn);


        update1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editName = mName.getText().toString();
                editPhone = mPhone.getText().toString();
                editEmail = mEmail.getText().toString();
                editPassword = mPassword.getText().toString();

                DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID);

                documentReference
                        .update("name", editName,"phoneNumber", editPhone)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                user.updateEmail(editEmail);
                                if(editPassword.trim()!=" " && editPassword.length()>=6 && editPassword.trim()!=""){
                                    Log.d("TAG", "ENTROU");
                updatePassword(editEmail, editPassword);
                                }
                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                Toast.makeText(getApplicationContext(),"Dados alterados com sucesso",Toast.LENGTH_SHORT).show();
                            }

                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("TAG", "Error updating document", e);
                            }
                        });



            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(EditProfile.this, Perfil.class);
                startActivity(q1);
            }
        });

    }

    public void updatePassword(String email, String newPass){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPass);
    }
}