package com.payboxtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.widget.SearchView;

import com.payboxtest.ui.DialogProgressManagement;
import com.payboxtest.ui.ImagesListFragment;
import com.payboxtest.ui.ProgressDialogFragment;
import com.payboxtest.ui.ViewPagerFragment;
import com.payboxtest.utils.Utils;

public class MainActivity extends AppCompatActivity implements DialogProgressManagement, ImagesListFragment.OnImagesListFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String SEARCH_TERM_KEY = "search_term";
    private final ProgressDialogFragment mProgressDialog = new ProgressDialogFragment();
    private String serchTerm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        SearchView searchView = findViewById(R.id.simpleSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {

                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "Search query: "+query);
                replaceFragment(ImagesListFragment.newInstance(query));
                serchTerm = query;
                return false;
            }

        });

        if(savedInstanceState != null) {
            serchTerm = savedInstanceState.getString(SEARCH_TERM_KEY);
        }

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if(!TextUtils.isEmpty(serchTerm))
            outState.putString(SEARCH_TERM_KEY, serchTerm);
        super.onSaveInstanceState(outState);
    }

    protected void replaceFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if(fm != null) {
            fm.beginTransaction().replace(R.id.mountFrameLayout, fragment, fragment.getClass().getSimpleName()).commitAllowingStateLoss();
        }
    }

    protected void addFragment(@NonNull Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        if(fm != null) {
            fm.beginTransaction().add(R.id.mountFrameLayout, fragment, fragment.getClass().getSimpleName()).addToBackStack(fragment.getClass().getName()).commitAllowingStateLoss();
        }
    }

    @Override
    public void showDialogProgress() {
        if(!mProgressDialog.isAdded()) {
            mProgressDialog.show(getSupportFragmentManager(), "progressDialog");
        }
    }

    @Override
    public void dismissDialogProgress() {
        mProgressDialog.dismissAllowingStateLoss();
    }

    @Override
    public void onClickOnImage(int position) {
        Log.d(TAG, "Image clicked pos="+position);
        addFragment(ViewPagerFragment.newInstance(serchTerm, position));
    }
}
