package com.example.medlis;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MedicationAdapterRecycler extends RecyclerView.Adapter<MedicationAdapterRecycler.ViewHolder>{


//    private String description;
//    private String dosage_hours;
//    private Timestamp expiry_date;
//    private String id_medicine;
//    private String remaining_quantity;
//    private String id_user;

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
        Log.d("MedicationAdapterR", String.valueOf(new MedicationAdapterRecycler.ViewHolder(itemView)));
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicationAdapterRecycler.ViewHolder viewHolder, int i){
        MedicationClass item = myMedicationList.get(i);
//        Log.d("TAG", item.getDescription());
        viewHolder.txtDescription.setText(item.getDescription());
        Log.d("medication", "Medication " + viewHolder.txtDescription.getText()  + " chegou.");
        viewHolder.txtDosageHours.setText(item.getDosageHours());
        viewHolder.txtExpiryDate.setText(item.getExpiryDate());
        viewHolder.txtMedicine.setText(item.getIdMedicine());
        Log.d("medication", "ITEM " + item.toString() + " chegou.");
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
