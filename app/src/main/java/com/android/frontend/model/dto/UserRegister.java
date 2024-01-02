package com.android.frontend.model.dto;

import lombok.Data;

@Data
public class UserRegister {
    private String userAccount;

    private String userPassword;

    private String checkPassword;

    public UserRegister(String userAccount, String userPassword, String checkPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
        this.checkPassword = checkPassword;
    }

}
