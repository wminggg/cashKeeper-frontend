package com.android.frontend.network;

public interface ResponseCallback<T> {
    void onSuccess(T response);
    void onFailure(Throwable t);
}