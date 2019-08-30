package com.yurmouky.hijjawi;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class resource_details extends AppCompatActivity {


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;





    String Special_Dir;
    String url;

    private ExpandableListView expLV;
    private ExpLVAdapter adapter;
    private ArrayList<String> listCategorias;
    private Map<String,ArrayList<String>> mapChild;
    private FirebaseHelper_details helper;
    private ClipboardManager clipboard;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resource_details);

try {





    Intent detail = getIntent();
    Special_Dir = detail.getStringExtra("Special");
    mDatabaseReference = FirebaseDatabase.getInstance().getReference("Resources").child(Special_Dir);

    clipboard = (ClipboardManager) getSystemService(this.CLIPBOARD_SERVICE);


    expLV = (ExpandableListView) findViewById(R.id.expLV);


    helper = new FirebaseHelper_details(this, mDatabaseReference);

    listCategorias = helper.retrieve();
    mapChild = helper.getMapchild();

    adapter = new ExpLVAdapter(this,listCategorias , mapChild);

    expLV.setAdapter(adapter);
    helper.setAdapter(adapter);






}catch (Exception e)
{
    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
}
    }





    public void copy(int groupPosition, int childPosition)
    {
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Resources").child(Special_Dir).child(listCategorias.get(groupPosition)).child(mapChild.get(listCategorias.get(groupPosition)).get(childPosition));



        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                uri uri ;
                uri = dataSnapshot.getValue(uri.class);
                ClipData clip = ClipData.newPlainText("Copied",uri.getUri());
                clipboard.setPrimaryClip(clip);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });





        Toast.makeText(this,"Copied", Toast.LENGTH_SHORT).show();



    }




    public void download(int groupPosition, int childPosition)
    {



        mDatabaseReference = FirebaseDatabase.getInstance().getReference("Resources").child(Special_Dir).child(listCategorias.get(groupPosition)).child(mapChild.get(listCategorias.get(groupPosition)).get(childPosition));

        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {

                uri uri ;
                uri = dataSnapshot.getValue(uri.class);

                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri.getUri())));
                }catch (Exception e)
                {
                    Toast.makeText(resource_details.this,e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });




    }







}
