package com.android.frontend.model.dto;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class UserLogin {

    @SerializedName("userAccount")
    private String userAccount;

    @SerializedName("userPassword")
    private String userPassword;

    public UserLogin(String userAccount, String userPassword) {
        this.userAccount = userAccount;
        this.userPassword = userPassword;
    }

}