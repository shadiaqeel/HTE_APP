package com.yurmouky.hijjawi;

import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;


import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.yurmouky.hijjawi.NotificationService;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private Button signout;
    private FirebaseAuth mAuth;
    private FirebaseHelper helper;
    public DatabaseReference ref;
    public DatabaseReference ref_version;
    public ArrayList<String>   Topics = new ArrayList<String>();

    private  DatabaseReference Ref_user;

    public ArrayList<String> getTopics() {return Topics;}


    private TabLayout tablayout; /**Tabs*/
    private ViewPager  viewPager; /**Tabs*/
    private  ViewPagerAdapter adapter; /**Tabs*/

    public int version =0;

    ValueEventListener version_listener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);







            tablayout = (TabLayout) findViewById(R.id.tab_layout); /**Tabs*/
            viewPager = (ViewPager) findViewById(R.id.viewPager);   /**Tabs*/

            mAuth = FirebaseAuth.getInstance();



            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
             Ref_user = firebaseDatabase.getReferenceFromUrl("https://hijjawi-yurmouky.firebaseio.com/Users/" + mAuth.getCurrentUser().getUid());
            String token = FirebaseInstanceId.getInstance().getToken();

            Ref_user.push();
            Ref_user.child("Token").setValue(token);

            ref_version =firebaseDatabase.getReference("version");




             version_listener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    if(!dataSnapshot.getValue(true).toString().equals(version+""))
                    {

                        //LoginManager.getInstance().logOut();
                        //FirebaseAuth.getInstance().signOut();
                        stopService();
                        startActivity(new Intent(MainActivity.this,block.class));
                        finish();

                    }

                    else
                    {


                        startService();



                    }

                    Ref_user.child("version").removeEventListener(this);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            };







            ref_version.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    version = Integer.parseInt(dataSnapshot.getValue(true).toString());
                    Ref_user.child("version").addListenerForSingleValueEvent(version_listener);
                    ref_version.removeEventListener(this);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });








            /** START
             TABS Dialog __________________________________________________________**/


            adapter = new ViewPagerAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);


           // final TabLayout.Tab tab0 = tablayout.newTab().setText("Notifications");
            // final TabLayout.Tab tab1 = tablayout.newTab().setText("Main");

            //tablayout.addTab(tab0, 0);
            //tablayout.addTab(tab1, 1);

          //  tablayout.setTabTextColors(ContextCompat.getColorStateList(this, R.color.tab_selector));
            // tablayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.colorAccent));

            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

            tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    viewPager.setCurrentItem(tab.getPosition());
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }

            });


            /** END
             TABS Dialog ________________________________________________________**/




            update();





    }catch(Exception e)
    {
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }
    }


    public void startService() {

        Intent intent =new Intent(this,NotificationService.class);
        //intent.putStringArrayListExtra("Topics",Topics);

        String s =(FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid())).toString();
        intent.putExtra("Ref",s);
        startService(intent);

    }


    public void stopService() {

        stopService(new Intent(this,NotificationService.class));
    }

    public void insert(String topic )
    {
        DatabaseReference rref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Topics");
        rref.push();
        rref.child(topic).setValue(1);


    }
    public void remove (String topic)
    {

        DatabaseReference rref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Topics");
        rref.push();
        rref.child(topic).removeValue();
    }




    public void update()
    {

        ref = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser().getUid()).child("Topics");
        ref.push();




        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Topics.add(dataSnapshot.getKey());


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                Topics.add(dataSnapshot.getKey());




            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                Topics.remove(dataSnapshot.getKey());

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }



}