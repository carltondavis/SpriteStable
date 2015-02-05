package com.hazyfutures.spritestable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity {

    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.viewpager_main);

        // Generate sample data

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        //TODO pass persistant data object
        adapter = new ViewPagerAdapter(MainActivity.this);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

                //This code is run every time a new page is chosen


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }


}