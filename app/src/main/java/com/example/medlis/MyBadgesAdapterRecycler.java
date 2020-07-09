package com.example.medlis;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medlis.badge.BadgeClass;
import com.example.medlis.notifications.Notification;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;


public class MyBadgesAdapterRecycler extends RecyclerView.Adapter<MyBadgesAdapterRecycler.ViewHolder> {
    FirebaseFirestore fstore;
    Context context;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        protected ConstraintLayout openBadge;

        protected TextView txtTitle, txtLevel, txtDate;
        protected ImageView badgeImage;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.titleBadge);
            txtLevel = itemView.findViewById(R.id.level);
            txtDate = itemView.findViewById(R.id.un_date);
            badgeImage = itemView.findViewById(R.id.badgeImage);
            openBadge = itemView.findViewById(R.id.singleMedal1);
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
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.single_medal, viewGroup, false);
        this.context = viewGroup.getContext();
        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(@NonNull MyBadgesAdapterRecycler.ViewHolder viewHolder, int i){
        BadgeClass item = myAlbumList.get(i);

        viewHolder.txtDate.setText(item.get_unblock_date().toDate().toString());
        Log.d("TAG", "chegou aqui" + item.toString());
        DocumentReference documentReference = fstore.getInstance().collection("Badge").document(item.getIdBadge());
        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            private static final String TAG = "TAG";

            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        viewHolder.txtTitle.setText( String.valueOf(document.get("title")));
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        if (document.get("image") != null) {
                            BitmapFactory.Options options = new BitmapFactory.Options();
                            options.inSampleSize = 8;
                            try {
                                java.net.URL url = new java.net.URL((String) document.get("image"));


                                Bitmap bmp = null;
                                try {
                                    bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                viewHolder.badgeImage.setImageBitmap(bmp);

                            } catch (MalformedURLException e) {
                                e.printStackTrace();
                            }

                        }
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        viewHolder.openBadge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent q1 = new Intent(context, Badge.class);
                q1.putExtra("BadgeId", item.get_user_badge());
                context.startActivity(q1);
            }
        });

    }

      //  txtLevel = itemView.findViewById(R.id.level);
//        badgeImage = itemView.findViewById(R.id.badgeImage);

        // viewHolder.imgCover.setText(item.getCover());
        // viewHolder.imgCover.setImageResource(item.getCover());


    @Override
    public int getItemCount(){
        return myAlbumList.size();
    }

}