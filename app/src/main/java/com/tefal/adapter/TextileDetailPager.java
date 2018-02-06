package com.tefal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tefal.fragment.FeelFragment;
import com.tefal.fragment.MaterialFragment;

/**
 * Created by AC 101 on 12-10-2017.
 */

public class TextileDetailPager extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public TextileDetailPager(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FeelFragment tab1 = new FeelFragment();
                return tab1;
            case 1:
                MaterialFragment tab2 = new MaterialFragment();
                return tab2;

            default:
                return null;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}