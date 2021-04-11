package com.example.medlis;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;


import java.util.HashMap;
import java.util.Map;

public class RegisterSocialNetworks extends AppCompatActivity {
    private Context context = RegisterSocialNetworks.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_social_networks);
        EditText mPhone  = (EditText) findViewById(R.id.userPhoneNumber);
        EditText mSocialNumber = (EditText) findViewById(R.id.NumUtente);
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //get username, email... atributos para o registo
                //mete tudo no documento do user - nutente, user, phone
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null){

                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("nUtente", mSocialNumber.getText().toString());
                    user1.put("name", user.getDisplayName());
                    user1.put("photoUrl",user.getPhotoUrl().toString());
                    user1.put("phoneNumber", mPhone.getText().toString());

                    FirebaseFirestore fStore = FirebaseFirestore.getInstance();

                    fStore.collection("Users").document(user.getUid())
                        .set(user1).addOnSuccessListener(command -> {
                    Intent q1 = new Intent(RegisterSocialNetworks.this, menu.class);
                    startActivity(q1);
                });

                }
                else{
                    Intent q1 = new Intent(RegisterSocialNetworks.this, RegisterType.class);
                    startActivity(q1);
                }

        }

        });
    }
}