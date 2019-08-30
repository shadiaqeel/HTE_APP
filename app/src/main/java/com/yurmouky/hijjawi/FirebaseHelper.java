package com.yurmouky.hijjawi;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
// import com.tutorials.hp.firebaselistviewmulti_items.m_Model.Spacecraft;
import java.util.ArrayList;
/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. RETRIEVE
 * 2.RETURN AN ARRAYLIST
 */
public class FirebaseHelper {
    DatabaseReference db;
    Context c;
    PostAdapter mAdapter;
    ChildEventListener listener;




    ArrayList<post> posts=new ArrayList<>();
    ArrayList<String>Topics =new ArrayList<>();
    /*
 PASS DATABASE REFRENCE
  */



    public FirebaseHelper(ArrayList<String>Topics, DatabaseReference db) {
        this.db = db;
        this.Topics=Topics;

    }

        //IMPLEMENT FETCH DATA AND FILL ARRAYLIST

    private void fetchData(DataSnapshot dataSnapshot) {



    post Post = dataSnapshot.getValue(post.class);
        Post.setKey(dataSnapshot.getKey());



        if( Topics.contains(Post.getTopic()))
         posts.add(0,Post);



        if(mAdapter!=null)
            mAdapter.notifyDataSetChanged();


    }



    //RETRIEVE
    public ArrayList<post> retrieve() {



       listener=new ChildEventListener() {
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
              //  refresh();
                if (search(dataSnapshot)!=-1)
               posts.remove(search(dataSnapshot));


                if(mAdapter!=null)
                    mAdapter.notifyDataSetChanged();


            }



            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        };


        db.addChildEventListener(listener);

        return posts;
    }

    public void setAdapter(PostAdapter A)
    {
        mAdapter=A;

    }

    public void refresh()
    {

        db.removeEventListener(listener);
        posts.clear();
        db.addChildEventListener(listener);

        if(mAdapter!=null)
            mAdapter.notifyDataSetChanged();

    }

    public int  search(DataSnapshot dataSnapshot)

    {

        for(int i=0;i<posts.size();i++)
        {

            if(posts.get(i).getKey().toString().equals(dataSnapshot.getKey()))
                return i;


        }
        return -1;

    }


}

