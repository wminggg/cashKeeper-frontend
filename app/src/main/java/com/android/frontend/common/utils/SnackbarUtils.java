package com.android.frontend.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import com.android.frontend.R;
import com.android.frontend.model.enums.SnackbarType;
import com.caverock.androidsvg.SVGImageView;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {

    // 定义常量
    private static final int TOAST_PADDING_DP = 10;
    private static final int CORNER_RADIUS_DP = 8;

    /**
     * 显示自定义Snackbar
     *
     * @param view      显示Snackbar的View
     * @param message   Snackbar消息文本
     * @param snackbarType Snackbar类型
     */
    public static void showCustomSnackbar(View view, String message, SnackbarType snackbarType) {
        Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT);
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

        // 设置透明背景，避免黑影
        snackbar.getView().setBackground(null);

        View customSnackbarView = createSnackbarLayout(view.getContext(), message, snackbarType);
        snackbarLayout.addView(customSnackbarView, 0);

        snackbar.show();
    }


    /**
     * 创建Snackbar的自定义布局
     *
     * @param context   上下文
     * @param message   Snackbar消息文本
     * @param snackbarType Snackbar类型
     * @return Snackbar的自定义布局
     */
    private static View createSnackbarLayout(Context context, String message, SnackbarType snackbarType) {
        @SuppressLint("InflateParams") View customSnackbarView = LayoutInflater.from(context).inflate(R.layout.snackbar_common, null);
        LinearLayout customSnackbarLayout = customSnackbarView.findViewById(R.id.customSnackbarLayout);

        // 加载并显示SVG图像
        SVGImageView svgImageView = SvgUtils.loadSvgImage(context, TOAST_PADDING_DP, snackbarType.getSvgType());
        customSnackbarLayout.addView(svgImageView);

        // 创建TextView
        TextView textView = createTextView(context, message, snackbarType);
        customSnackbarLayout.addView(textView);

        // 设置背景
//        Drawable background = createRoundRectBackground(ContextCompat.getColor(context, R.color.ivory), context);
        Drawable background = createRoundRectBackground(ContextCompat.getColor(context, R.color.grey), context);
        customSnackbarLayout.setBackground(background);

        return customSnackbarView;
    }

    /**
     * 创建TextView
     *
     * @param context   上下文
     * @param message   文本消息
     * @param snackbarType Toast类型
     * @return TextView
     */
    private static TextView createTextView(Context context, String message, SnackbarType snackbarType) {
        TextView textView = new TextView(context);
        textView.setText(message);
        textView.setTextColor(snackbarType.getTextColor());
        textView.setTypeface(Typeface.DEFAULT, snackbarType.getTextStyle());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        textView.setLayoutParams(layoutParams);
        return textView;
    }

    /**
     * 创建圆角矩形背景
     *
     * @param color   背景颜色
     * @param context 上下文
     * @return 圆角矩形背景Drawable
     */
    private static Drawable createRoundRectBackground(int color, Context context) {
        GradientDrawable background = new GradientDrawable();
        background.setColor(color);
        background.setCornerRadius(DimenUtils.dpToPx(context, CORNER_RADIUS_DP));
        return background;
    }
}