package com.android.frontend.common.utils;

import android.content.Context;
import android.widget.LinearLayout;

import com.android.frontend.model.enums.SvgType;
import com.caverock.androidsvg.SVGImageView;

public class SvgUtils {
    public static SVGImageView loadSvgImage(Context context, int viewPadding, SvgType svgType) {
        int svgResourceId = svgType.getSvgResourceId();
        SVGImageView svgImageView = new SVGImageView(context);
        svgImageView.setImageResource(svgResourceId);

        //设置布局
        int svgPadding = DimenUtils.dpToPx(context, viewPadding);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                DimenUtils.dpToPx(context, svgPadding),  // 设置宽度，根据需要调整
                DimenUtils.dpToPx(context, svgPadding)   // 设置高度，根据需要调整
        );
        svgImageView.setLayoutParams(layoutParams);

        return svgImageView;
    }
}