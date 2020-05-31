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

public class Register2 extends AppCompatActivity {
    String name="";
    String phone="";
    String email ="";
    private static final String TAG ="TAG" ;
    private FirebaseAuth mAuth;// ...
    EditText mPassword;
    EditText mUserNumber;
    EditText mConfirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
// Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register2);

        final TextView login = findViewById(R.id.login);
        final Button register = findViewById(R.id.register);
        mUserNumber =  (EditText) findViewById(R.id.userNumber);
        mPassword =  (EditText) findViewById(R.id.password);
        mConfirmPassword =  (EditText) findViewById(R.id.confirmPassword);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            phone = extras.getString("phone");
            email = extras.getString("email");
            //The key argument here must match that used in the other activity
        }
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent q1 = new Intent(Register2.this, login.class);
                startActivity(q1);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean result =mPassword.getText().toString().equalsIgnoreCase(mConfirmPassword.getText().toString());

                if(mPassword.getText().toString().equalsIgnoreCase(mConfirmPassword.getText().toString())){
                    Log.d(TAG, "passmatch!");
                    Log.d(TAG, "createAccount:" + email);
                    String pass = mPassword.getText().toString();

                    createAccount(email, pass);
                }
                else{

                    Log.d(TAG, "NOOOO!" +result);

                }


            }
        });
    }
    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    //name, phone, mUserNumber.getText().toString()
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                            Intent q1 = new Intent(Register2.this, login.class);
                            startActivity(q1);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(Register2.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);@
                        }

                        // [START_EXCLUDE]
                        //hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
        // [END create_user_with_email]
    }
}
