package com.android.frontend.common.utils;

import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.android.frontend.R;

public class KeyBoardUtils {
    private final LinearLayout keyboardView;
    private final EditText editText;
    private OnEnsureListener onEnsureListener;

    public interface OnEnsureListener {
        void onEnsure();
    }

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    public KeyBoardUtils(LinearLayout keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);

        initializeButtons();
        setKeyboardButtonListeners();
    }

    private void initializeButtons() {
        int[] buttonIds = {R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5,
                R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn0, R.id.btnDot,
                R.id.btnClear, R.id.btnDone};

        for (int buttonId : buttonIds) {
            Button button = keyboardView.findViewById(buttonId);
            button.setOnClickListener(v -> handleButtonClick(button.getText().toString()));
        }
    }

    private void setKeyboardButtonListeners() {
        // Add any additional keyboard button listeners if needed
    }

    private void handleButtonClick(String buttonText) {
        Editable editable = editText.getText();
        int start = editText.getSelectionStart();

        switch (buttonText) {
            case "删除":
                if (editable != null && editable.length() > 0 && start > 0) {
                    editable.delete(start - 1, start);
                }
                break;
            case "确认":
                if (onEnsureListener != null) {
                    onEnsureListener.onEnsure();
                }
                break;
            default:
                editable.insert(start, buttonText);
                break;
        }
    }

    public void showKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.INVISIBLE || visibility == View.GONE) {
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void hideKeyboard() {
        int visibility = keyboardView.getVisibility();
        if (visibility == View.VISIBLE || visibility == View.INVISIBLE) {
            keyboardView.setVisibility(View.GONE);
        }
    }
}