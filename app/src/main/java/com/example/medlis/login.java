package com.example.medlis;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {
    private static final String TAG ="TAG" ;
    View mEmail;
    EditText mPassword;
    FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Button button = findViewById(R.id.button);
        final TextView register = findViewById(R.id.textView);
        //findViewById(R.id.button).setOnClickListener((View.OnClickListener) this);
        EditText mEmail =  (EditText) findViewById(R.id.email);
        EditText mPassword =  (EditText) findViewById(R.id.password);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                signIn(mEmail.getText().toString(), mPassword.getText().toString());
            }
        });

        // Initialize Firebase Auth
       mAuth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(login.this, Registo.class);
                startActivity(q1);
            }
        });
    }
    // [START on_start_check_user]
/*    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }*/
   private void signIn(String email, String password) {

        Log.d(TAG, "signIn:" + email);
        //if (!validateForm()) {
          //  return;
        //}

        //showProgressDialog();

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                         if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                             Intent q1 = new Intent(login.this, menu.class);
                             startActivity(q1);
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // [START_EXCLUDE]
                        //if (!task.isSuccessful()) {
                          //  mStatusTextView.setText(R.string.auth_failed);
                        //}
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]

    }

}
