package com.tefsalkw.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.tefsalkw.R;

public class CustomTextView extends AppCompatTextView {

    Typeface myTypeface;

    public CustomTextView(Context context) {
        super(context);
        init(null);
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomFontText);
            String fontName = a.getString(0);
            if (!(fontName == null || isInEditMode())) {
                this.myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
            }
            setTypeface(this.myTypeface);
            a.recycle();
        }
    }
}

