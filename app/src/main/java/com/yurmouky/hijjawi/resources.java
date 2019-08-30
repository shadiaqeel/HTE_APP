package com.yurmouky.hijjawi;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class resources extends AppCompatActivity {

    //DatabaseReference db;
    FirebaseHelper helper;
    CustomAdapter adapter;
    ListView lv;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            try
            {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resources);


                ArrayList<card_view> card_views =new ArrayList<>();
                card_views.add(new card_view("Civil Engineering",R.drawable.civil));
                card_views.add(new card_view("Computer Engineering",R.drawable.cpu));
                card_views.add(new card_view("Communication Engineering",R.drawable.communication));
                card_views.add(new card_view("Power Engineering",R.drawable.power));
                card_views.add(new card_view("Electronic Engineering",R.drawable.electronic));
                card_views.add(new card_view("Biomedical engineering",R.drawable.biomedical));
                card_views.add(new card_view("Architectural Engineering",R.drawable.arch));
                card_views.add(new card_view("Industrial Engineering",R.drawable.industrial));





                lv = (ListView) findViewById(R.id.lv);
        //INITIALIZE FIREBASE DB
        //db = FirebaseDatabase.getInstance().getReference("Resoures");


        //helper = new FirebaseHelper(getBaseContext(),db);
        //ADAPTER

               // adapter = new CustomAdapter(this, helper.retrieve());

                adapter = new CustomAdapter(this,card_views);

               // helper.setAdapter(adapter);
                lv.setAdapter(adapter);




            }catch(Exception e)
            {
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }

        }




    }



