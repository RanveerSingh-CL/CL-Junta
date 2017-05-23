package com.skeleton.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Category
 */
public class Category {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("categoryType")
    @Expose
    private String categoryType;
    @SerializedName("updatedAt")
    @Expose
    private String updatedAt;
    @SerializedName("createdAt")
    @Expose
    private String createdAt;
    @SerializedName("isDeleted")
    @Expose
    private Boolean isDeleted;
    @SerializedName("picURL")
    @Expose
    private PicURL picURL;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * @return category id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id category id
     */
    public void setId(final String id) {
        this.id = id;
    }

    /**
     * @return categoryType
     */
    public String getCategoryType() {
        return categoryType;
    }

    /**
     * @param categoryType categoryType
     */
    public void setCategoryType(final String categoryType) {
        this.categoryType = categoryType;
    }

    /**
     * @return updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt updatedAt
     */
    public void setUpdatedAt(final String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * @return createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt createdAt
     */
    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return isDeleted
     */
    public Boolean getIsDeleted() {
        return isDeleted;
    }

    /**
     * @param isDeleted isDeleted
     */
    public void setIsDeleted(final Boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    /**
     * @return picURL
     */
    public PicURL getPicURL() {
        return picURL;
    }

    /**
     * @param picURL picURL
     */
    public void setPicURL(final PicURL picURL) {
        this.picURL = picURL;
    }

    /**
     * @return category name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name category name
     */
    public void setName(final String name) {
        this.name = name;
    }

}
