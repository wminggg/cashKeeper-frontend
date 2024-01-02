package com.android.frontend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.android.frontend.common.ResponseCallback.ResponseCallback;
import com.android.frontend.databinding.AuthLoginLayoutBinding;
import com.android.frontend.model.dto.UserLogin;
import com.android.frontend.model.enums.SnackbarType;
import com.android.frontend.network.NetworkManager;
import com.android.frontend.common.utils.SnackbarUtils;

public class LoginActivity extends AppCompatActivity {
    // 使用 ViewBinding 自动生成的绑定类
    private AuthLoginLayoutBinding authLoginLayoutBinding;

    // 表示当前页面
    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authLoginLayoutBinding = AuthLoginLayoutBinding.inflate(getLayoutInflater());
        setContentView(authLoginLayoutBinding.getRoot());

        // 在onCreate中初始化currentView
        currentView = findViewById(android.R.id.content);

        // 登录页面的Snackbar
        SnackbarUtils.showCustomSnackbar(currentView, "请登录", SnackbarType.DEFAULT);

        authLoginLayoutBinding.LoginButton.setOnClickListener(v -> handleLogin());
        authLoginLayoutBinding.ToRegisterButton.setOnClickListener(v -> handleToRegister());
    }

    private void handleLogin() {
        String userAccount = authLoginLayoutBinding.UserNameEdit.getText().toString();
        String userPassword = authLoginLayoutBinding.PassWordEdit.getText().toString();

        if (userAccount.isEmpty()) {
            SnackbarUtils.showCustomSnackbar(currentView, "用户名不能为空", SnackbarType.DEFAULT);
            return;
        }

        if (userPassword.isEmpty()) {
            SnackbarUtils.showCustomSnackbar(currentView, "密码不能为空", SnackbarType.DEFAULT);
            return;
        }

        UserLogin userLogin = new UserLogin(userAccount, userPassword);
        NetworkManager.loginUser(userLogin, new ResponseCallback<>() {
            @Override
            public void onSuccess(Void response) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    // 在登录成功后再次显示 Snackbar
                    SnackbarUtils.showCustomSnackbar(currentView, "登录成功", SnackbarType.SUCCESS);
                });
            }

            @Override
            public void onFailure(Throwable t) {
                runOnUiThread(() -> {
                    String errorMessage = t.getMessage();
                    SnackbarUtils.showCustomSnackbar(currentView, errorMessage, SnackbarType.FAILURE);
                });
            }
        });
    }


    private void handleToRegister() {
        runOnUiThread(() -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
}