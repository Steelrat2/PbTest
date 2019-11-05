package com.payboxtest.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DiscoverResponse {

    @SerializedName("totalHits")
    private Integer totalHits;
    @SerializedName("hits")
    private List<PixabayImage> hits = null;
    @SerializedName("total")
    private Integer total;

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

    public List<PixabayImage> getHits() {
        return hits;
    }

    public void setHits(List<PixabayImage> hits) {
        this.hits = hits;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

}
