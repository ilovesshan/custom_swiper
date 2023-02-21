package com.ilovesshan.swipperview.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.ilovesshan.swipperview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/21
 * @description:
 */
public class SwiperAdapter extends PagerAdapter {
    private static final String TAG = "SwiperAdapter";

    private List<Integer> swipers = new ArrayList<>();


    @Override
    public int getCount() {
        // Log.d(TAG, "Integer.MAX_VALUE: " + Integer.MAX_VALUE);
        // if (swipers != null) {
        //     return swipers.size();
        // }
        // return 0;
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_swiper, container, false);
        ImageView imageView = view.findViewById(R.id.image);
        final int currentIndex = position % swipers.size();
        Log.d(TAG, "currentIndex: " + currentIndex + ", position: " + position);
        imageView.setImageResource(swipers.get(currentIndex));

        container.addView(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Log.d(TAG, "destroyItem: " + object);
        container.removeView((View) object);
    }

    public void setData(List<Integer> swipers) {
        this.swipers.clear();
        this.swipers = swipers;
        // this.notifyDataSetChanged();
    }
}
