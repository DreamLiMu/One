package com.dream.one.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dream.com.one.R;

/**
 * Created by CNKI-0000 on 2015/11/23.
 */
public class WelcomeActivity extends Activity {

    ViewPager viewPager;
    LayoutInflater inflater;
    View view1;
    View view2;
    View view3;
    List<View> viewList;

    ImageView[] dots;

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();

        initDots();
    }

    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);

        dots = new ImageView[viewList.size()];
        for (int i = 0; i< dots.length; i++){
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setBackgroundColor(Color.GRAY);

            currentIndex = 0;
            dots[currentIndex].setBackgroundColor(Color.DKGRAY);
        }
    }

    private void setCurrentDot(int position){
        if (position < 0 || position > viewList.size() - 1
                || currentIndex == position) {
            return;
        }

        dots[position].setBackgroundColor(Color.DKGRAY);
        dots[currentIndex].setBackgroundColor(Color.GRAY);

        currentIndex = position;
    }

    public void initView(){
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        inflater = getLayoutInflater().from(this);
        view1 = inflater.inflate(R.layout.vp1, null);
        view2 = inflater.inflate(R.layout.vp2, null);
        view3 = inflater.inflate(R.layout.vp3,null);
        viewList = new ArrayList<View>();
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);


        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(viewList.get(position));
            }

            @Override
            public int getItemPosition(Object object) {
                return super.getItemPosition(object);
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(viewList.get(position));
                if(position == 2){
                    Button btn_ex = (Button) findViewById(R.id.btn_exper);
                    btn_ex.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(WelcomeActivity.this,OneActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
                return viewList.get(position);
            }
        };

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
