package com.android.frontend.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.android.frontend.R;
import com.android.frontend.common.ResponseCallback.ResponseCallback;
import com.android.frontend.common.utils.SnackbarUtils;
import com.android.frontend.databinding.AuthRegisterLayoutBinding;
import com.android.frontend.model.dto.UserRegister;
import com.android.frontend.model.enums.SnackbarType;
import com.android.frontend.network.NetworkManager;

public class RegisterActivity extends AppCompatActivity {

    private AuthRegisterLayoutBinding authRegisterLayoutBinding;

    // 表示当前页面
    private View currentView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authRegisterLayoutBinding = AuthRegisterLayoutBinding.inflate(getLayoutInflater());
        setContentView(authRegisterLayoutBinding.getRoot());

        // 在onCreate中初始化currentView
        currentView = findViewById(android.R.id.content);

        // 登录页面的Snackbar
        SnackbarUtils.showCustomSnackbar(currentView, "请注册", SnackbarType.DEFAULT);

        authRegisterLayoutBinding.RegisterButton.setOnClickListener(v -> handleRegister());
        authRegisterLayoutBinding.ToLoginButton.setOnClickListener(v -> handleToLogin());
    }

    private void handleRegister() {
        String userAccount = authRegisterLayoutBinding.UserNameEdit.getText().toString();
        String userPassword = authRegisterLayoutBinding.PassWordEdit.getText().toString();
        String checkPassword = authRegisterLayoutBinding.CheckPasswordEdit.getText().toString();
        if (userAccount.isEmpty()) {
            SnackbarUtils.showCustomSnackbar(currentView, "账号不能为空", SnackbarType.DEFAULT);
            return;

        }
        if (userPassword.isEmpty()) {
            SnackbarUtils.showCustomSnackbar(currentView, "密码不能为空", SnackbarType.DEFAULT);
            return;

        }
        if (checkPassword.isEmpty()) {
            SnackbarUtils.showCustomSnackbar(currentView, "请再次输入您的密码", SnackbarType.DEFAULT);
            return;

        }
        UserRegister userRegister = new UserRegister(userAccount, userPassword, checkPassword);
        NetworkManager.registerUser(userRegister, new ResponseCallback<>() {
            @Override
            public void onSuccess(Void response) {
                runOnUiThread(() -> {
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                    SnackbarUtils.showCustomSnackbar(currentView, "注册成功", SnackbarType.SUCCESS);
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

    private void handleToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
