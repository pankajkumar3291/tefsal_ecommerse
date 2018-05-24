package com.tefsalkw.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class FeelFragment extends BaseFragment {


    @BindView(R.id.header_txt)
    TextView header_txt;


    public static Fragment newInstance() {


        return  new FeelFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_feel, container, false);

        ButterKnife.bind(this, v);

        header_txt.setText(TextileDetailActivity.feelString);


        return v;
    }


}
