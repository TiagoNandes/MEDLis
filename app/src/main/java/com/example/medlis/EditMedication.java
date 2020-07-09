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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class EditMedication extends AppCompatActivity {

    String editPosology ="";
    String editDescription ="";
    EditText description;
    EditText posology;

    FirebaseFirestore fstore;
    FirebaseAuth fAuth;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final String[] editMedId = {""};
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_medication);

        final ConstraintLayout goBack = findViewById(R.id.header);


        description = findViewById(R.id.editMedDescription);
        posology = findViewById(R.id.editPosology);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            editMedId[0] = extras.getString("editMed");
            Log.e("OLA BOM DIA", editMedId[0]);
            //The key argument here must match that used in the other activity
        }


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userID = user.getUid();

            DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID).collection("User_med").document(String.valueOf(editMedId[0]));
            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    Log.e("description", String.valueOf(description));
                    description.setText(documentSnapshot.getString("dosage_description"));
                    Log.e("hours", documentSnapshot.getString("dosage_hours"));
                    posology.setText(documentSnapshot.getString("dosage_hours"));
                    //mPassword.setText(documentSnapshot.getString("password"));
                    //profilePic.
                }
            });



        }
        final Button btnEditMed = findViewById(R.id.editMedSavebtn);


        btnEditMed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                editDescription = description.getText().toString();
                editPosology = posology.getText().toString();

                DocumentReference documentReference = fstore.getInstance().collection("Users").document(userID).collection("User_med").document(String.valueOf(editMedId[0]));;

                documentReference
                        .update("dosage_description", editDescription,"dosage_hours", editPosology)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                Log.d("TAG", "DocumentSnapshot successfully updated!");
                                Toast.makeText(getApplicationContext(),"Dados alterados com sucesso",Toast.LENGTH_SHORT).show();
                                Intent q1 = new Intent(EditMedication.this, ManageMedication.class);
                                startActivity(q1);
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
                Intent q1 = new Intent(EditMedication.this, ManageMedication.class);
                startActivity(q1);
            }
        });





    }
}