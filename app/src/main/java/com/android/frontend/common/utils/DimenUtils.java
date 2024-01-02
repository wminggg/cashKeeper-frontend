package com.android.frontend.common.utils;

import android.content.Context;

public class DimenUtils {

    public static int dpToPx(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }
}
