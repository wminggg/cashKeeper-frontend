package com.android.frontend.ui;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.android.frontend.databinding.ActivityMainBinding;
import com.android.frontend.model.enums.ToastType;
import com.android.frontend.network.NetworkManager;
import com.android.frontend.utils.ToastUtils;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding; // 使用 ViewBinding 自动生成的绑定类

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView() {
        binding.btnLogin.setOnClickListener(v -> {
            String userAccount = binding.editUsername.getText().toString();
            String userPassword = binding.editPwd.getText().toString();

            NetworkManager.loginUser(userAccount, userPassword, new NetworkManager.ResponseCallback<>() {
                @Override
                public void onSuccess(Void response) {
                    runOnUiThread(() -> {
                        Intent intent = new Intent(MainActivity.this, YourMainActivity.class);
                        startActivity(intent);
                        ToastUtils.showCustomToast(MainActivity.this, "登录成功", ToastType.SUCCESS);
                    });
                }
                @Override
                public void onFailure(Throwable t) {
                    runOnUiThread(() -> {
                        String errorMessage = t.getMessage(); // 获取具体的错误消息
                        ToastUtils.showCustomToast(MainActivity.this, errorMessage, ToastType.FAILURE);
                    });
                }
            });
        });
    }
}


