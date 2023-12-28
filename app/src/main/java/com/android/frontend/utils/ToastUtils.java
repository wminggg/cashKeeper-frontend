package com.android.frontend.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.widget.TextView;
import android.widget.Toast;
import com.android.frontend.model.enums.ToastType;
import me.drakeet.support.toast.ToastCompat;

public class ToastUtils {

    public static void showCustomToast(Activity activity, String message, ToastType toastType) {
        // Set text color and style based on toast type
        int textColor;
        int textStyle;
        switch (toastType) {
            case SUCCESS:
                textColor = Color.GREEN;
                textStyle = Typeface.BOLD;
                break;
            case FAILURE:
                textColor = Color.RED;
                textStyle = Typeface.BOLD;
                break;
            default:
                textColor = Color.WHITE;
                textStyle = Typeface.NORMAL;
                break;
        }

        // Create a new TextView with the provided message
        TextView textView = new TextView(activity);
        textView.setText(message);
        textView.setTextColor(textColor);
        textView.setTypeface(Typeface.DEFAULT, textStyle);

        // Create and show the toast using ToastCompat
        ToastCompat.makeText(activity, textView.getText(), Toast.LENGTH_SHORT).show();
    }
}

