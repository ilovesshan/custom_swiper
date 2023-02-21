package com.ilovesshan.swipperview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.ilovesshan.swipperview.entity.Swiper;
import com.ilovesshan.swipperview.views.YFSwiper;

import java.util.ArrayList;
import java.util.List;

public class SwiperActivity extends AppCompatActivity {
    private static final String TAG = "SwiperActivity";

    private List<Swiper> swipers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swiper);

        initData();

        initViewAndBindEvent();

    }

    private void initData() {
        swipers.add(new Swiper("第一个轮播图", R.mipmap.banner01));
        swipers.add(new Swiper("第2个轮播图", R.mipmap.banner02));
        swipers.add(new Swiper("第叁个轮播图", R.mipmap.banner03));
        swipers.add(new Swiper("第Ⅳ个轮播图", R.mipmap.banner04));
    }

    private void initViewAndBindEvent() {
        final YFSwiper yfSwiper = findViewById(R.id.yf_swiper);
        final YFSwiper.InnerSwiperAdapter swiperAdapter = new YFSwiper.InnerSwiperAdapter() {
            @Override
            public View getSubview(ViewGroup container, int position) {
                final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_swiper, container, false);
                ImageView imageView = view.findViewById(R.id.image);
                imageView.setImageResource(swipers.get(position % swipers.size()).getIcon());
                return imageView;
            }

            @Override
            public int getDataSize() {
                return swipers.size();
            }

            @Override
            public String getCurrentTitle(int position) {
                return swipers.get(position).getTitle();
            }
        };

        swiperAdapter.setOnItemClickListener(position -> {
            Toast.makeText(this, swipers.get(position).getTitle() + "被点击...", Toast.LENGTH_SHORT).show();
        });
        yfSwiper.setData(swiperAdapter);
    }
}