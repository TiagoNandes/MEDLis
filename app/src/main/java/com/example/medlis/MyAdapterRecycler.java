package com.example.medlis;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.notifications.Notification;

import java.util.ArrayList;


public class MyAdapterRecycler extends RecyclerView.Adapter<MyAdapterRecycler.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {


        protected TextView txtTitle, txtBand, imgCover;

        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtBand = itemView.findViewById(R.id.txtDescription);
            imgCover = itemView.findViewById(R.id.txtDate);

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
        viewHolder.txtBand.setText(item.getBand());
       // viewHolder.imgCover.setText(item.getCover());

       // viewHolder.imgCover.setImageResource(item.getCover());
    }

    @Override
    public int getItemCount(){
        return myAlbumList.size();
    }

}