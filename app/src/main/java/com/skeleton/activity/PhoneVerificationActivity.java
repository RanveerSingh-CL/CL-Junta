package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.database.CommonData;
import com.skeleton.model.otp.Otp;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;

import java.util.HashMap;

/**
 * PhoneVerificationActivity
 */
public class PhoneVerificationActivity extends BaseActivity implements View.OnClickListener {
    private TextView tvVerifNumber, tvBarLabel, tvResendotp, tvEditNumber;
    private EditText etDigitOne, etDigitTwo, etDigitThree, etDigitFour;
    private Button btVerify;
    private String mOtpCode;
    private String mCountryCode, mPhone;
    private String mAccessToken;
    private ImageView ivBack;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        init();

    }

    /**
     * initialization
     */
    private void init() {
        tvVerifNumber = (TextView) findViewById(R.id.tv_verify_number);
        etDigitOne = (EditText) findViewById(R.id.tv_digit_one);
        etDigitTwo = (EditText) findViewById(R.id.tv_digit_two);
        etDigitThree = (EditText) findViewById(R.id.tv_digit_three);
        etDigitFour = (EditText) findViewById(R.id.tv_digit_four);
        btVerify = (Button) findViewById(R.id.bt_verify);
        ivBack = (ImageView) findViewById(R.id.bt_back);
        tvBarLabel = (TextView) findViewById(R.id.tv_appbar);
        tvResendotp = (TextView) findViewById(R.id.tv_resend_otp);
        tvEditNumber = (TextView) findViewById(R.id.tv_edit_number);
        ivBack.setVisibility(View.VISIBLE);
        tvBarLabel.setVisibility(View.VISIBLE);
        mAccessToken = "bearer " + CommonData.getAccessToken();
        mCountryCode = CommonData.getUserData().getUserDetails().getCountryCode();
        mPhone = CommonData.getUserData().getUserDetails().getPhoneNo();
        tvVerifNumber.setText(mCountryCode + " " + mPhone);
        btVerify.setOnClickListener(this);
        tvResendotp.setOnClickListener(this);
        ivBack.setOnClickListener(this);
    }

    /**
     * verify otp
     */
    private void confirmOtp() {
        mOtpCode = etDigitOne.getText().toString()
                + etDigitTwo.getText().toString()
                + etDigitThree.getText().toString()
                + etDigitFour.getText().toString();
        Toast.makeText(this, mOtpCode, Toast.LENGTH_SHORT).show();
        final HashMap<String, String> commonParams = new CommonParams.Builder()
                .add("countryCode", mCountryCode)
                .add("phoneNo", mPhone)
                .add("OTPCode", mOtpCode).build().getMap();


        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.verifyOtp(mAccessToken, commonParams).enqueue(new ResponseResolver<Otp>(this, true, true) {
            @Override
            public void success(final Otp otp) {
                if (!(CommonData.getUserData().getUserDetails().getStep1CompleteOrSkip() && CommonData.getUserData().getUserDetails().
                        getStep2CompleteOrSkip())) {
                    startActivity(new Intent(PhoneVerificationActivity.this, ProfileCompletenessActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(PhoneVerificationActivity.this, MainActivity.class));
                }
            }

            @Override
            public void failure(final APIError error) {

            }
        });
    }

    /**
     * resend otp request method
     */
    private void resendOTP() {

        ApiInterface resendOTP = RestClient.getApiInterface();
        resendOTP.resendOTP(mAccessToken).enqueue(new ResponseResolver<CommonResponse>(this, true, true) {
            @Override
            public void success(final CommonResponse commonResponse) {
                Toast.makeText(PhoneVerificationActivity.this, commonResponse.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(final APIError error) {

            }
        });

    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.bt_verify:
                confirmOtp();
                break;
            case R.id.tv_resend_otp:
                resendOTP();
                break;
            default:
            case R.id.bt_back:
                CommonData.clearData();
                startActivity(new Intent(PhoneVerificationActivity.this, LoginSignupActivity.class));
        }

    }
}
