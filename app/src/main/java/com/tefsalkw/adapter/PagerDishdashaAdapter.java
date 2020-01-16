package com.tefsalkw.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tefsalkw.fragment.FragmentAllStores;
import com.tefsalkw.fragment.FragmentTailorProducts;
import com.tefsalkw.fragment.FragmentTailorStore;
import com.tefsalkw.fragment.FragmentTextileProducts;
import com.tefsalkw.fragment.FragmentTextileStore;
import com.tefsalkw.fragment.TailorTextileChooseFragment;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class PagerDishdashaAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    String store_id;
    Bundle bundl;
    public PagerDishdashaAdapter(FragmentManager fm, int NumOfTabs, String store_id) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.store_id = store_id;
        bundl = new Bundle();

    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                bundl.putString("store_id", store_id);
                bundl.putString("flag", "dish");
                FragmentTextileProducts tab1 = new FragmentTextileProducts();
                tab1.setArguments(bundl);
                return tab1;
            case 1:
                bundl.putString("store_id", store_id);
                TailorTextileChooseFragment tab2=new TailorTextileChooseFragment();
                //FragmentTailorProducts tab2 = new FragmentTailorProducts();
                tab2.setArguments(bundl);
                return tab2;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}