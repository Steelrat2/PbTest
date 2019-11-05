package com.payboxtest.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.payboxtest.MyApplication;
import com.payboxtest.datasource.ImagesDataSource;
import com.payboxtest.datasource.ImagesFactory;
import com.payboxtest.model.PixabayImage;
import com.payboxtest.utils.NetworkState;

import java.util.concurrent.Executor;

public class ImagesListViewModel extends AndroidViewModel {

    public ImagesListViewModel(@NonNull Application application, String searchTerm ) {
        super(application);
        init(searchTerm);
    }

    private LiveData<NetworkState> networkState;
    private LiveData<PagedList<PixabayImage>> articleLiveData;


    private void init(String searchTerm) {

        Executor executor = ((MyApplication)getApplication()).getExecutor();

        ImagesFactory repository = new ImagesFactory(getApplication(), ImagesFactory.TypeRepository.CLOUD, searchTerm);
        networkState = Transformations.switchMap(repository.getMutableLiveData(),
                new Function<ImagesDataSource, LiveData<NetworkState>>() {
                    @Override
                    public LiveData<NetworkState> apply(ImagesDataSource dataSource) {
                        return dataSource.getNetworkState();
                    }
                });


        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                //    .setInitialLoadSizeHint(20)
                .setPageSize(20)
                .build();

        articleLiveData = new LivePagedListBuilder<>(repository, pagedListConfig)
                .setFetchExecutor(executor)
                .build();
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public LiveData<PagedList<PixabayImage>> getArticleLiveData() {
        return articleLiveData;
    }

}
