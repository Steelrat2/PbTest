package com.payboxtest.ui;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.paging.PagedList;
import androidx.viewpager.widget.ViewPager;

import com.payboxtest.MainActivity;
import com.payboxtest.R;
import com.payboxtest.model.PixabayImage;
import com.payboxtest.utils.NetworkState;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewPagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewPagerFragment extends Fragment {

    private static final String ARG_PARAM_TERM = "search_term";
    private static final String ARG_PARAM_POS = "position";
    private static final String CURRENT_POS_KEY = "current_position";


    private String searchTerm;
    private int startPosition;
    private ImagesListViewModel mViewModel;
    private ViewPagerAdapter mPagerAdapter;
    private ViewPager mViewPager;


    public ViewPagerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param searchTerm Parameter 1.
     * @param startPosition Parameter 2.
     * @return A new instance of fragment ViewPagerFragment.
     */
    public static ViewPagerFragment newInstance(String searchTerm, int startPosition) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_TERM, searchTerm);
        args.putInt(ARG_PARAM_POS, startPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            searchTerm = getArguments().getString(ARG_PARAM_TERM);
            startPosition = getArguments().getInt(ARG_PARAM_POS);
        }
        mViewModel = ViewModelProviders.of(
                getActivity().getSupportFragmentManager().findFragmentByTag(ImagesListFragment.class.getSimpleName()),
                new ViewModelProvider.Factory() {
            @NonNull
            @Override
            public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
                return (T)new ImagesListViewModel (getActivity().getApplication(), searchTerm);
            }
        }).get(ImagesListViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        if(savedInstanceState!=null) {
            startPosition = savedInstanceState.getInt(CURRENT_POS_KEY);
        }
        mViewPager = view.findViewById(R.id.viewPager);
        MainActivity activity = (MainActivity) getActivity();
        mPagerAdapter = new ViewPagerAdapter(activity);
        mViewPager.setAdapter(mPagerAdapter);
        mViewModel.getArticleLiveData().observe(this, new Observer<PagedList<PixabayImage>>() {
            @Override
            public void onChanged(PagedList<PixabayImage> images) {
                mPagerAdapter.submitList(images);
                mViewPager.setCurrentItem(startPosition);
            }
        });

        mViewModel.getNetworkState().observe(this, new Observer<NetworkState>() {
            @Override
            public void onChanged(NetworkState networkState) {
                if(networkState.getStatus()== NetworkState.Status.FAILED) {
                    Toast.makeText(getContext(), networkState.getMsg(), Toast.LENGTH_LONG).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_POS_KEY, mViewPager.getCurrentItem());
        super.onSaveInstanceState(outState);
    }

}
