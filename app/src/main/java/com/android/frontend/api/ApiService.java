package com.android.frontend.api;

import com.android.frontend.model.dto.BaseResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("users/login")
    Call<BaseResponse<Void>> loginUser(@Body RequestBody requestBody);

    @POST("users/register")
    Call<BaseResponse<Void>> registerUser(@Body RequestBody requestBody);

}