package com.skeleton.model.profileConstants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data
 */
public class Data {

    @SerializedName("Orientation")
    @Expose
    private List<String> orientation = null;
    @SerializedName("RelationshipHistory")
    @Expose
    private List<String> relationshipHistory = null;
    @SerializedName("Ethnicity")
    @Expose
    private List<String> ethnicity = null;
    @SerializedName("Religion")
    @Expose
    private List<String> religion = null;
    @SerializedName("BodyType")
    @Expose
    private List<String> bodyType = null;
    @SerializedName("Smoking")
    @Expose
    private List<String> smoking = null;
    @SerializedName("Drinking")
    @Expose
    private List<String> drinking = null;
    @SerializedName("Height")
    @Expose
    private List<String> height = null;

    /**
     * @return orientation
     */
    public List<String> getOrientation() {
        return orientation;
    }

    /**
     * @param orientation orientation
     */

    public void setOrientation(final List<String> orientation) {
        this.orientation = orientation;
    }

    /**
     * @return relationshipHistory
     */
    public List<String> getRelationshipHistory() {
        return relationshipHistory;
    }

    /**
     * @param relationshipHistory relationshipHistory
     */
    public void setRelationshipHistory(final List<String> relationshipHistory) {
        this.relationshipHistory = relationshipHistory;
    }

    /**
     * @return ethnicity
     */
    public List<String> getEthnicity() {
        return ethnicity;
    }

    /**
     * @param ethnicity ethnicity
     */
    public void setEthnicity(final List<String> ethnicity) {
        this.ethnicity = ethnicity;
    }

    /**
     * @return religion
     */
    public List<String> getReligion() {
        return religion;
    }

    /**
     * @param religion religion
     */
    public void setReligion(final List<String> religion) {
        this.religion = religion;
    }

    /**
     * @return bodyType
     */
    public List<String> getBodyType() {
        return bodyType;
    }

    /**
     * @param bodyType bodyType
     */
    public void setBodyType(final List<String> bodyType) {
        this.bodyType = bodyType;
    }

    /**
     * @return smoking
     */
    public List<String> getSmoking() {
        return smoking;
    }

    /**
     * @param smoking smoking
     */
    public void setSmoking(final List<String> smoking) {
        this.smoking = smoking;
    }

    /**
     * @return drinking
     */
    public List<String> getDrinking() {
        return drinking;
    }

    /**
     * @param drinking drinking
     */
    public void setDrinking(final List<String> drinking) {
        this.drinking = drinking;
    }

    /**
     * @return height
     */
    public List<String> getHeight() {
        return height;
    }

    /**
     * @param height height
     */
    public void setHeight(final List<String> height) {
        this.height = height;
    }

}
