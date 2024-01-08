package com.android.frontend.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.android.frontend.R;

/**
 * @Description: 自定义带有输入框的对话框，用于输入备注信息等
 * @Author: MING
 * @Date: 2024/01/06
 */
public class NoteDialog extends Dialog implements View.OnClickListener {

    private EditText et;
    private OnEnsureListener onEnsureListener;
    public NoteDialog(@NonNull Context context) {
        super(context);
        init();
    }

    private void init() {
        setContentView(R.layout.dialog_note);
        et = findViewById(R.id.dialog_note_et);
        Button cancelBtn = findViewById(R.id.dialog_note_btn_cancel);
        Button ensureBtn = findViewById(R.id.dialog_note_btn_ensure);
        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }


    /**
     * @Description: 回调接口，用于在“确定”按钮点击时执行相应操作
     * @Author: MING
     * @Date: 2024/01/06
     */

    public interface OnEnsureListener {
        void onEnsure();
    }

    /**
     * @Description: 设置确定按钮点击监听器
     * @see OnEnsureListener
     */

    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.dialog_note_btn_cancel) {
            cancel();
        } else if (viewId == R.id.dialog_note_btn_ensure) {
            if (onEnsureListener != null) {
                onEnsureListener.onEnsure();
            }
        }
    }

    /**
     * @Description: 获取输入框内容
     * @see String
     */

    public String getEditText() {
        return et.getText().toString().trim();
    }

    /**
     * @Description: 设置对话框尺寸，使其显示在屏幕中央
     */
    public void setDialogSize() {
        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();

        // 修改gravity为屏幕中央
        wlp.gravity = Gravity.CENTER;

        // 使用ViewTreeObserver获取正确的宽度
        ViewTreeObserver vto = getWindow().getDecorView().getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                wlp.width = getWindow().getDecorView().getWidth();
                window.setBackgroundDrawableResource(android.R.color.transparent);
                window.setAttributes(wlp);

                // 移除监听器以避免多次调用
                getWindow().getDecorView().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }

}

