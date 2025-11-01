package com.halata.blueapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.halata.blueapp.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        // Handle swipe between pages
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
             int itemId = item.getItemId();
             if(itemId == R.id.mnu_home){
                 viewPager.setCurrentItem(0);
             }else if(itemId == R.id.mnu_settings){
                 viewPager.setCurrentItem(1);
             }else {
                 viewPager.setCurrentItem(2);
             }
             return true;
        });

    }
}