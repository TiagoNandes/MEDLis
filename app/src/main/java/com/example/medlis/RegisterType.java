package com.example.medlis;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.client.Firebase;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.Console;
import java.io.InputStream;
import java.net.URL;


public class RegisterType extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{

//        private static final String TAG = "SignInActivity";
//        private static final int RC_SIGN_IN = 9001;

 //       private GoogleApiClient mGoogleApiClient;
 private static final String TAG = "GoogleSignInActivity";
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private GoogleApiClient mGoogleApiClient;
    private ImageView mImageView;
    private TextView mTextViewProfile;
    private CallbackManager mCallbackManager;
    FirebaseFirestore fStore = FirebaseFirestore.getInstance();
    private Context context = RegisterType.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);
        final ImageButton email = findViewById(R.id.email);
        final LoginButton facebook = findViewById(R.id.buttonFacebookLogin);

        final TextView login = findViewById(R.id.login);
        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //     FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, Registo.class);
                startActivity(q1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, login.class);
                startActivity(q1);
            }
        });
       // findViewById(R.id.buttonFacebookLogin).setOnClickListener(this);
        facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Intent q1 = new Intent(RegisterType.this, FacebookLogin.class);
                // startActivity(q1);

                Intent q1 = new Intent(RegisterType.this, FacebookLogin.class);
                startActivityForResult(q1, RC_SIGN_IN);
            }
        });
        mImageView = findViewById(R.id.logo);
     //   mTextViewProfile = findViewById(R.id.profile);

        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setOnClickListener(this);

       // findViewById(R.id.sign_out_button).setOnClickListener(this);
        //findViewById(R.id.disconnect_button).setOnClickListener(this);

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                     user = FirebaseAuth.getInstance().getCurrentUser();
                     String userId = user.getUid();
                    final Query mQuery = fStore.collection("Users")
                            .whereEqualTo("idTagRead", userId);
                    mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            Log.d(TAG, "checkingIfTagExist: checking if tag exists");
                            Toast.makeText(context, task.toString(), Toast.LENGTH_LONG).show();

                            if (task.isSuccessful()) {
                                Toast.makeText(context, task.toString(), Toast.LENGTH_LONG).show();

                                for (DocumentSnapshot ds : task.getResult()) {
                                    String userNames = ds.getString("idTagRead");
                                    Toast.makeText(context, ds.toString(), Toast.LENGTH_LONG).show();
                                    if (userNames.equals(userId)) {

                                        Log.d(TAG, "checkingIfusernameExist: FOUND A MATCH -username already exists");

                                        Toast.makeText(context, "O utilizador já existe", Toast.LENGTH_SHORT).show();
                                        Intent q1 = new Intent(RegisterType.this, menu.class);
                                        q1.putExtra("userId", ds.getId());
                                        startActivity(q1);
                                    }
                                    else{

                                    }
                                }
                            }
                            //checking if task contains any payload. if no, then update
                            if (task.getResult().size() == 0) {
                                try {
                                    Log.d(TAG, "onComplete: MATCH NOT FOUND - username is available");
                                    Intent q1 = new Intent(RegisterType.this, RegisterSocialNetworks.class);
                                    startActivity(q1);
                                    //Updating new username............


                                } catch (NullPointerException e) {
                                    Log.e(TAG, "NullPointerException: " + e.getMessage());
                                }
                            }
                        }
                    });

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                updateUI(user);
            }
        };
        // Initialize Facebook Login button
     /*   mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.buttonFacebookLogin);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }
*/
     /*       @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                updateUI(null);
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                updateUI(null);
            }
        });*/
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

       try{ if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                // Google Sign In failed, update UI appropriately
                updateUI(null);
            }
        }}
       catch(Exception e ){
           mCallbackManager.onActivityResult(requestCode, resultCode, data);
       }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());
        //showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
              /*      mTextViewProfile.setTextColor(Color.RED);
                    mTextViewProfile.setText(task.getException().getMessage());*/
                } else {
//                    mTextViewProfile.setTextColor(Color.DKGRAY);
                }
              //  hideProgressDialog();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
       // alert.setMessage(R.string.logout);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Firebase sign out
                mAuth.signOut();
                // Google sign out
                Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                updateUI(null);
                            }
                        }
                );
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    private void revokeAccess() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
       // alert.setMessage(R.string.logout);
        alert.setCancelable(false);
        alert.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Firebase sign out
                mAuth.signOut();
                // Google revoke access
                Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient).setResultCallback(
                        new ResultCallback<Status>() {
                            @Override
                            public void onResult(@NonNull Status status) {
                                updateUI(null);
                            }
                        }
                );
            }
        });
        alert.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alert.show();
    }

    private void updateUI(FirebaseUser user) {
        if (user != null) {
            if (user.getPhotoUrl() != null) {
                new DownloadImageTask().execute(user.getPhotoUrl().toString());
            }
            String name = user.getDisplayName();
      /*      mTextViewProfile.setText("DisplayName: " + user.getDisplayName());
            mTextViewProfile.append("\n\n");
            mTextViewProfile.append("Email: " + user.getEmail());
            mTextViewProfile.append("\n\n");
            mTextViewProfile.append("Firebase ID: " + user.getUid());*/
            Toast.makeText(this, "DisplayName: " + name, Toast.LENGTH_LONG).show();
            Intent q1 = new Intent(RegisterType.this, menu.class);
            startActivity(q1);
            findViewById(R.id.sign_in_button).setVisibility(View.GONE);
          //  findViewById(R.id.sign_out_and_disconnect).setVisibility(View.VISIBLE);
        } else {
           // mImageView.getLayoutParams().width = (getResources().getDisplayMetrics().widthPixels / 100) * 64;
           // mImageView.setImageResource(R.mipmap.authentication);
         //   mTextViewProfile.setText(null);
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
         //   findViewById(R.id.sign_out_and_disconnect).setVisibility(View.GONE);
        }
      //  hideProgressDialog();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                signIn();
                break;
         /*   case R.id.sign_out_button:
                signOut();
                break;
            case R.id.disconnect_button:
                revokeAccess();
                break;*/
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            Bitmap mIcon = null;
            try {
                InputStream in = new URL(urls[0]).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if (result != null) {
/*                mImageView.getLayoutParams().width = (getResources().getDisplayMetrics().widthPixels / 100) * 24;
                mImageView.setImageBitmap(result);*/
            }
        }
    }
    // auth_with_facebook
    private void handleFacebookAccessToken(AccessToken token) {
        //https://github.com/jirawatee/FirebaseAuth-Android/blob/master/app/src/main/java/com/example/auth/FacebookLoginActivity.java
        Log.d(TAG, "handleFacebookAccessToken:" + token);


        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                if (!task.isSuccessful()) {
                   /* mTextViewProfile.setTextColor(Color.RED);
                    mTextViewProfile.setText(task.getException().getMessage());*/
                } else {
                   // mTextViewProfile.setTextColor(Color.DKGRAY);
                    Intent q1 = new Intent(RegisterType.this, menu.class);
                    startActivity(q1);
                }

            }
        });
    }
/*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_type);
        final ImageButton email = findViewById(R.id.email);
        final LoginButton facebook = findViewById(R.id.buttonFacebookLogin2);

        final TextView login = findViewById(R.id.login);
       // mAuth = FirebaseAuth.getInstance();
        email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           //     FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, Registo.class);
                startActivity(q1);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               // FirebaseAuth.getInstance().signOut();

                Intent q1 = new Intent(RegisterType.this, login.class);
                startActivity(q1);
            }
        });

        facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


               // Intent q1 = new Intent(RegisterType.this, FacebookLogin.class);
               // startActivity(q1);

                Intent q1 = new Intent(RegisterType.this, FacebookLogin.class);
                startActivityForResult(q1, RC_SIGN_IN);
            }
        });
        //Google
        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.

        createRequest();

        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                signIn();
                //startActivityForResult(signInIntent, RC_SIGN_IN);
                // Using a redirect.
          /*      mAuth.getRedirectResult().then(function(result) {
                    if (result.credential) {
                        // This gives you a Google Access Token.
                        var token = result.credential.accessToken;
                    }
                    var user = result.user;
                });

// Start a sign in process for an unauthenticated user.
                var provider = new firebase.auth.GoogleAuthProvider();
                provider.addScope('profile');
                provider.addScope('email');
                firebase.auth().signInWithRedirect(provider);*/
            }
/*

        });
    }

    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        // [END configure_signin]

        // [START build_client]
        // Build a GoogleApiClient with access to the Google Sign-In API and the
        // options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)                .enableAutoManage(this /* FragmentActivity */
//, this /* OnConnectionFailedListener */)
       /*         .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        SignInButton signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        // [END customize_button]
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void updateUI(GoogleSignInAccount account) {
        Log.d(TAG, String.valueOf(account));
        mAuth = FirebaseAuth.getInstance();
        Toast.makeText(RegisterType.this, mAuth.toString(),
                Toast.LENGTH_SHORT).show();

    }

   @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getId();

            Toast.makeText(RegisterType.this, idToken +"\n"+ account.getServerAuthCode() + "\n"+ account.getIdToken() ,
                    //retorna valor, null, null
                    Toast.LENGTH_LONG).show();
            firebaseAuthWithGoogle(idToken, account);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(RegisterType.this, "signInResult:failed code=" + e.getStatusCode(),
                    Toast.LENGTH_LONG).show();
            updateUI(null);
        }
    }

   private void firebaseAuthWithGoogle(String idToken, GoogleSignInAccount mGoogleSignInClient) {
       Toast.makeText(RegisterType.this, idToken,
               Toast.LENGTH_LONG).show(); //retorna valor

        if(idToken != null && idToken != "") {
            AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
            mAuth = FirebaseAuth.getInstance();
        //    AuthCredential credential = mAuth.GoogleAuthProvider.credential(idToken);
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");
                                // Signed in successfully, show authenticated UI.
                                updateUI(mGoogleSignInClient);
                                FirebaseUser user = mAuth.getCurrentUser();
                                Toast.makeText(RegisterType.this, "signInWithCredential:success",
                                        Toast.LENGTH_SHORT).show();
                                  Intent q1 = new Intent(RegisterType.this, menu.class);
                                  startActivity(q1);

                            } else {
                                // If sign in fails, display a message to the user. ERROR malformed or has expired
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                Toast.makeText(RegisterType.this, "signInWithCredential:failure"+ task.getException(),
                                        Toast.LENGTH_LONG).show();
                                //    Snackbar.make(mBinding.mainLayout, "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                                updateUI(null);
                            }
                        }
                    });
        }
    }*/

//}
