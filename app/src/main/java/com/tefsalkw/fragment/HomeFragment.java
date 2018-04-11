package com.tefsalkw.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tefsalkw.R;
import com.tefsalkw.activity.AccessoriesActivity;
import com.tefsalkw.activity.DishdishaStyleActivity;
import com.tefsalkw.activity.OtherStoresActivity;
import com.tefsalkw.app.TefalApp;

/**
 * Created by prateek on 13/07/17.
 */

public class HomeFragment extends BaseFragment {

    private int dotsCount;

    private ImageView[] dots;

    private Integer[] img_array = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3, R.drawable.slider4};
    private String[] title_array;// = {getString(R.string.DISHDASHA), getString(R.string.DARAA), getString(R.string.ABAYA), getString(R.string.ACCESSORIES)};

    MainPagerAdapter adapter;

    ViewPager mainViewPager;

    public static String productFlag = "";

    ImageView leftArrow, rightArrow;

    LinearLayout pager_indicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        mainViewPager = (ViewPager) v.findViewById(R.id.mainViewPager);
        pager_indicator = (LinearLayout) v.findViewById(R.id.viewPagerCountDots);
        title_array=new String[] {getString(R.string.DISHDASHA), getString(R.string.DARAA), getString(R.string.ABAYA), getString(R.string.ACCESSORIES)};

        leftArrow = (ImageView) v.findViewById(R.id.leftArrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.arrowScroll(ViewPager.FOCUS_LEFT);
            }
        });
        rightArrow = (ImageView) v.findViewById(R.id.rightArrow);
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainViewPager.arrowScroll(ViewPager.FOCUS_RIGHT);
            }
        });
        adapter = new MainPagerAdapter(getActivity(), img_array);
        mainViewPager.setAdapter(adapter);
        mainViewPager.setOffscreenPageLimit(4);
        mainViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                toggleArrowVisibility(position == 0, position == dotsCount - 1);

                for (int i = 0; i < dotsCount; i++)
                {
                    dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_non_selected));
                }
                dots[position].setImageDrawable(getResources().getDrawable(R.drawable.dot_select));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setUiPageViewController();

        return v;
    }
    public void toggleArrowVisibility(boolean isAtZeroIndex, boolean isAtLastIndex) {
        if(isAtZeroIndex)
            leftArrow.setVisibility(View.INVISIBLE);
        else
            leftArrow.setVisibility(View.VISIBLE);
        if(isAtLastIndex)
            rightArrow.setVisibility(View.INVISIBLE);
        else
            rightArrow.setVisibility(View.VISIBLE);

    }
    private void setUiPageViewController() {

        dotsCount = adapter.getCount();
        dots = new ImageView[dotsCount];

        for (int i = 0; i < dotsCount; i++) {
            dots[i] = new ImageView(getActivity());
            dots[i].setImageDrawable(getResources().getDrawable(R.drawable.dot_non_selected));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );

            params.setMargins(8, 0, 8, 0);

            pager_indicator.addView(dots[i], params);
        }

        dots[0].setImageDrawable(getResources().getDrawable(R.drawable.dot_select));
    }

    public class MainPagerAdapter extends PagerAdapter {
        Context context;
        Integer[] img;
        LayoutInflater layoutInflater;


        public MainPagerAdapter(Context context, Integer[] img) {
            this.context = context;
            this.img = img;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return img.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((RelativeLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final View itemView = layoutInflater.inflate(R.layout.pager_item, container, false);

            final ImageView imageView = (ImageView) itemView.findViewById(R.id.m_image);
            final TextView title = (TextView) itemView.findViewById(R.id.titleText);

            imageView.setImageResource(img[position]);
            title.setText(title_array[position]);

            container.addView(itemView);

            //listening to image click
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (position == 0)
                    {
                        startActivity(new Intent(getActivity(), DishdishaStyleActivity.class));
                        productFlag = "1";
                    }
                    else if (position == 1)
                    {
                        TefalApp.getInstance().setToolbar_title("DARAA STORES");
                        startActivity(new Intent(getActivity(), OtherStoresActivity.class).putExtra("flag", "Daraa"));
                        productFlag = "3";
                    }
                    else if (position == 2)
                    {
                        TefalApp.getInstance().setToolbar_title("ABAYA STORES");
                        startActivity(new Intent(getActivity(), OtherStoresActivity.class).putExtra("flag", "Abaya"));
                        productFlag = "2";
                    }
                    else if (position == 3)
                    {
                        startActivity(new Intent(getActivity(), AccessoriesActivity.class));
                        productFlag = "4";
                    }
                }
            });

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((RelativeLayout) object);
        }
    }
}
