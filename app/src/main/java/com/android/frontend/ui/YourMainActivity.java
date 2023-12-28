package com.android.frontend.ui;

import android.os.Bundle;
import com.android.frontend.R;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class YourMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout1);

        // 获取 TextView 控件
        TextView textView = findViewById(R.id.textView);

        // 设置欢迎消息
        textView.setText("Welcome to Your App!");
    }
}