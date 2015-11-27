package com.dream.one.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.dream.one.R;
import com.dream.one.view.DepthPageTransformer;
import com.dream.one.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by CNKI-0000 on 2015/11/23.
 */
public class WelcomeActivity extends Activity implements View.OnClickListener{

    Context context;
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
        context = getApplicationContext();
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
        // 设置indicator动画
        ObjectAnimator.ofFloat(dots[position], "scaleX", 0.0f, 1.0f)
                .setDuration(200)
                .start();
        dots[position].setBackgroundColor(Color.DKGRAY);
        ObjectAnimator.ofFloat(dots[currentIndex],"scaleX",0.0f,1.0f)
                .setDuration(200)
                .start();
        dots[currentIndex].setBackgroundColor(Color.GRAY);

        currentIndex = position;

        if(position == viewList.size() - 1){
            Button btn_ex = (Button) findViewById(R.id.btn_exper);
            btn_ex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(context, BaseActivity.class));
                    finish();
                }
            });
        }
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


        viewPager.setAdapter(new ViewPagerAdapter(context, viewList));
        viewPager.setPageTransformer(true, new DepthPageTransformer());
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setCurrentDot(position);
                Toast.makeText(context, dots[position].getId() + "", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.indcator_1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.indcator_2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.indcator_3:
                viewPager.setCurrentItem(2);
                break;
            default:
                break;

        }
    }
}
