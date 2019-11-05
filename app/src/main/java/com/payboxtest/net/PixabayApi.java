package com.payboxtest.net;

import com.payboxtest.model.DiscoverResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface PixabayApi {

    @GET("api")
    Call<DiscoverResponse> searchImages(@QueryMap Map<String, String> options);
}
