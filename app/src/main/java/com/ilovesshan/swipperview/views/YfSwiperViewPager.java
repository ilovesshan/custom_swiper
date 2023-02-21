package com.ilovesshan.swipperview.views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/21
 * @description:
 */
public class YfSwiperViewPager extends ViewPager {
    private static final String TAG = "YfViewPager";

    private static final int DEFAULT_INDICATOR_SWITCH_TIME = 2000;
    private static int DURATION_TIME = DEFAULT_INDICATOR_SWITCH_TIME;
    private OnItemClickListener onItemClickListener;


    public YfSwiperViewPager(@NonNull Context context) {
        super(context);
    }

    public YfSwiperViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow....");
        // 开启轮播
        startLopper();

        // 设置事件监听, ViewPager被触摸时暂停轮播， 离开再继续轮播
        setOnTouchListener(new OnTouchListener() {
            boolean isClick = false;
            long leastPressedTime;
            float mX = 0.0f;
            float mY = 0.0f;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    // 按下
                    case MotionEvent.ACTION_DOWN:
                        mX = event.getX();
                        mY = event.getY();
                        leastPressedTime = System.currentTimeMillis();
                        stopLopper();
                        break;
                    // 抬起
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        final float absX = Math.abs(mX - event.getX());
                        final float absY = Math.abs(mY - event.getY());
                        if (absX > 5 || absY > 5) {
                            Log.d(TAG, "onTouch: " + "滑动事件...");
                        } else {
                            if (System.currentTimeMillis() - leastPressedTime > 500) {
                                Log.d(TAG, "onTouch: " + "长按事件...");
                            } else {
                                Log.d(TAG, "onTouch: " + "点击事件...");
                                // 通知给外界，当前Item被点击了
                                if (onItemClickListener != null) {
                                    onItemClickListener.onClick(getCurrentItem());
                                }
                            }
                        }
                        startLopper();
                        break;
                }
                return false;
            }
        });
    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        Log.d(TAG, "onDetachedFromWindow....");
        stopLopper();
    }


    private final Runnable lopperTask = new Runnable() {
        @Override
        public void run() {
            int currentItem = getCurrentItem();
            currentItem++;
            setCurrentItem(currentItem);
            // 循环执行当前Task
            postDelayed(this, DURATION_TIME);
        }
    };

    // 开启轮播
    private void startLopper() {
        postDelayed(lopperTask, DURATION_TIME);
    }

    // 暂停轮播
    private void stopLopper() {
        removeCallbacks(lopperTask);
    }


    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setIndicatorSwitchTime(int time) {
        DURATION_TIME = time;
    }

    public interface OnItemClickListener {
        void onClick(int position);
    }
}
