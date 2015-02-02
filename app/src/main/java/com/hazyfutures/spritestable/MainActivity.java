package com.hazyfutures.spritestable;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

public class MainActivity extends Activity {

    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;
    String[] rank;
    String[] country;
    String[] population;
    int[] flag;
    private Dice Dice = new Dice();
    public PersistentValues data = new PersistentValues();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from viewpager_main.xml
        setContentView(R.layout.viewpager_main);

        //TODO Load info from database, creating persistant data object

        data.RestoreFromDB(this);

        // Generate sample data

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        // Pass results to ViewPagerAdapter Class
        //TODO pass persistant data object
        adapter = new ViewPagerAdapter(MainActivity.this);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);

    }


}