package com.tefsalkw.utils;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NumberCrawler {


    public void replaceFonts(LinearLayout view)
    {
        View child;
        for(int i = 0; i < view.getChildCount(); ++i)
        {
            child = view.getChildAt(i);

             if(child instanceof TextView)
            {
                // base case
                ((TextView) child).setText(replaceArabicNumbers(((TextView) child).getText()));
            }
        }
    }


    private String replaceArabicNumbers(CharSequence original) {
        if (original != null) {
            return original.toString()
                    .replaceAll("٩", "1")
                    .replaceAll("٨", "2")
                    .replaceAll("٧", "3")
                    .replaceAll("٦", "4")
                    .replaceAll("٥", "5")
                    .replaceAll("٤", "6")
                    .replaceAll("٣", "7")
                    .replaceAll("٢", "8")
                    .replaceAll("١", "9")
                    .replaceAll("٠", "0");

        }

        return null;
    }


}
