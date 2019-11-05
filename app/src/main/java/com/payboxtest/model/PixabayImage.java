package com.payboxtest.model;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import com.google.gson.annotations.SerializedName;

public class PixabayImage {
    @SerializedName("largeImageURL")
    private String largeImageURL;
    @SerializedName("webformatHeight")
    private Integer webformatHeight;
    @SerializedName("webformatWidth")
    private Integer webformatWidth;
    @SerializedName("likes")
    private Integer likes;
    @SerializedName("imageWidth")
    private Integer imageWidth;
    @SerializedName("id")
    private Integer id;
    @SerializedName("user_id")
    private Integer userId;
    @SerializedName("views")
    private Integer views;
    @SerializedName("comments")
    private Integer comments;
    @SerializedName("pageURL")
    private String pageURL;
    @SerializedName("imageHeight")
    private Integer imageHeight;
    @SerializedName("webformatURL")
    private String webformatURL;
    @SerializedName("type")
    private String type;
    @SerializedName("previewHeight")
    private Integer previewHeight;
    @SerializedName("tags")
    private String tags;
    @SerializedName("downloads")
    private Integer downloads;
    @SerializedName("user")
    private String user;
    @SerializedName("favorites")
    private Integer favorites;
    @SerializedName("imageSize")
    private Integer imageSize;
    @SerializedName("previewWidth")
    private Integer previewWidth;
    @SerializedName("userImageURL")
    private String userImageURL;
    @SerializedName("previewURL")
    private String previewURL;

    public String getLargeImageURL() {
        return largeImageURL;
    }

    public void setLargeImageURL(String largeImageURL) {
        this.largeImageURL = largeImageURL;
    }

    public Integer getWebformatHeight() {
        return webformatHeight;
    }

    public void setWebformatHeight(Integer webformatHeight) {
        this.webformatHeight = webformatHeight;
    }

    public Integer getWebformatWidth() {
        return webformatWidth;
    }

    public void setWebformatWidth(Integer webformatWidth) {
        this.webformatWidth = webformatWidth;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Integer getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(Integer imageWidth) {
        this.imageWidth = imageWidth;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public String getPageURL() {
        return pageURL;
    }

    public void setPageURL(String pageURL) {
        this.pageURL = pageURL;
    }

    public Integer getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(Integer imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getWebformatURL() {
        return webformatURL;
    }

    public void setWebformatURL(String webformatURL) {
        this.webformatURL = webformatURL;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPreviewHeight() {
        return previewHeight;
    }

    public void setPreviewHeight(Integer previewHeight) {
        this.previewHeight = previewHeight;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Integer getDownloads() {
        return downloads;
    }

    public void setDownloads(Integer downloads) {
        this.downloads = downloads;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public Integer getImageSize() {
        return imageSize;
    }

    public void setImageSize(Integer imageSize) {
        this.imageSize = imageSize;
    }

    public Integer getPreviewWidth() {
        return previewWidth;
    }

    public void setPreviewWidth(Integer previewWidth) {
        this.previewWidth = previewWidth;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getPreviewURL() {
        return previewURL;
    }

    public void setPreviewURL(String previewURL) {
        this.previewURL = previewURL;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        PixabayImage article = (PixabayImage) obj;
        return article.id == this.id;
    }

    public static DiffUtil.ItemCallback<PixabayImage> DIFF_CALLBACK = new DiffUtil.ItemCallback<PixabayImage>() {
        @Override
        public boolean areItemsTheSame(@NonNull PixabayImage oldItem, @NonNull PixabayImage newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull PixabayImage oldItem, @NonNull PixabayImage newItem) {
            return oldItem.equals(newItem);
        }
    };
}
