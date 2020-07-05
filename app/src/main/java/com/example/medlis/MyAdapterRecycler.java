package com.example.medlis;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.notifications.Notification;

import java.util.ArrayList;


public class MyAdapterRecycler extends RecyclerView.Adapter<MyAdapterRecycler.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {


        protected TextView txtTitle, txtDescription, txtDate;
        protected ImageButton checkIntake;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDescription = itemView.findViewById(R.id.txtMedMedicine);
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
        Log.d("TAG", item.getTitle());
        viewHolder.txtTitle.setText(item.getTitle());
        viewHolder.txtDescription.setText(item.getDescription());
        viewHolder.txtDate.setText(item.getAlertDate().toDate().toString());
        if(item.getCheckIntake()==false){viewHolder.checkIntake.setVisibility(View.VISIBLE);//show
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