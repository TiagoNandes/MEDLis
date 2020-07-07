package com.example.medlis;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.notifications.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.Executor;


public class NewAdapterRecycler extends RecyclerView.Adapter<NewAdapterRecycler.ViewHolder> {
    FirebaseFirestore fstore;

    TextToSpeech tts;
    Context context;
    private TextToSpeech mTTs;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtDescription, txtDosageHours, txtExpiryDate, txtMedicine;
        protected  ImageView b;



        public ViewHolder(View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtMedDescription);
            txtDosageHours = itemView.findViewById(R.id.txtMedDosageHours);
            txtExpiryDate = itemView.findViewById(R.id.txtMedExpiryDate);
            txtMedicine = itemView.findViewById(R.id.txtMedMedicine);
            b = itemView.findViewById(R.id.imageView10);




        }
    }

    private ArrayList<MedicationClass> myAlbumList;

    public NewAdapterRecycler(ArrayList<MedicationClass> myAlbumList) {

        this.myAlbumList = myAlbumList;
    }

    public NewAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        this.context = viewGroup.getContext();
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_medication, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapterRecycler.ViewHolder viewHolder, int i) {

        MedicationClass item = myAlbumList.get(i);

        //------------------------------------------ get medication by id ----------------------------------------
        String id_medicine = item.getIdMedicine();
        String medicineName = getMedName(id_medicine);

        viewHolder.txtMedicine.setText(medicineName);
        DocumentReference documentReference = fstore.getInstance().collection("Medicine").document(id_medicine);
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();

                    if (document.exists()) {
                        viewHolder.txtMedicine.setText(document.getString("name").toString());
                    }

                }
            }
        });


        //------------------------------------------ end get medication by id ----------------------------------------

        viewHolder.txtDescription.setText(item.getDescription() + " de " + item.getDosageHours() + " em " + item.getDosageHours() + " horas");
        viewHolder.txtDosageHours.setText("Comprimidos restantes: " + item.getRemainingQuantity());
        viewHolder.txtExpiryDate.setText("Prazo Validade: " + item.getExpiryDate());
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
                            viewHolder.b.setEnabled(true);
                        }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });




        viewHolder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String textToRead = viewHolder.txtMedicine.getText().toString() + "         " + viewHolder.txtDescription.getText() + "         " + viewHolder.txtDosageHours.getText() + "         " + viewHolder.txtExpiryDate.getText();
                textToSpeech(textToRead);
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
    private void textToSpeech(String toRead) {
        Log.d("TAG", "chegou");
        mTTs.speak(toRead, TextToSpeech.QUEUE_FLUSH, null);
    }




}