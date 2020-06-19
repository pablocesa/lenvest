package com.example.expandablecardview;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class Convert {

    public static float pixelsToDp(float px, Context context) {
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float dp = px / ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return dp;
    }

    public static float dpToPixels(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }
}