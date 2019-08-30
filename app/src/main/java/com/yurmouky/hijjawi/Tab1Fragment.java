package com.yurmouky.hijjawi;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by dell on 12/09/2017.
 */

public class Tab1Fragment extends Fragment {

    private Button signout;
    private FloatingActionButton Resources_btn,youtube_btn,gpa_btn;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab1,container,false);



        signout = (Button) v.findViewById(R.id.sign_out);
        Resources_btn =(FloatingActionButton) v.findViewById(R.id.resource_btn);
        youtube_btn =(FloatingActionButton) v.findViewById(R.id.youtube_btn);
        gpa_btn =(FloatingActionButton) v.findViewById(R.id.GPA_btn);




        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LoginManager.getInstance().logOut();
                FirebaseAuth.getInstance().signOut();

                try {

                    ((MainActivity)getActivity()).stopService();
                    getActivity().finish();
                    startActivity(new Intent(getActivity(), LoginActivity.class));


                }catch (Exception e)
                {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT).show();
                }


            }
        });

        Resources_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


                startActivity(new Intent(getActivity(), resources.class));



            }
        });

        youtube_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCIE4tdqF5f_NBOzubSeXFOQ/playlists")));

            }
        });



        gpa_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

               // startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCIE4tdqF5f_NBOzubSeXFOQ/playlists")));

            }
        } );




        return v;

    }

}
