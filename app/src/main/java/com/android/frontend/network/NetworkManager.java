package com.android.frontend.network;

import com.android.frontend.api.ApiManager;
import com.android.frontend.common.ResponseCallback.ResponseCallback;
import com.android.frontend.common.ResponseCallback.ResponseHandler;
import com.android.frontend.model.dto.BaseResponse;
import com.android.frontend.model.dto.UserLogin;
import com.android.frontend.common.utils.JsonUtils;
import com.android.frontend.model.dto.UserRegister;
import okhttp3.RequestBody;
import retrofit2.Call;

public class NetworkManager {

    public static void loginUser(UserLogin userLogin, ResponseCallback<Void> callback) {
        RequestBody requestBody = JsonUtils.buildJsonBody(userLogin);
        Call<BaseResponse<Void>> call = ApiManager.getApiService().loginUser(requestBody);
        ResponseHandler.handleResponse(call, callback);
    }
    public static void registerUser(UserRegister userRegister, ResponseCallback<Void> callback) {
        RequestBody requestBody = JsonUtils.buildJsonBody(userRegister);
        Call<BaseResponse<Void>> call = ApiManager.getApiService().registerUser(requestBody);
        ResponseHandler.handleResponse(call, callback);
    }
}



