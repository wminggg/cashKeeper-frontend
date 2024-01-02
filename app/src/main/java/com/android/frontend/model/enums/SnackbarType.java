package com.android.frontend.model.enums;

import android.graphics.Color;
import android.graphics.Typeface;
import lombok.Getter;

@Getter
public enum SnackbarType {
    SUCCESS("成功", Color.GREEN, Typeface.BOLD, SvgType.SUCCESS),
    FAILURE("失败", Color.RED, Typeface.BOLD, SvgType.FAILURE),
    DEFAULT("默认", Color.BLACK, Typeface.NORMAL, SvgType.DEFAULT);

    private final String text;
    private final int textColor;
    private final int textStyle;
    private final SvgType svgType;

    SnackbarType(String text, int textColor, int textStyle, SvgType svgType) {
        this.text = text;
        this.textColor = textColor;
        this.textStyle = textStyle;
        this.svgType = svgType;
    }
}
