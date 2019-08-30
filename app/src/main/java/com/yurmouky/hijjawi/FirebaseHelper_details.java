package com.yurmouky.hijjawi;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 20/09/2017.
 */



public class FirebaseHelper_details {



    DatabaseReference db;
    Context c;
    ExpLVAdapter mAdapter;


    ArrayList<String> Parents;
    Map<String, ArrayList<String>> mapchild ;

    int size = 0;



    public FirebaseHelper_details(Context c, DatabaseReference db) {
        this.db = db;
        this.c = c;


        Parents = new ArrayList<String>();
        mapchild = new HashMap<String, ArrayList<String>>();


    }


    private void fetchData(DataSnapshot dataSnapshot) {

        try {
            ArrayList<String> Childs = new ArrayList<String>();
            Childs.clear();

            //Parents.clear();


            Parents.add(dataSnapshot.getKey());

            for (DataSnapshot data : dataSnapshot.getChildren()) {
                Childs.add(data.getKey());
            }


            mapchild.put(Parents.get(size), Childs);
            size++;




            if(mAdapter!=null)
                mAdapter.notifyDataSetChanged();


        }catch (Exception e)
        {
            Toast.makeText(c, e.toString(), Toast.LENGTH_SHORT).show();
        }



    }


    public ArrayList<String> retrieve() {

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
            }
            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(c, "error", Toast.LENGTH_SHORT).show();
                mAdapter.notifyDataSetChanged();
            }

        });


        return Parents;
    }

    public Map<String, ArrayList<String>> getMapchild() {
        
        return mapchild;

    }


    public void setAdapter(ExpLVAdapter A)
    {
        mAdapter=A;

    }




}
