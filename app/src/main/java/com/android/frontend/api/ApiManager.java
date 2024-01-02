package com.android.frontend.api;

import com.android.frontend.constant.CommonConstant;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiManager {
    private static volatile ApiService apiService;

    private ApiManager() {
        // private constructor to prevent instantiation
    }
    public static ApiService getApiService() {
        if (apiService == null) {
            synchronized (ApiManager.class) {
                if (apiService == null) {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(CommonConstant.BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    apiService = retrofit.create(ApiService.class);
                }
            }
        }
        return apiService;
    }
}
