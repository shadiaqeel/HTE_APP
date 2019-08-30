package com.yurmouky.hijjawi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by dell on 11/09/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public  ViewPagerAdapter (FragmentManager fm){
        super(fm);
}

    @Override
    public Fragment getItem(int position) {

        if(position ==1)
        {
            return new Tab1Fragment();

        }
        else{
            return new Tab0Fragment();

        }

    }

    @Override
    public int getCount() {
        return 2;
    }
}
