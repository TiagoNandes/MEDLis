package com.example.medlis;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.notifications.Notification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class MedicationAdapterRecycler extends RecyclerView.Adapter<com.example.medlis.MedicationAdapterRecycler.ViewHolder>{
    FirebaseFirestore fstore;
    public static class ViewHolder extends RecyclerView.ViewHolder {

                protected TextView txtDescription, txtDosageHours, txtExpiryDate, txtMedicine;
//    protected ImageButton readMedication;

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

    private ArrayList<MedicationClass> myMedicationList;

    public MedicationAdapterRecycler(ArrayList<MedicationClass> myMedicationList) {
        this.myMedicationList = myMedicationList;
    }


    public MedicationAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_medication, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationAdapterRecycler.ViewHolder viewHolder, int i){
        MedicationClass item = myMedicationList.get(i);

        viewHolder.txtDescription.setText(item.getDescription());
       // Log.d("medication", "Medication " + viewHolder.txtDescription.getText()  + " chegou.");
        viewHolder.txtDosageHours.setText(item.getDosageHours());
        viewHolder.txtExpiryDate.setText(item.getExpiryDate());
        viewHolder.txtMedicine.setText(item.getIdMedicine());
        //Log.d("medication", "ITEM " + item.toString() + " chegou.");
//        if(item.getCheckIntake()==false){viewHolder.checkIntake.setVisibility(View.VISIBLE);//show
//        }
//        else {
//            viewHolder.checkIntake.setVisibility(View.GONE);//makes it disappear
//        }

        // viewHolder.imgCover.setText(item.getCover());

        // viewHolder.imgCover.setImageResource(item.getCover());
    }

    @Override
    public int getItemCount(){
        return myMedicationList.size();
    }


}
