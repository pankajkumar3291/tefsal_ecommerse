package com.tefal.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tefal.fragment.FragmentAllStores;
import com.tefal.fragment.FragmentTailorStore;
import com.tefal.fragment.FragmentTextileStore;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class PagerStoresAdapter extends FragmentStatePagerAdapter {

    int mNumOfTabs;
    String flag;

    public PagerStoresAdapter(FragmentManager fm, int NumOfTabs, String flag) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.flag = flag;
    }
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        switch (position) {
           /* case 0:

                bundle.putString("flag", flag);
                FragmentAllStores tab1 = new FragmentAllStores();
                tab1.setArguments(bundle);
                return tab1;*/
            case 0:
                bundle.putString("flag", flag);
                FragmentTextileStore tab2 = new FragmentTextileStore();
                tab2.setArguments(bundle);
                return tab2;
                //return null;
            case 1:
                bundle.putString("flag", flag);
                FragmentTailorStore tab3 = new FragmentTailorStore();
                tab3.setArguments(bundle);
                return tab3;
                //return null;
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}