package com.hazyfutures.spritestable;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class MainActivity extends FragmentActivity {

    // Declare Variables
    public PersistentValues data = new PersistentValues();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        data.RestoreFromDB(this);
        setContentView(R.layout.viewpager_main);


        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

/*
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {

            }

            @Override
            public void onPageSelected(int i) {

                //This code is run every time a new page is chosen
)
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

*/

    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        if (data != null) {
            data.SaveAllToDB();
        }
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first


        if (data == null) {
            data.RestoreFromDB(this);
        }
    }

}