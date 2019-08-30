package com.yurmouky.hijjawi;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by dell on 20/09/2017.
 */

public class ExpLVAdapter extends BaseExpandableListAdapter {


    private ArrayList<String> listCategoria ;
    private Map<String , ArrayList<String>>mapChild;
    private Context context;


    private class ViewHolder {
        ImageButton image_copy,image_down;
        TextView txtTitle;
    }

    public ExpLVAdapter(Context context,ArrayList<String> listCategoria, Map<String, ArrayList<String>> mapChild) {
        this.listCategoria = listCategoria;
        this.mapChild = mapChild;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return listCategoria.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mapChild.get(listCategoria.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  listCategoria.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mapChild.get(listCategoria.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        String titleCategoria =(String)getGroup(groupPosition);
        convertView = LayoutInflater.from(context).inflate(R.layout.elv_parent,null);
        TextView tvParent= (TextView)convertView.findViewById(R.id.parent);
        tvParent.setText(titleCategoria);


        if (isExpanded) {
            tvParent.setTypeface(null, Typeface.BOLD);
            tvParent.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_expanded,0);

        } else {
            // If group is not expanded then change the text back into normal
            // and change the icon

            tvParent.setTypeface(null, Typeface.NORMAL);
            tvParent.setCompoundDrawablesWithIntrinsicBounds(0, 0,
                    R.drawable.ic_notexpanded, 0);
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

try {


    ViewHolder holder = null;
    String item = (String) getChild(groupPosition, childPosition);
    convertView = LayoutInflater.from(context).inflate(R.layout.elv_child, null);
    // TextView tvChild= (TextView)convertView.findViewById(R.id.child);
    // tvChild.setText(item);


        holder = new ViewHolder();
        TextView txtTitle= (TextView)convertView.findViewById(R.id.seleced_Item);
        holder.image_copy = (ImageButton) convertView.findViewById(R.id.image_copy);
        holder.image_down = (ImageButton) convertView.findViewById(R.id.image_down);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.seleced_Item);
        convertView.setTag(holder);




    holder.txtTitle.setText(item);
    holder.image_copy.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_copy));
    holder.image_down.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_download));


    final int group = groupPosition;
    final int child = childPosition;


    holder.image_copy.setOnClickListener(new ImageButton.OnClickListener() {


        @Override
        public void onClick(View v) {

            ((resource_details) context).copy(group, child);
        }

    });

    holder.image_down.setOnClickListener(new ImageButton.OnClickListener() {


        @Override
        public void onClick(View v) {

            ((resource_details) context).download(group, child);
        }

    });



}
catch (Exception e)
{
    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
}

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition){return true;}
}
