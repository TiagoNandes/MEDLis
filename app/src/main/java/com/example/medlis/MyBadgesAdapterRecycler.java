package com.example.medlis;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.badge.BadgeClass;
import com.example.medlis.notifications.Notification;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;


public class MyBadgesAdapterRecycler extends RecyclerView.Adapter<MyBadgesAdapterRecycler.ViewHolder> {
    FirebaseFirestore fstore;
    public static class ViewHolder extends RecyclerView.ViewHolder {


        protected TextView txtTitle, txtDescription, txtDate;
        protected ImageButton checkIntake;
        protected ConstraintLayout layout;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtDescription);
            txtDate = itemView.findViewById(R.id.txtDate);
            checkIntake = itemView.findViewById(R.id.taken);
            layout = itemView.findViewById(R.id.constraintLayout29);
           /* Button b = itemView.findViewById(R.id.button);
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("mymusic", "Album " + getAdapterPosition() + " clicado.");
                }
            });*/


        }
    }

    private ArrayList<BadgeClass> myAlbumList;

    public MyBadgesAdapterRecycler(ArrayList<BadgeClass> myAlbumList) {
        this.myAlbumList = myAlbumList;
    }

    public MyBadgesAdapterRecycler.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i){
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_notification, viewGroup, false);
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyBadgesAdapterRecycler.ViewHolder viewHolder, int i){
        BadgeClass item = myAlbumList.get(i);
        viewHolder.txtTitle.setText(item.getIdBadge());
        viewHolder.txtDescription.setText(item.get_user_badge());
        viewHolder.txtDate.setText(item.get_unblock_date().toString());



        // viewHolder.imgCover.setText(item.getCover());
        // viewHolder.imgCover.setImageResource(item.getCover());

    }

    @Override
    public int getItemCount(){
        return myAlbumList.size();
    }

}