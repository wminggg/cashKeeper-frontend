package com.android.frontend.network;

import com.android.frontend.api.ApiManager;
import com.android.frontend.model.dto.BaseResponse;
import com.android.frontend.model.dto.UserLogin;
import okhttp3.RequestBody;
import retrofit2.Call;

public class NetworkManager {

    public interface ResponseCallback<T> {
        void onSuccess(T response);
        void onFailure(Throwable t);
    }

    public static void loginUser(String userAccount, String userPassword, final ResponseCallback<Void> callback) {
        UserLogin userLogin = new UserLogin(userAccount, userPassword);
        RequestBody requestBody = RequestBuilder.buildJsonBody(userLogin);

        Call<BaseResponse<Void>> call = ApiManager.getApiService().loginUser(requestBody);
        ResponseHandler.handleResponse(call, callback);
    }
}



