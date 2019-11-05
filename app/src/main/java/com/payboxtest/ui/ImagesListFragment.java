package com.payboxtest.ui;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.payboxtest.MainActivity;
import com.payboxtest.R;
import com.payboxtest.model.PixabayImage;
import com.payboxtest.utils.NetworkState;
import com.payboxtest.utils.Utils;

public class ImagesListFragment extends Fragment {

    public interface OnImagesListFragmentListener {
        void onClickOnImage(int position);
    }

    private static final String ARG_PARAM = "param_search";

    private String searchTerm;
    private ImagesListViewModel mViewModel;
    private OnImagesListFragmentListener mListener;

    public static ImagesListFragment newInstance(String searchTerm) {
        ImagesListFragment fragment = new ImagesListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, searchTerm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchTerm = getArguments().getString(ARG_PARAM);
        }
        mViewModel = ViewModelProviders.of(this, new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T)new ImagesListViewModel (getActivity().getApplication(), searchTerm);
            }
        }).get(ImagesListViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_images_list, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        final RecyclerView recyclerView = view.findViewById(R.id.moviesRecyclerView);

//        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
//        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
//        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        Configuration configuration = getResources().getConfiguration();
        int screenWidthDp = configuration.screenWidthDp;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), (int)(screenWidthDp/142)));

        final ImagesAdapter imagesAdapter = new ImagesAdapter(mListener);

        recyclerView.setAdapter(imagesAdapter);

        mViewModel.getArticleLiveData().observe(this, new Observer<PagedList<PixabayImage>>() {
            @Override
            public void onChanged(PagedList<PixabayImage> images) {
                imagesAdapter.submitList(images);
                DialogProgressManagement dialogProgress = (DialogProgressManagement)getActivity();
                if(dialogProgress!=null)
                    dialogProgress.dismissDialogProgress();
            }
        });

        mViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                imagesAdapter.setNetworkState(networkState);
                if(networkState.getStatus()== NetworkState.Status.FAILED) {
                    DialogProgressManagement dialogProgress = (DialogProgressManagement)getActivity();
                    if(dialogProgress!=null)
                        dialogProgress.dismissDialogProgress();
                    Toast.makeText(getContext(), networkState.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });

        recyclerView.requestFocus();

        ((DialogProgressManagement)getActivity()).showDialogProgress();

        view.postDelayed(new Runnable() {
            @Override
            public void run() {
                Activity activity = getActivity();
                if(activity!=null) {
                    recyclerView.requestFocus();
                    Utils.hideKeyboard(activity);
                }
            }
        }, 180);

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnImagesListFragmentListener)
            mListener = (OnImagesListFragmentListener)context;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        if(activity instanceof OnImagesListFragmentListener)
            mListener = (OnImagesListFragmentListener)activity;
    }

}
