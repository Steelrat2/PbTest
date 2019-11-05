package com.payboxtest.datasource;


import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;


import com.payboxtest.R;
import com.payboxtest.model.DiscoverResponse;
import com.payboxtest.model.PixabayImage;
import com.payboxtest.net.Client;
import com.payboxtest.net.PixabayApi;
import com.payboxtest.utils.NetworkState;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ImagesDataSource extends PageKeyedDataSource<Long, PixabayImage> {

    private static final String TAG = ImagesDataSource.class.getSimpleName();
    private static final int PER_PAGE = 20;
    private MutableLiveData<NetworkState> networkState = new MutableLiveData<>();;
    private MutableLiveData<NetworkState> initialLoading = new MutableLiveData<>();
    private String searchTerm;
    private Context context;

    public ImagesDataSource(Context context, String searchTerm) {
        this.context = context;
        this.searchTerm = searchTerm;
    }


    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData<NetworkState> getInitialLoading() {
        return initialLoading;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> params,
                            @NonNull final LoadInitialCallback<Long, PixabayImage> callback) {

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);


        PixabayApi pixabayApi = Client.getPixabayApi();
        Map<String, String> reqParam = new HashMap<>();
        if(!TextUtils.isEmpty(searchTerm))
            reqParam.put("q", searchTerm);
        reqParam.put("key", context.getString(R.string.api_key));
        reqParam.put("image_type", "photo");
        reqParam.put("orientation", "vertical");
        reqParam.put("per_page", String.valueOf(PER_PAGE));
        reqParam.put("page", "1");


        pixabayApi.searchImages(reqParam)
                .enqueue(new Callback<DiscoverResponse>() {
                    @Override
                    public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                        if(response.isSuccessful()) {
                            Long nextKey = (response.body().getTotalHits()<=PER_PAGE) ? null : 2L;
                            callback.onResult(response.body().getHits(), null, nextKey);
                            initialLoading.postValue(NetworkState.LOADED);
                            networkState.postValue(NetworkState.LOADED);

                        } else {
                            initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                            networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                        }
                    }

                    @Override
                    public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                        String errorMessage = t == null ? "unknown error" : t.getMessage();
                        networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> params,
                           @NonNull LoadCallback<Long, PixabayImage> callback) {

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Long> params,
                          @NonNull final LoadCallback<Long, PixabayImage> callback) {

        Log.i(TAG, "Loading Rang " + params.key + " Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        PixabayApi pixabayApi = Client.getPixabayApi();

        Map<String, String> reqParam = new HashMap<>();
        if(!TextUtils.isEmpty(searchTerm))
            reqParam.put("q", searchTerm);
        reqParam.put("key", context.getString(R.string.api_key));
        reqParam.put("image_type", "photo");
        reqParam.put("orientation", "vertical");
        reqParam.put("per_page", String.valueOf(PER_PAGE));
        reqParam.put("page", params.key.toString());

        pixabayApi.searchImages(reqParam).enqueue(new Callback<DiscoverResponse>() {
            @Override
            public void onResponse(Call<DiscoverResponse> call, Response<DiscoverResponse> response) {
                if(response.isSuccessful()) {
                    Long nextKey = (params.key < (response.body().getTotalHits()/PER_PAGE + 1)) ? (params.key + 1) : null;
                    callback.onResult(response.body().getHits(), nextKey);
                    networkState.postValue(NetworkState.LOADED);

                }
                else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<DiscoverResponse> call, Throwable t) {
                String errorMessage = t == null ? "unknown error" : t.getMessage();
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
            }
        });
    }
}
