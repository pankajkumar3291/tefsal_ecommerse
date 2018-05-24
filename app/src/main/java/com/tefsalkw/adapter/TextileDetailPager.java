package com.tefsalkw.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tefsalkw.fragment.FeelFragment;
import com.tefsalkw.fragment.MaterialFragment;

/**
 * Created by AC 101 on 12-10-2017.
 */

public class TextileDetailPager extends FragmentStatePagerAdapter {


    int tabCount;

    public TextileDetailPager(FragmentManager fm, int tabCount) {
        super(fm);

        this.tabCount = tabCount;
    }


    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FeelFragment f1 =  new FeelFragment();
                return f1;

            case 1:
                MaterialFragment f2 =  new MaterialFragment();
                return f2;

            default:
                FeelFragment f3 =  new FeelFragment();
                return f3;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}