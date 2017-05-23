package com.skeleton.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * picURL
 */
public class PicURL {

    @SerializedName("thumbnail")
    @Expose
    private String thumbnail;
    @SerializedName("original")
    @Expose
    private String original;

    /**
     * @return thumbnail
     */
    public String getThumbnail() {
        return thumbnail;
    }

    /**
     * @param thumbnail thumbnail
     */
    public void setThumbnail(final String thumbnail) {
        this.thumbnail = thumbnail;
    }

    /**
     * @return original
     */
    public String getOriginal() {
        return original;
    }

    /**
     * @param original original
     */
    public void setOriginal(final String original) {
        this.original = original;
    }

}
