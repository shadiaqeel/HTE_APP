package com.yurmouky.hijjawi;

import android.content.Context;
import android.widget.Adapter;
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



public class Topic_Helper {



    DatabaseReference db;
    Context c;

    ArrayList<String> Topics;

    TopicAdapter mAdapter;


    int size = 0;



    public Topic_Helper(Context c, DatabaseReference db) {
        this.db = db;
        this.c = c;


        Topics = new ArrayList<String>();

    }


    private void fetchData(DataSnapshot dataSnapshot) {


            Topics.add(dataSnapshot.getKey());


        if(mAdapter!=null)
        {mAdapter.notifyDataSetChanged();}
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
                Topics.remove(dataSnapshot.getKey());
                if(mAdapter!=null)
                {mAdapter.notifyDataSetChanged();}

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(c, "error", Toast.LENGTH_SHORT).show();

            }

        });


        return Topics;
    }




    public void setAdapter(TopicAdapter A)
    {
        mAdapter=A;

    }




}
