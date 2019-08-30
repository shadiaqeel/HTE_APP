package com.yurmouky.hijjawi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by dell on 12/09/2017.
 */

public class Tab0Fragment extends Fragment {



    ListView lv,lv_t;
    DatabaseReference db;
    FirebaseHelper helper;
    PostAdapter mAdapter;
    TopicAdapter mAdapter_t;
    Button btn,ok_btn;
    View v;
    AlertDialog mDialog;

    ArrayList<String> Topics = new ArrayList<String>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {





            v = inflater.inflate(R.layout.tab0, container, false);


            lv = (ListView) v.findViewById(R.id.lv_posts);

            db = FirebaseDatabase.getInstance().getReference("Posts");
            helper = new FirebaseHelper(((MainActivity) getActivity()).getTopics(), db);
            mAdapter = new PostAdapter(v.getContext(), helper.retrieve());
            helper.setAdapter(mAdapter);

            lv.setAdapter(mAdapter);


            btn = (Button) v.findViewById(R.id.select_notifi);

            //lv_t.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


            /**_______________________________________*/
            View topics_view = getActivity().getLayoutInflater().inflate(R.layout.topics, null);
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
            mBuilder.setView(topics_view);
            // mBuilder.setTitle("Select Topics");
            lv_t = (ListView) topics_view.findViewById(R.id.lv_Topic);
            ok_btn = (Button) topics_view.findViewById(R.id.ok_btn);

            Topic_Helper helper_topic = new Topic_Helper(topics_view.getContext(), FirebaseDatabase.getInstance().getReference("Topics"));

            mAdapter_t = new TopicAdapter(topics_view.getContext(), helper_topic.retrieve());



        helper_topic.setAdapter(mAdapter_t);
            lv_t.setAdapter(mAdapter_t);

            mDialog = mBuilder.create();

            /**_______________________________________*/


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    mDialog.show();

                    ok_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDialog.dismiss();
                           // ((MainActivity)v.getContext()).update();
                          //  ((MainActivity) v.getContext()).startService();
                           // mAdapter_t.notifyDataSetChanged();

                        }
                    });


                }
            });



        return v;

    }


}
