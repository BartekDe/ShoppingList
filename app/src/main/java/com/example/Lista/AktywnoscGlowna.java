package com.example.Lista;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.v4.view.ViewPager;



public class AktywnoscGlowna extends AppCompatActivity{
    private MyPagerAdapter mCustomPagerAdapter;
    private ViewPager viewPager;
    private Handler handler;
    private int page = 0;
    private int delay = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktywnosc_glowna);

        viewPager = findViewById(R.id.pager);
        handler = new Handler();


        int[] mResources = {
                R.drawable.warzywa,
                R.drawable.warzywa2,
                R.drawable.warzywa3,
                R.drawable.warzywa5
        };

        mCustomPagerAdapter = new MyPagerAdapter(this, mResources);

        viewPager.setAdapter(mCustomPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                page = position;
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delay);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    Runnable runnable = new Runnable() {
        public void run() {
            if (mCustomPagerAdapter.getCount() == page) {
                page = 0;
            } else {
                page++;
            }
            viewPager.setCurrentItem(page, true);
            handler.postDelayed(this, delay);
        }
    };

    public void buttonClickFunction(View v)
    {
        Intent intent = new Intent(getApplicationContext(), AktywnoscLista.class);
        startActivity(intent);
    }
}
