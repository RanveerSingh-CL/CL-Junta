package com.skeleton.model.category;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data
 */
public class Data {

    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    /**
     * @return categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * @param categories categories
     */
    public void setCategories(final List<Category> categories) {
        this.categories = categories;
    }

}
