package com.yurmouky.hijjawi;

/**
 * Created by dell on 15/09/2017.
 */

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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
public class PostAdapter extends BaseAdapter{
    Context c;
    ArrayList<post> post_cards;


    String url;


    public PostAdapter(Context c, ArrayList<post> post_cards) {
        this.c = c;
        this.post_cards = post_cards;


    }
    @Override
    public int getCount() {
        return post_cards.size();
    }
    @Override
    public Object getItem(int position) {
        return post_cards.get(position);
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.message_card,parent,false);
        }

        TextView messageTxt= (TextView) convertView.findViewById(R.id.message);
        TextView TitleTxt= (TextView) convertView.findViewById(R.id.Title);
        Button btn = (Button) convertView.findViewById(R.id.download_url);



        final post s= (post) this.getItem(position);

        messageTxt.setText(s.getMessage());
        TitleTxt.setText(s.getTopic());


        if(s.getUrl()!=null) {
            btn.setVisibility(View.VISIBLE);

        }else{
            btn.setVisibility(View.INVISIBLE);
        }

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                url= s.getUrl();
               c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });
        return convertView;
    }

}