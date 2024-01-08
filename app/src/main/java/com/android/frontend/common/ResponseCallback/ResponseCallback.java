package com.android.frontend.common.ResponseCallback;

/**
 * @Description: 响应接口
 * @Author: MING
 * @Date: 2024/01/06
 */

public interface ResponseCallback<T> {
    void onSuccess(T response);
    void onFailure(Throwable t);
}
