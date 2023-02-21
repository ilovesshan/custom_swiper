package com.ilovesshan.swipperview.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.ilovesshan.swipperview.R;
import com.ilovesshan.swipperview.utils.ScreenUtil;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: ilovesshan
 * @date: 2023/2/21
 * @description:
 */
public class YFSwiper extends LinearLayout {
    private static final String TAG = "YFSwiper";

    private TextView title;
    private YfSwiperViewPager viewPager;
    private RadioGroup indicatorList;
    private InnerSwiperAdapter pagerAdapter;
    private boolean isShowTitle;
    private int indicatorSwitchTime;

    public YFSwiper(Context context) {
        this(context, null);
    }

    public YFSwiper(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public YFSwiper(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final View view = LayoutInflater.from(context).inflate(R.layout.yf_item_swiper, this, false);
        addView(view);

        // 读取自定义属性
        final TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.YFSwiper);
        isShowTitle = ta.getBoolean(R.styleable.YFSwiper_showTitle, true);
        indicatorSwitchTime = ta.getInt(R.styleable.YFSwiper_indicatorSwitchTime, 3000);
        Log.d(TAG, "isShowTitle: " + isShowTitle);
        Log.d(TAG, "indicatorSwitchTime: " + indicatorSwitchTime);

        ta.recycle();

        // 初始化view和事件绑定
        initViewAndBindEvent();
    }


    public void setData(InnerSwiperAdapter pagerAdapter) {
        this.viewPager.setAdapter(pagerAdapter);
        this.pagerAdapter = pagerAdapter;
        // 保证 默认选中第一个
        viewPager.setCurrentItem(Integer.MAX_VALUE / pagerAdapter.getDataSize() + 1);
        initIndication();
    }


    private void initIndication() {
        if (pagerAdapter != null) {
            final int count = pagerAdapter.getDataSize();
            final LayoutParams layoutParams = new LayoutParams(ScreenUtil.dip2px(getContext(), 8), ScreenUtil.dip2px(getContext(), 8));
            layoutParams.setMargins(ScreenUtil.dip2px(getContext(), 10), 0, ScreenUtil.dip2px(getContext(), 10), 0);
            indicatorList.removeAllViews();
            for (int i = 0; i < count; i++) {
                // 构建指示器 小圆点
                final RadioButton radioButton = new RadioButton(getContext());
                radioButton.setBackgroundResource(R.drawable.indicator_selector);
                radioButton.setButtonDrawable(null);
                radioButton.setLayoutParams(layoutParams);
                indicatorList.addView(radioButton);
            }

            // 默认显示第一个标题
            title.setText(pagerAdapter.getCurrentTitle(0));
            // 默认选中第一个指示器点
            updateIndicatorActive(0);
        }
    }


    private void initViewAndBindEvent() {
        title = this.findViewById(R.id.swiper_title);
        viewPager = this.findViewById(R.id.swiper_view_pager);
        indicatorList = this.findViewById(R.id.swiper_indicator_list);

        // 根据自定义属性值 进行UI初始化设置
        if (!isShowTitle) {
            title.setVisibility(View.GONE);
        }
        viewPager.setIndicatorSwitchTime(indicatorSwitchTime);


        // 预先加载三个Page
        viewPager.setOffscreenPageLimit(3);
        // 设置边距
        viewPager.setPageMargin(ScreenUtil.dip2px(getContext(), 20));

        // ViewPager 点击事件
        viewPager.setOnItemClickListener(position -> {
            if (pagerAdapter != null && pagerAdapter.onItemClickListener != null) {
                // 通知给 调用者
                pagerAdapter.onItemClickListener.onItemClick(position % pagerAdapter.getDataSize());
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                // 更新标题和更新指示器状态
                if (pagerAdapter != null) {
                    final int realCount = position % pagerAdapter.getDataSize();
                    Log.d(TAG, "realCount: " + realCount);
                    title.setText(pagerAdapter.getCurrentTitle(realCount));
                    updateIndicatorActive(realCount);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void updateIndicatorActive(int position) {
        for (int i = 0; i < indicatorList.getChildCount(); i++) {
            if (i == position) {
                ((RadioButton) indicatorList.getChildAt(i)).setChecked(true);
            } else {
                ((RadioButton) indicatorList.getChildAt(i)).setChecked(false);
            }
        }
    }

    public abstract static class InnerSwiperAdapter extends PagerAdapter {
        private OnItemClickListener onItemClickListener;

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = getSubview(container, position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }

        public abstract View getSubview(ViewGroup container, int position);

        public abstract int getDataSize();

        public abstract String getCurrentTitle(int position);


        public interface OnItemClickListener {
            void onItemClick(int position);
        }

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }
    }
}
