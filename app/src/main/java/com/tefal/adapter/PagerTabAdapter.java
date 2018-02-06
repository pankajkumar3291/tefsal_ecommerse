package com.tefal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tefal.fragment.FragmentAbaya;
import com.tefal.fragment.FragmentAllStores;
import com.tefal.fragment.FragmentDaraa;
import com.tefal.fragment.FragmentDishdasha;
import com.tefal.fragment.FragmentTailorStore;
import com.tefal.fragment.FragmentTextileStore;

public class PagerTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public PagerTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentDishdasha tab1 = new FragmentDishdasha();
                return tab1;
            case 1:
                FragmentDaraa tab2 = new FragmentDaraa();
                return tab2;
            case 2:
                FragmentAbaya tab3 = new FragmentAbaya();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}