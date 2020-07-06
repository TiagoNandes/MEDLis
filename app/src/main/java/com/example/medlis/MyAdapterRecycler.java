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


public class MyAdapterRecycler extends RecyclerView.Adapter<MyAdapterRecycler.ViewHolder> {
    FirebaseFirestore fstore;
    public static class ViewHolder extends RecyclerView.ViewHolder {


        protected TextView txtTitle, txtDescription, txtDate;
        protected ImageButton checkIntake;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
            checkIntake = itemView.findViewById(R.id.taken);
           /* Button b = itemView.findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mymusic", "Album " + getAdapterPosition() + " clicado.");
                }
            });*/


        }
    }

    private ArrayList<Notification> myAlbumList;

    public MyAdapterRecycler(ArrayList<Notification> myAlbumList) {
        this.myAlbumList = myAlbumList;
    }

    public MyAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_notification, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapterRecycler.ViewHolder viewHolder, int i){
        Notification item = myAlbumList.get(i);
        viewHolder.txtTitle.setText(item.getTitle());
        viewHolder.txtDescription.setText(item.getDescription());
        viewHolder.txtDate.setText(item.getAlertDate().toDate().toString());
        if(item.getCheckIntake()==false){
            viewHolder.checkIntake.setVisibility(View.VISIBLE);//show
            viewHolder.checkIntake.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    DocumentReference washingtonRef = fstore.getInstance().collection("Alert").document(item.getNotification_id());

// Set the "isCapital" field of the city 'DC'
                    washingtonRef
                            .update("checkIntake", true)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG", "DocumentSnapshot successfully updated!");
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

        }
        else {
            viewHolder.checkIntake.setVisibility(View.GONE);//makes it disappear
        }
        // viewHolder.imgCover.setText(item.getCover());
        // viewHolder.imgCover.setImageResource(item.getCover());

    }

    @Override
    public int getItemCount(){
        return myAlbumList.size();
    }

}