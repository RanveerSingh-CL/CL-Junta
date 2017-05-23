package com.skeleton.model;

import java.io.Serializable;


/**
 * CategoryInformation
 */
public class CategoryInformation implements Serializable {
    private String mCategoryName, mUrlThumbnail, mUrlOriginal;

    /**
     * @param mCategoryName category Label
     * @param mUrlThumbnail mUrlThumbnail
     * @param mUrlOriginal  mUrlOriginal
     */
    public CategoryInformation(final String mCategoryName, final String mUrlThumbnail, final String mUrlOriginal) {
        this.mCategoryName = mCategoryName;
        this.mUrlThumbnail = mUrlThumbnail;
        this.mUrlOriginal = mUrlOriginal;
    }

    /**
     * @return mCategoryName
     */
    public String getmCategoryName() {
        return mCategoryName;
    }

    /**
     * @return mCategoryName
     */
    public String getmUrlThumbnail() {
        return mUrlThumbnail;
    }

    /**
     * @return mUrlOriginal
     */
    public String getmUrlOriginal() {
        return mUrlOriginal;
    }


}
