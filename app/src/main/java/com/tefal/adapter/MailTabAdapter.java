package com.tefal.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.tefal.fragment.FragmentInbox;
import com.tefal.fragment.FragmentSent;

/**
 * Created by jagbirsinghkang on 13/07/17.
 */

public class MailTabAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public MailTabAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                FragmentInbox tab1 = new FragmentInbox();
                return tab1;
            case 1:
                FragmentSent tab2 = new FragmentSent();
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