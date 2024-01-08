package com.android.frontend.common.utils;

import android.content.Context;

/**
 * @Description: 尺寸单位转换工具类
 * @Author: MING
 * @Date: 2024/01/07
 */

public class DimenUtils {

    /**
     * @Description: 将dp单位转换为px单位
     * @see Context
     * @see int
     * @see int
     */

    public static int dpToPx(Context context, int dp) {
        // 获取屏幕密度
        float density = context.getResources().getDisplayMetrics().density;
        // 使用四舍五入将dp值转换为px值
        return Math.round(dp * density);
    }
}

