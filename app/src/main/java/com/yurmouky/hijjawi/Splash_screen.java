package com.yurmouky.hijjawi;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash_screen extends AppCompatActivity {

    private TextView text_logo;
    private ImageView logo;


    private  Thread timer;
    private boolean firsttime ;
    private DatabaseReference Ref;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        logo =(ImageView)findViewById(R.id.logo_splash);
        text_logo =(TextView)findViewById(R.id.text_logo);
        Animation  splash_anim = AnimationUtils.loadAnimation(this,R.anim.splash_transition);
        logo.startAnimation(splash_anim);
        text_logo.startAnimation(splash_anim);



       // final Intent i = new Intent(this,LoginActivity.class);







        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                mAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                Ref = firebaseDatabase.getReferenceFromUrl("https://hijjawi-yurmouky.firebaseio.com/Users/" );

                firsttime =true;
                if (user != null) {
                    // User is signed in



                    Ref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if( dataSnapshot.hasChild( mAuth.getCurrentUser().getUid())) {firsttime=false; }

                               done();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }



                    });

                    Log.d("Auth:", user.getUid());

                } else {
                    // User is signed out

                    finish();
                    startActivity(new Intent(Splash_screen.this, LoginActivity.class));

                }

            }
        };




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


    private  void done()
    {

        if(firsttime)
            startActivity(new Intent(Splash_screen.this, LoginActivity.class));
        else{ startActivity(new Intent(Splash_screen.this, MainActivity.class));}

        finish();
    }



}
