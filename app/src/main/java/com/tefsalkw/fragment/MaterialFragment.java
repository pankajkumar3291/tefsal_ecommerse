package com.tefsalkw.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.activity.TextileDetailActivity;
import com.tefsalkw.adapter.DishdashaTextileProductAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 */
public class MaterialFragment extends BaseFragment {

@BindView(R.id.tv_material)
    TextView tv_material;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_material, container, false);
        ButterKnife.bind(this, v);
        tv_material.setText(DishdashaTextileProductAdapter.textileModels.get(TextileDetailActivity.position).getMaterial());

        return v;
    }

}
