package com.skeleton.model.getuser;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.skeleton.model.Data;

/**
 * GetUserResponse
 */
public class GetUserResponse {

    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private Data data;

    /**
     * @return : statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode :statusCode
     */
    public void setStatusCode(final Integer statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * @return : message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message : message
     */
    public void setMessage(final String message) {
        this.message = message;
    }

    /**
     * @return : data
     */
    public Data getData() {
        return data;
    }

    /**
     * @param data : data
     */
    public void setData(final Data data) {
        this.data = data;
    }

}
