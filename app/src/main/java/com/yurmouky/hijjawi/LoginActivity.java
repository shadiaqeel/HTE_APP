package com.yurmouky.hijjawi;


import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView;


import com.facebook.AccessToken;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.facebook.places.internal.LocationPackageManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.CallbackManager;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;


public class LoginActivity extends AppCompatActivity {

    // !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private AutoCompleteTextView username_field;
    private EditText password_field;
    private Button signin_btn, signup_btn;
    private LoginButton facebook_btn;


    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager callbackManager;

    private AlertDialog signup_Alert;
    private AlertDialog register_Alert;

    private boolean ID_Exist ;

    String email;
    String password;
    String special;

    DatabaseReference Ref;
    DatabaseReference Ref_ID;
    DatabaseReference Ref_user;

    String token ;





    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            progressDialog = new ProgressDialog(this);


            mAuth = FirebaseAuth.getInstance();





            username_field = (AutoCompleteTextView) findViewById(R.id.username);
            password_field = (EditText) findViewById(R.id.password);
            signin_btn = (Button) findViewById(R.id.sign_in_button);
            signup_btn = (Button) findViewById(R.id.signup);
            facebook_btn = (LoginButton) findViewById(R.id.signin_facebook);   /**Facebook */


            FacebookSdk.sdkInitialize(getApplicationContext()); /**Facebook */

            callbackManager = CallbackManager.Factory.create();            /**Facebook */
            facebook_btn.setReadPermissions("email", "public_profile");  /**Facebook */


            /** START
             SignUp Dialog __________________________________________________________**/


            signup_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    View change_view = getLayoutInflater().inflate(R.layout.activity_signup, null);
                    AlertDialog.Builder signup_builder = new AlertDialog.Builder(LoginActivity.this);
                    signup_builder.setView(change_view);
                    signup_Alert = signup_builder.create();
                    signup_Alert.show();


                    final AutoCompleteTextView username1 = (AutoCompleteTextView) change_view.findViewById(R.id.username);
                    final EditText Pass = (EditText) change_view.findViewById(R.id.pass);
                    final EditText ConfirmPass = (EditText) change_view.findViewById(R.id.confirmpass);
                    final Button sign_up_btn = (Button) change_view.findViewById(R.id.sign_up_btn);


                    sign_up_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {


                            if (!(ConfirmPass.getText().toString().equals(Pass.getText().toString()))) {
                                Toast.makeText(LoginActivity.this, "Confirm password did not match ! ", Toast.LENGTH_SHORT).show();
                                Pass.setText("");
                                ConfirmPass.setText("");
                            } else {

                                email = username1.getText().toString();
                                password = Pass.getText().toString();


                                sign_up(v);
                                register();

                            }


                        }
                    });


                }

            });


            /** END
             SignUp Dialog ________________________________________________________**/


            mAuthListener = new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                    Ref = firebaseDatabase.getReferenceFromUrl("https://hijjawi-yurmouky.firebaseio.com/Users/" );

                    if (user != null) {
                        // User is signed in


                        progressDialog.setTitle("Please Wait");
                        progressDialog.setMessage("Signing in .....");
                        progressDialog.show();
                        Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                progressDialog.cancel();
                                if( dataSnapshot.hasChild( mAuth.getCurrentUser().getUid())) {


                                    finish();
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                }else
                                {

                                    register();
                                }


                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }



                        });

                        Log.d("Auth:", user.getUid());

                    } else {
                        // User is signed out

                    }

                }
            };


            /**Facebook */
            facebook_btn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Log.d("Auth: ", "facebook:onSuccess:" + loginResult);
                    handleFacebookAccessToken(loginResult.getAccessToken());


                }

                @Override
                public void onCancel() {
                    Log.d("Auth: ", "facebook:onCancel");
                    // ...
                }

                @Override
                public void onError(FacebookException error) {
                    Log.d("Auth: ", "facebook:onError", error);
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    // ...
                }
            });


            signin_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    sign_in(v);
                }
            });


        }catch (Exception e )
        {
            Toast.makeText(this, e.toString() , Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }


    public void sign_in(View v) {

        String username = username_field.getText().toString();
        String password = password_field.getText().toString();


        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

            Toast.makeText(this, "Fields are empty.     ", Toast.LENGTH_SHORT).show();
        } else {


            //  mAuth = FirebaseAuth.getInstance();
            mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("Auth:", "signInWithEmail:onComplete:" + task.isSuccessful());

                    // If sign in fails, display a message to the user. If sign in succeeds
                    // the auth state listener will be notified and logic to handle the
                    // signed in user can be handled in the listener.
                    if (!task.isSuccessful()) {
                        Log.w("Auth:", "signInWithEmail:failed", task.getException());
                        Toast.makeText(LoginActivity.this, "signInWithEmail:failed", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "signInWithEmail:successed", Toast.LENGTH_SHORT).show();
                    }

                    // ...
                }
            });


        }
    }


    public void sign_up(View v) {


        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("Auth : ", "createUserWithEmail:onComplete:" + task.isSuccessful());

                // If sign in fails, display a message to the user. If sign in succeeds
                // the auth state listener will be notified and logic to handle the
                // signed in user can be handled in the listener.
                if (!task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Could not register , please try again", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(LoginActivity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    signup_Alert.cancel();

                }

                // ...
            }
        });
    }


    public void register() {


        View register_view = getLayoutInflater().inflate(R.layout.registration, null);
        AlertDialog.Builder register_builder = new AlertDialog.Builder(LoginActivity.this);
        register_builder.setView(register_view);
        register_Alert = register_builder.create();
        register_Alert.show();
        mAuth.removeAuthStateListener(mAuthListener);


        final AutoCompleteTextView firstname = (AutoCompleteTextView) register_view.findViewById(R.id.FistName);
        final AutoCompleteTextView lastname = (AutoCompleteTextView) register_view.findViewById(R.id.LastName);
        final AutoCompleteTextView uniID = (AutoCompleteTextView) register_view.findViewById(R.id.uni_ID);

        final Button register_btn = (Button) register_view.findViewById(R.id.register);
        final Spinner Specials = (Spinner) register_view.findViewById(R.id.Specializations);


        special = null;


        ArrayAdapter<String> spAdapter = new ArrayAdapter<String>(LoginActivity.this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Specializations));
        spAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Specials.setAdapter(spAdapter);

        Specials.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i != 0) {
                    special = adapterView.getItemAtPosition(i).toString();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {


            }

        });

        try {

            register_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean valid = true;

                    if (firstname.getText().toString().trim().isEmpty() || firstname.getText().toString().trim().length() > 32) {
                        firstname.setError("please Enter valid name");
                        valid = false;
                    }
                    if (lastname.getText().toString().trim().isEmpty() || lastname.getText().toString().trim().length() > 32) {
                        lastname.setError("please Enter valid name");
                        valid = false;
                    }

                    if (special==null) {
                        Toast.makeText(LoginActivity.this, "Select specialization", Toast.LENGTH_LONG).show();
                        valid = false;
                    }

                    if (uniID.getText().toString().trim().isEmpty() || uniID.getText().toString().trim().length() != 10||(Integer.parseInt(uniID.getText().toString())<2013000000) || (Integer.parseInt(uniID.getText().toString())>2018000000)) {
                        uniID.setError("please Enter valid number");

                        valid = false;
                    }





                    if (!valid) {
                        Toast.makeText(LoginActivity.this, "signup has failed", Toast.LENGTH_SHORT).show();
                    } else {



                        try {

                            progressDialog.setMessage("Registering .... ");
                            progressDialog.show();

                            token = FirebaseInstanceId.getInstance().getToken();
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                            Ref_user = firebaseDatabase.getReferenceFromUrl("https://hijjawi-yurmouky.firebaseio.com/Users/" + mAuth.getCurrentUser().getUid());


                            Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    ID_Exist=false;
                                    for(DataSnapshot child : dataSnapshot.getChildren()) { if (child.hasChild(uniID.getText().toString())) {ID_Exist=true;}}

                                    if(ID_Exist)
                                    { uniID.setError("the number is exist");} else {
                                        Ref_user.push();
                                        Ref_user.child("First ").setValue(firstname.getText().toString());
                                        Ref_user.child("second").setValue(lastname.getText().toString());
                                        //  Ref_user.child("ID").setValue(uniID.getText().toString());
                                        Ref_user.child(uniID.getText().toString()).setValue("ID");
                                        //Ref_user.child("special").setValue(special);
                                        Ref_user.child("version").setValue(1);
                                        Ref_user.child("Token").setValue(token);
                                        Ref_user.child("special").setValue(special);
                                        Ref_user.child("Topics").child((special.toString().split(" "))[0]).setValue(1);
                                        Ref_user.child("Topics").child("HTE").setValue("hte");


                                        finish();
                                        startActivity(new Intent(LoginActivity.this, MainActivity.class));


                                    }

                                    progressDialog.cancel();
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });







                        } catch (Exception e) {
                            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                        }







                    }

                }
            });
        }catch (Exception e)
        {
            Toast.makeText(LoginActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * Facebook
     */
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("Auth:", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Auth:", "signInWithCredential:success");
                    //  FirebaseUser user = mAuth.getCurrentUser();
                    mAuth.addAuthStateListener(mAuthListener);

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("Auth:", "signInWithCredential:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();

                }

                // ...
            }
        });

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data);

        Toast.makeText(this, "facebook"+data.toString(), Toast.LENGTH_SHORT).show();


    }




}




