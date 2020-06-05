package com.example.medlis;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

public class TakePictureActivity extends AppCompatActivity {
    static Uri capturedImageUri = null;
    //Firebase
    FirebaseStorage storage;
    StorageReference storageReference;
    private ImageView imageView;
    private Uri filePath;
    public static final int PICK_IMAGE = 0;
    private String selectedImagePath="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //selectImage(TakePictureActivity.this);
            }
        });
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


        final Button takePicture = findViewById(R.id.takePicture);
        final Button pickPicture = findViewById(R.id.pickPicture);

        takePicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent takePicture = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 1);

                Calendar cal = Calendar.getInstance();
                File file = new File(Environment.getExternalStorageDirectory(), (cal.getTimeInMillis() + ".jpg"));
                if (!file.exists()) {
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                } else {
                    file.delete();
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                capturedImageUri = Uri.fromFile(file);
                takePicture.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
                //startActivityForResult(takePicture, CAMERA_RESULT);
            }
        });
        pickPicture.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
      /*          Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

                Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
                getIntent.setType("image/*");

                Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickIntent.setType("image/*");

                Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

                startActivityForResult(chooserIntent, PICK_IMAGE);*/

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, 0);


            }
        });

    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageBitmap(imageBitmap);
            //saveImageInFirestore(imageView);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String imageName = user.getUid() + ".jpeg";
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference reference = storage.getReference();
            StorageReference newProfilePicRef = reference.child(imageName);
            //Bitmap newProfilePicBitmap = BitmapFactory.decodeResource(getResources(), );
            ByteArrayOutputStream boas = new ByteArrayOutputStream();

            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas);
            newProfilePicRef.putBytes(boas.toByteArray())
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(TakePictureActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if (requestCode == 0 && resultCode == RESULT_OK && resultCode !=RESULT_CANCELED) {




                    Uri selectedImageUri = data.getData();
                    selectedImagePath = getPath(selectedImageUri);
                    // here you can set the image
            Bitmap myBitmap = BitmapFactory.decodeFile(selectedImagePath);
            imageView.setImageBitmap(myBitmap);


           // InputStream inputStream = context.getContentResolver().openInputStream(data.getData());

          /*  Bundle extras = data.getExtras();
            File imgFile = new File(data.getData().toString());

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                imageView = (ImageView) findViewById(R.id.imageView2);
                imageView.setImageBitmap(myBitmap);

            //Bitmap imageBitmap = (Bitmap) data.getData();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView = (ImageView) findViewById(R.id.imageView2);
            imageView.setImageBitmap(imageBitmap);
            //saveImageInFirestore(imageView);

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            String imageName = user.getUid() + ".jpeg";
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference reference = storage.getReference();
            StorageReference newProfilePicRef = reference.child(imageName);
            //Bitmap newProfilePicBitmap = BitmapFactory.decodeResource(getResources(), );
            ByteArrayOutputStream boas = new ByteArrayOutputStream();

// Create file metadata including the content type
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .build();

            //imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, boas);
            newProfilePicRef.putBytes(boas.toByteArray(), metadata)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(TakePictureActivity.this, "Upload Successful", Toast.LENGTH_SHORT).show();
                        }
                    });*/
        }
    }


}

