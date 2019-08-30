package com.yurmouky.hijjawi;

/**
 * * Created by dell on 12/09/2017.

 */


import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class TopicAdapter extends BaseAdapter {


    Context context;
    ArrayList<String> items;
    ArrayList<String> Topics;
    ArrayList<Boolean> checkedState;

    String Item;

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();

        Topics = ((MainActivity)context).getTopics();

    }

    public TopicAdapter(Context context, ArrayList<String> items) {

        this.context = context;
        this.items=items;
        checkedState =new ArrayList<>();


    }

    /*private view holder class*/
    private class ViewHolder {
        TextView Text;
        CheckBox check;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView( int position, View convertView, ViewGroup parent) {
        try {

            final View v = convertView;
            Item = (String) getItem(position);
            final ViewHolder holder;
            //  LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (Topics.contains(Item)) {checkedState.add(position, true);} else   {checkedState.add(position, false);}

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.topic_view, parent, false);
                holder = new ViewHolder();
                holder.Text = (TextView) convertView.findViewById(R.id.Topic);
                holder.check = (CheckBox) convertView.findViewById(R.id.checkBox);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
                holder.check.setOnCheckedChangeListener(null);
            }


            holder.Text.setText(Item);
            holder.check.setChecked(checkedState.get(position));

            holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                final String txt = holder.Text.getText().toString();
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (isChecked) {
                        ((MainActivity) context).insert(txt);
                    } else {
                        ((MainActivity) context).remove(txt);
                    }

                }
            });

        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }
        return convertView;
    }






}

