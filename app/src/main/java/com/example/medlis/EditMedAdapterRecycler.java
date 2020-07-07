package com.example.medlis;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class EditMedAdapterRecycler extends RecyclerView.Adapter<EditMedAdapterRecycler.ViewHolder> {
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

    public EditMedAdapterRecycler(ArrayList<MedicationClass> myAlbumList) {

        this.myAlbumList = myAlbumList;
    }

    public EditMedAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_medication, viewGroup, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EditMedAdapterRecycler.ViewHolder viewHolder, int i){



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