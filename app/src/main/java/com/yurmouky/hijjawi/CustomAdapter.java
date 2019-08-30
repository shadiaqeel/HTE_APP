package com.yurmouky.hijjawi;

/**
 * Created by dell on 15/09/2017.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapter extends BaseAdapter{
    Context c;
    ArrayList<card_view> card_views;
    public CustomAdapter(Context c, ArrayList<card_view> card_views) {
        this.c = c;
        this.card_views = card_views;


    }
    @Override
    public int getCount() {
        return card_views.size();
    }
    @Override
    public Object getItem(int position) {
        return card_views.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.card_view,parent,false);
        }
        TextView nameTxt= (TextView) convertView.findViewById(R.id.nameTxt);
        LinearLayout image =(LinearLayout) convertView.findViewById(R.id.LinearLayout0);

        final card_view s= (card_view) this.getItem(position);

        nameTxt.setText(s.getName());
        image.setBackgroundResource(s.getImage());



        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent details = new Intent(c,resource_details.class);
                details.putExtra("Special",s.getName());
                c.startActivity(details);


            }
        });
        return convertView;
    }
}