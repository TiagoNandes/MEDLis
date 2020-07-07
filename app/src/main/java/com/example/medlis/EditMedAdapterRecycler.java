package com.example.medlis;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Locale;


public class EditMedAdapterRecycler extends RecyclerView.Adapter<EditMedAdapterRecycler.ViewHolder> {
    FirebaseFirestore fstore;
    String userID;

    TextToSpeech tts;
    Context context;
    private TextToSpeech mTTs;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtEditMedDescription, txtEditMedDosageHours, txtEditMedExpiryDate, txtEditTitle;
        protected ConstraintLayout singleEditMed;
        protected ImageButton btnEditMed, btnDeleteMed;



        public ViewHolder(View itemView) {
            super(itemView);
            txtEditMedDescription = itemView.findViewById(R.id.txtEditMedDescription);
            txtEditMedDosageHours = itemView.findViewById(R.id.txtEditMedDosageHours);
            txtEditMedExpiryDate = itemView.findViewById(R.id.txtEditMedExpiryDate);
            txtEditTitle = itemView.findViewById(R.id.txtEditTitle);
            singleEditMed = itemView.findViewById(R.id.singleEditMed);
            btnEditMed = itemView.findViewById(R.id.btnEditMed);
            btnDeleteMed = itemView.findViewById(R.id.btnDeleteMed);




        }
    }

    private ArrayList<MedicationClass> myAlbumList;

    public EditMedAdapterRecycler(ArrayList<MedicationClass> myAlbumList) {

        this.myAlbumList = myAlbumList;
    }

    public EditMedAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        this.context = viewGroup.getContext();
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_edit_medication, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EditMedAdapterRecycler.ViewHolder viewHolder, int i) {

        MedicationClass item = myAlbumList.get(i);

        //------------------------------------------ get medication by id ----------------------------------------
        String id_medicine = item.getIdMedicine();
        String medicineName = getMedName(id_medicine);

        viewHolder.txtEditTitle.setText(medicineName);
        DocumentReference documentReference = fstore.getInstance().collection("Medicine").document(id_medicine);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        viewHolder.txtEditTitle.setText(document.getString("name").toString());
                    }

                }
            }
        });


        //------------------------------------------ end get medication by id ----------------------------------------

        viewHolder.txtEditMedDescription.setText(item.getDescription() + " de " + item.getDosageHours() + " em " + item.getDosageHours() + " horas");
        viewHolder.txtEditMedDosageHours.setText("Comprimidos restantes: " + item.getRemainingQuantity());
        viewHolder.txtEditMedExpiryDate.setText("Prazo Validade: " + item.getExpiryDate());
//        viewHolder.txtMedicine.setText(item.getIdMedicine());



        mTTs = new TextToSpeech(this.context, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS) {
                    int result = mTTs.setLanguage(new Locale("pt", "pt"));
                        if (result == TextToSpeech.LANG_MISSING_DATA ||
                                result == TextToSpeech.LANG_NOT_SUPPORTED) {
                            Log.e("TTS", "Language not supported");

                        } else {
                            viewHolder.singleEditMed.setEnabled(true);
                        }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });




        viewHolder.singleEditMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToRead = viewHolder.txtEditTitle.getText().toString() + viewHolder.txtEditMedDescription.getText() + viewHolder.txtEditMedDosageHours.getText() + viewHolder.txtEditMedExpiryDate.getText();
                textToSpeech(textToRead);
            }
        });


        viewHolder.btnDeleteMed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.e("OLA BOM DIA", item.getMedicineId());
                deleteMed(item.getMedicineId());

            }
        });



    }

    @Override
    public int getItemCount(){
        return myAlbumList.size();
    }


    public String getMedName(String id_medicine){
        String medicineName ="";
        DocumentReference documentReference = fstore.getInstance().collection("Medicine").document(id_medicine);

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("2", "validar");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        document.getString("name").toString();
                    }

                }
            }
            });
        return medicineName;
    }

    private void deleteMed(String idMed) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();
        fstore.getInstance().collection("Users").document(userID).collection("User_med").document(idMed)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

    private void textToSpeech(String toRead) {
        Log.d("TAG", "chegou");
        mTTs.speak(toRead, TextToSpeech.QUEUE_FLUSH, null);
    }




}