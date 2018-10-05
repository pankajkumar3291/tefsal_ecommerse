package com.tefsalkw.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

public class CustomTextView extends AppCompatTextView {

    public CustomTextView(Context context) {
        super(context);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setText(CharSequence text, BufferType type) {

        super.setText(replaceArabicNumbers(text), type);


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

