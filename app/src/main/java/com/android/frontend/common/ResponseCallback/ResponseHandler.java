package com.android.frontend.common.ResponseCallback;

import com.android.frontend.model.dto.BaseResponse;
import org.jetbrains.annotations.NotNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;

/**
 * @Description: 响应处理类
 * @Author: MING
 * @Date: 2024/01/06
 */

public class ResponseHandler {

    public static <T> void handleResponse(Call<BaseResponse<T>> call, ResponseCallback<T> callback) {
        call.enqueue(new Callback<>() {
            @Override
            public void onResponse(@NotNull Call<BaseResponse<T>> call, @NotNull Response<BaseResponse<T>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    handleSuccess(response.body(), callback);
                } else {
                    String errorMessage = (response.body() != null) ? response.body().getMessage() : null;
                    callback.onFailure(createThrowable(errorMessage));
                }
            }
            @Override
            public void onFailure(@NotNull Call<BaseResponse<T>> call, @NotNull Throwable t) {
                String errorMessage = isNetworkError(t) ? "网络连接问题，请检查网络设置" : null;
                callback.onFailure(createThrowable(errorMessage));
            }
        });
    }

    private static <T> void handleSuccess(BaseResponse<T> body, ResponseCallback<T> callback) {
        if (body.getCode() == 0) {
            callback.onSuccess(body.getData());
        } else {
            callback.onFailure(createThrowable(body.getMessage()));
        }
    }
    private static Throwable createThrowable(String errorMessage) {
        return new Throwable(errorMessage);
    }
    private static boolean isNetworkError(Throwable t) {
        return t instanceof IOException;
    }
}
