package com.android.frontend.common.ResponseCallback.impl;

import androidx.core.util.Consumer;
import com.android.frontend.common.ResponseCallback.ResponseCallback;

/**
 * @Description: 响应接口实现类
 * @Author: MING
 * @Date: 2024/01/06
 */

public class ResponseCallbackImpl<T> implements ResponseCallback<T> {
    private final Consumer<T> successConsumer;
    private final Consumer<Throwable> failureConsumer;

    public ResponseCallbackImpl(Consumer<T> successConsumer, Consumer<Throwable> failureConsumer) {
        this.successConsumer = successConsumer;
        this.failureConsumer = failureConsumer;
    }

    @Override
    public void onSuccess(T response) {
        successConsumer.accept(response);
    }

    @Override
    public void onFailure(Throwable t) {
        failureConsumer.accept(t);
    }
}
