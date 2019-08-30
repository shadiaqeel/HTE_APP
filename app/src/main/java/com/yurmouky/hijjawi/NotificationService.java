package com.yurmouky.hijjawi;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class NotificationService extends Service {

   // ArrayList<String> Topics;
    DatabaseReference Ref;
    ChildEventListener listener;

    public NotificationService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
try {
    startService(new Intent(NotificationService.this, FirebaseService.class));
    startService(new Intent(NotificationService.this, MyFirebaseMessagingService.class));


    listener = new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {


            FirebaseMessaging.getInstance().subscribeToTopic(dataSnapshot.getKey());


        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            FirebaseMessaging.getInstance().subscribeToTopic(dataSnapshot.getKey());

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

            FirebaseMessaging.getInstance().unsubscribeFromTopic(dataSnapshot.getKey());

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

}catch (Exception e)
{
    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    stopSelf();
}

    }

    @Override
    public int onStartCommand(Intent intent, int flags , int startId) {



        Toast.makeText(NotificationService.this,"Started", Toast.LENGTH_SHORT).show();
        RefLisenter(intent.getStringExtra("Ref"));



        return START_NOT_STICKY;
    }






    @Override
    public void onDestroy() {

        try {


            Ref.removeEventListener(listener);
            stopService(new Intent(NotificationService.this, FirebaseService.class));
            stopService(new Intent(NotificationService.this, MyFirebaseMessagingService.class));


        }catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        super.onDestroy();



    }

    @Override
    public IBinder onBind(Intent intent) {

        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public void RefLisenter(String db)
    {
        try {


            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            Ref = firebaseDatabase.getReferenceFromUrl(db).child("Topics");
            Ref.push();

            Ref.addChildEventListener(listener);
        }catch (Exception e)
        {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            stopSelf();
        }

    }


}
