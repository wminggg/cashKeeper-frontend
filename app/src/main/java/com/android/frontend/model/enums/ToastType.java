package com.android.frontend.model.enums;

import android.graphics.Color;
import android.graphics.Typeface;

public enum ToastType {
    SUCCESS("成功", Color.GREEN, Typeface.BOLD),
    FAILURE("失败", Color.RED, Typeface.BOLD),
    DEFAULT("默认", Color.BLACK, Typeface.NORMAL);

    private final String text;
    private final int textColor;
    private final int textStyle;

    ToastType(String text, int textColor, int textStyle) {
        this.text = text;
        this.textColor = textColor;
        this.textStyle = textStyle;
    }

    public String getText() {
        return text;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getTextStyle() {
        return textStyle;
    }
}

