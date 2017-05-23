package com.skeleton.model.otp;

/**
 * Created by rishucuber on 17/5/17.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Otp code for phone verification
 */
public class Otp {

    @SerializedName("OTP")
    @Expose
    private String mOtp;

    /**
     * Get Otp for phone verfication
     *
     * @return : otp
     */
    public String getOTP() {
        return mOtp;
    }

    /**
     * Set otp of user phone
     *
     * @param oTP : otp code
     */
    public void setOTP(final String oTP) {
        mOtp = oTP;
    }

}