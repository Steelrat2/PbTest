package com.payboxtest.datasource;


import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.payboxtest.model.PixabayImage;


public class ImagesFactory extends DataSource.Factory<Long, PixabayImage> {

    private MutableLiveData<ImagesDataSource> mutableLiveData;
    private ImagesDataSource moviesDataSource;
    private TypeRepository typeRepository;
    private String searchTerm;
    private Context context;

    public enum TypeRepository {
        CLOUD,
        DB
    }

    public ImagesFactory(Context context, TypeRepository typeRepository, String searchTerm) {
        this.searchTerm = searchTerm;
        this.typeRepository = typeRepository;
        this.mutableLiveData = new MutableLiveData<>();
        this.context = context;
    }

    @Override
    public DataSource<Long, PixabayImage> create() {
        moviesDataSource = new ImagesDataSource(context, searchTerm);
        mutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }


    public MutableLiveData<ImagesDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}
