package com.ilovesshan.swipperview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.ilovesshan.swipperview.adapter.SwiperAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private ViewPager swiper;
    private List<Integer> swipers = new ArrayList<>();
    private SwiperAdapter swiperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        initViewAndBindEvent();


    }

    private void initData() {
        swipers.add(R.mipmap.banner01);
        swipers.add(R.mipmap.banner02);
        swipers.add(R.mipmap.banner03);
        swipers.add(R.mipmap.banner04);
    }

    private void initViewAndBindEvent() {
        swiper = findViewById(R.id.swiper);
        swiperAdapter = new SwiperAdapter();
        swiperAdapter.setData(swipers);
        swiper.setAdapter(swiperAdapter);
        swiper.setCurrentItem(Integer.MAX_VALUE / 2 + 1);
    }
}