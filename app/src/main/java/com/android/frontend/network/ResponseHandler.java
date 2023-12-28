package com.android.frontend.network;

import com.android.frontend.model.dto.BaseResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

public class ResponseHandler {

    public static <T> void handleResponse(Call<BaseResponse<T>> call, NetworkManager.ResponseCallback<T> callback) {
        call.enqueue(new Callback<BaseResponse<T>>() {
            @Override
            public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccess(response.body(), callback);
                } else {
                    handleFailure(response, callback);
                }
            }

            @Override
            public void onFailure(Call<BaseResponse<T>> call, Throwable t) {
                handleNetworkError(t, callback);
            }
        });
    }

    private static <T> void handleSuccess(BaseResponse<T> body, NetworkManager.ResponseCallback<T> callback) {
        if (body.getCode() == 0) {
            callback.onSuccess(body.getData());
        } else {
            String errorMessage = body.getMessage();
            callback.onFailure(new Throwable(errorMessage));
        }
    }

    private static <T> void handleFailure(Response<BaseResponse<T>> response, NetworkManager.ResponseCallback<T> callback) {
        String errorMessage = null;
        if (response.body() != null) {
            errorMessage += response.body().getMessage();
        }
        callback.onFailure(new Throwable(errorMessage));
    }

    private static <T> void handleNetworkError(Throwable t, NetworkManager.ResponseCallback<T> callback) {
        if (isNetworkError(t)) {
            callback.onFailure(new Throwable("网络连接问题，请检查网络设置"));
        } else {
            callback.onFailure(t);
        }
    }

    private static boolean isNetworkError(Throwable t) {
        return t instanceof IOException;
    }
}






