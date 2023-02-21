package com.ilovesshan.swipperview.utils;

import android.content.Context;

public class ScreenUtil {
    public static int dip2px(Context context, double dipValue) {
        // density 1个dip对应多少px
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * density + 0.5);
    }
}