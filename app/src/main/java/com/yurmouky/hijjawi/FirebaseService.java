package com.yurmouky.hijjawi;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by dell on 23/09/2017.
 */

public class FirebaseService extends FirebaseInstanceIdService {



    private FirebaseAuth mAuth;
    private DatabaseReference Ref;
    FirebaseDatabase firebaseDatabase ;



    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.


        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("Token", "Refreshed token: " + refreshedToken);
        Toast.makeText(this,refreshedToken , Toast.LENGTH_SHORT).show();

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        sendRegistrationToServer(refreshedToken);
    }


    private void sendRegistrationToServer(String refreshedToken) {






    }




}
