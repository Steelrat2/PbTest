package com.payboxtest.ui;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.paging.AsyncPagedListDiffer;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.AsyncDifferConfig;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.viewpager.widget.PagerAdapter;

import com.payboxtest.R;
import com.payboxtest.model.PixabayImage;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ViewPagerAdapter extends PagerAdapter implements ListUpdateCallback {

    private final static String TAG = ViewPagerAdapter.class.getSimpleName();

    private final AsyncPagedListDiffer<PixabayImage> mDiffer =
            new AsyncPagedListDiffer(this, new AsyncDifferConfig.Builder<>(PixabayImage.DIFF_CALLBACK).build());
    private Context context;


    public ViewPagerAdapter(Context context) {
        assert context != null;
        this.context = context;
    }

    public void submitList(PagedList<PixabayImage> pagedList) {
        mDiffer.submitList(pagedList);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mDiffer.getItemCount();
    }

    @Override
    public void startUpdate(@NonNull ViewGroup container) {
        super.startUpdate(container);
        Log.d(TAG, "startUpdate... ");

    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, int position) {
        View imageLayout = LayoutInflater.from(context).inflate(R.layout.page_image_view, view, false);

        assert imageLayout != null;

        final ImageView imageView = (ImageView) imageLayout.findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) imageLayout.findViewById(R.id.progressBarPage);

        final PixabayImage pixabayImage = mDiffer.getItem(position);

       // progressMng.showDialogProgress();
        Picasso.with(context).load(pixabayImage.getLargeImageURL()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Log.e(TAG, "Error: Cannot load the image from URL: " + pixabayImage.getLargeImageURL());
                Toast.makeText(context, "Cannot load the Image", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.ic_no_image);
            }
        });

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
        Log.d(TAG, "restoreState ");
    }

    @Override
    public Parcelable saveState() {
        Log.d(TAG, "saveState ");
        return null;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }


    @Override
    public void onInserted(int position, int count) {
        Log.d(TAG, "onInserted position="+position+" count="+count);
        notifyDataSetChanged();
    }

    @Override
    public void onRemoved(int position, int count) {
        Log.d(TAG, "onRemoved position="+position+" count="+count);
        notifyDataSetChanged();
    }

    @Override
    public void onMoved(int fromPosition, int toPosition) {
        Log.d(TAG, "onMoved fromPosition="+fromPosition+" toPosition="+toPosition);
        notifyDataSetChanged();
    }

    @Override
    public void onChanged(int position, int count, @Nullable Object payload) {
        Log.d(TAG, "onChanged position="+position+" count="+count);
        notifyDataSetChanged();
    }
}
