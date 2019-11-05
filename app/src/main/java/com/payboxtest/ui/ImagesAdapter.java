package com.payboxtest.ui;



import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;


import com.payboxtest.R;
import com.payboxtest.model.PixabayImage;
import com.payboxtest.utils.NetworkState;
import com.squareup.picasso.Picasso;


public class ImagesAdapter extends PagedListAdapter<PixabayImage, ImagesAdapter.ImageHolder> {


    public static class ImageHolder extends RecyclerView.ViewHolder {

        ImageView poster;

        public ImageHolder(View view) {
            super(view);
            poster = (ImageView) view.findViewById(R.id.posterImageView);
        }
    }

    private NetworkState newNetworkState;
    private ImagesListFragment.OnImagesListFragmentListener listener;

    public ImagesAdapter(ImagesListFragment.OnImagesListFragmentListener listener) {
        super(PixabayImage.DIFF_CALLBACK);
        this.listener = listener;
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.
                from(parent.getContext()).
                inflate(R.layout.card_image, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImagesAdapter.ImageHolder holder, final int position) {
        if (holder instanceof ImageHolder) {
            final PixabayImage image = getItem(position);

            Picasso.with(holder.itemView.getContext()).load(image.getPreviewURL()).into(holder.poster);

            holder.poster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null) {
                        listener.onClickOnImage(position);
                    }
                }
            });

        }
    }

    public void setNetworkState(NetworkState newNetworkState) {
        this.newNetworkState = newNetworkState;
    }

}
