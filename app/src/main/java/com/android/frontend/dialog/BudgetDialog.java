package com.android.frontend.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.*;
import android.widget.Button;
import android.widget.EditText;
import com.android.frontend.R;
import com.android.frontend.common.utils.SnackbarUtils;
import com.android.frontend.model.enums.SnackbarType;

/**
 * @Description: 预算对话框类，用于输入预算金额
 * @Author: MING
 * @Date: 2024/01/07
 */

public class BudgetDialog extends Dialog implements View.OnClickListener {
    private EditText moneyEt;
    private OnEnsureListener onEnsureListener;
    private final View currentView;

    /**
     * 确定按钮点击事件回调接口
     */
    public interface OnEnsureListener {
        void onEnsure(float money);
    }

    /**
     * 设置确定按钮点击监听器
     *
     * @param onEnsureListener 确定按钮点击事件回调接口实例
     */
    public void setOnEnsureListener(OnEnsureListener onEnsureListener) {
        this.onEnsureListener = onEnsureListener;
    }

    /**
     * 构造函数，接收上下文和当前视图
     *
     * @param context     上下文
     * @param currentView 当前视图
     */
    public BudgetDialog(Context context, View currentView) {
        super(context);
        this.currentView = currentView;
        init();
    }

    /**
     * 初始化对话框
     */
    private void init() {
        setContentView(R.layout.dialog_budget);

        Button cancelBtn = findViewById(R.id.dialog_budget_btn_cancel);
        Button ensureBtn = findViewById(R.id.dialog_budget_btn_ensure);
        moneyEt = findViewById(R.id.dialog_budget_et);

        cancelBtn.setOnClickListener(this);
        ensureBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.dialog_budget_btn_cancel) {
            cancel();  // 取消对话框
        } else if (viewId == R.id.dialog_budget_btn_ensure) {
            // 获取输入数据数值
            String data = moneyEt.getText().toString();
            if (TextUtils.isEmpty(data)) {
                SnackbarUtils.showCustomSnackbar(currentView, "输入数据不能为空！", SnackbarType.DEFAULT);
                return;
            }
            float money = Float.parseFloat(data);
            if (money <= 0) {
                SnackbarUtils.showCustomSnackbar(currentView, "预算金额必须大于0！", SnackbarType.DEFAULT);
                return;
            }
            if (onEnsureListener != null) {
                onEnsureListener.onEnsure(money);
            }
            cancel();
        }
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
