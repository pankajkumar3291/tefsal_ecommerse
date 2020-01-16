package com.tefsalkw.fragment;


import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.tefsalkw.R;
import com.tefsalkw.adapter.DishdashaTextileOtherProductAdapter;
import com.tefsalkw.models.ProductRecord;
import com.tefsalkw.models.ProductsResponse;
import com.tefsalkw.utils.Contents;
import com.tefsalkw.utils.SessionManager;
import com.tefsalkw.utils.SimpleProgressBar;

import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;


public class SubCategoryFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    String store_id, flag;
    DishdashaTextileOtherProductAdapter dishdashaAdapter;
    SessionManager session;
    ArrayList<ProductRecord> records;
    public SubCategoryFragment(){
    }
    public static Fragment newInstance(ArrayList<ProductRecord> records1, String store_id) {

        SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
        Bundle args = new Bundle();
        args.putString("store_id", store_id);
        args.putSerializable("ProductRecord", records1);
        subCategoryFragment.setArguments(args);
        return subCategoryFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_sub_category, container, false);
        ButterKnife.bind(this, rootView);
        session = new SessionManager(getActivity());

        flag = getActivity().getIntent().getStringExtra("flag");
        records = (ArrayList<ProductRecord>) getArguments().getSerializable("ProductRecord");
        Log.e("recordssize ", records.size() + "");
        store_id = getArguments().getString("store_id");

        Log.e("store_id ", store_id);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dishdashaAdapter = new DishdashaTextileOtherProductAdapter(getActivity(), records, store_id, flag);
        recycler.setAdapter(dishdashaAdapter);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        recycler.setLayoutManager(mLayoutManager);
        recycler.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recycler.setItemAnimator(new DefaultItemAnimator());

    }


    //region GridList Methods



    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    //endregion

}
