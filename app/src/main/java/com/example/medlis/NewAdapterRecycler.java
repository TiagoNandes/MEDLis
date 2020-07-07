package com.example.medlis;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.notifications.Notification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class NewAdapterRecycler extends RecyclerView.Adapter<NewAdapterRecycler.ViewHolder> {
    FirebaseFirestore fstore;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected TextView txtDescription, txtDosageHours, txtExpiryDate, txtMedicine;

        public ViewHolder(View itemView) {
            super(itemView);
            txtDescription = itemView.findViewById(R.id.txtMedDescription);
            txtDosageHours = itemView.findViewById(R.id.txtMedDosageHours);
            txtExpiryDate = itemView.findViewById(R.id.txtMedExpiryDate);
            txtMedicine = itemView.findViewById(R.id.txtMedMedicine);


           /* Button b = itemView.findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mymusic", "Album " + getAdapterPosition() + " clicado.");
                }
            });*/


        }
    }

    private ArrayList<MedicationClass> myAlbumList;

    public NewAdapterRecycler(ArrayList<MedicationClass> myAlbumList) {

        this.myAlbumList = myAlbumList;
    }

    public NewAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_medication, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewAdapterRecycler.ViewHolder viewHolder, int i){



        Log.d("TAGGGGGG", String.valueOf(i));
        MedicationClass item = myAlbumList.get(i);
        Log.d("medication", "Medication "+ item.getDescription());
       viewHolder.txtDescription.setText(item.getDescription());
        // Log.d("medication", "Medication " + viewHolder.txtDescription.getText()  + " chegou.");
        viewHolder.txtDosageHours.setText(item.getDosageHours());
        viewHolder.txtExpiryDate.setText(item.getExpiryDate());
        viewHolder.txtMedicine.setText(item.getIdMedicine());

    }

    @Override
    public int getItemCount(){
        return myAlbumList.size();
    }

}