package com.android.frontend.common.ResponseCallback;

import androidx.core.util.Consumer;

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
