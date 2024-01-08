package com.android.frontend.api;

import com.android.frontend.model.dto.BaseResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.List;

/**
 * @Description: 网络请求接口
 * @Author: MING
 * @Date: 2024/01/06
 */

public interface ApiService {


    /**
     * @Description: 登录
     * @see RequestBody
     * @see Call<BaseResponse<Void>>
     */
    @POST("users/login")
    Call<BaseResponse<Void>> loginUser(@Body RequestBody requestBody);

    /**
     * @Description: 注册
     * @see RequestBody
     * @see Call<BaseResponse<Void>>
     */
    @POST("users/register")
    Call<BaseResponse<Void>> registerUser(@Body RequestBody requestBody);

}