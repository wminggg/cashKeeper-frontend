package com.android.frontend.common.utils;

import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class JsonUtils {
    private static final Gson gson = new Gson();

    public static RequestBody buildJsonBody(Object body) {
        return RequestBody.create(gson.toJson(body), MediaType.parse("application/json; charset=utf-8"));
    }
}
