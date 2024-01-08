package com.android.frontend.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import com.android.frontend.R;
import com.android.frontend.ui.*;

public class MoreDialog extends Dialog implements View.OnClickListener {
    Button historyBtn,homeBtn,outBtn;
    ImageView errorIv;
    public MoreDialog(@NonNull Context context) {
        super(context);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_more);
        historyBtn = findViewById(R.id.dialog_more_btn_record);
        homeBtn = findViewById(R.id.dialog_more_btn_home);
        outBtn = findViewById(R.id.dialog_more_btn_out);
        errorIv = findViewById(R.id.dialog_more_iv);

        historyBtn.setOnClickListener(this);
        outBtn.setOnClickListener(this);
        homeBtn.setOnClickListener(this);
        errorIv.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.dialog_more_btn_record:
                intent.setClass(getContext(), HistoryActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_btn_home:
                intent.setClass(getContext(), HomeActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_btn_out:
                intent.setClass(getContext(), LoginActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.dialog_more_iv:
                break;
        }
        cancel();
    }

    /* 设置Dialog的尺寸和屏幕尺寸一致*/
    public void setDialogSize(){
//        获取当前窗口对象
        Window window = getWindow();
//        获取窗口对象的参数
        WindowManager.LayoutParams wlp = window.getAttributes();
//        获取屏幕宽度
        Display d = window.getWindowManager().getDefaultDisplay();
        wlp.width = (int)(d.getWidth());  //对话框窗口为屏幕窗口
        wlp.gravity = Gravity.BOTTOM;
        window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(wlp);
    }
}
