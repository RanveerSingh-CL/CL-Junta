package com.skeleton.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kbeanie.multipicker.api.entity.ChosenImage;
import com.skeleton.R;
import com.skeleton.activity.PhoneVerificationActivity;
import com.skeleton.activity.ProfileCompletenessActivity;
import com.skeleton.database.CommonData;
import com.skeleton.model.signup.SignUpResponse;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;
import com.skeleton.util.imagepicker.ImageChooser;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

/**
 * MainActivity simple {@link Fragment} subclass.
 */
public class SignupFragment extends BaseFragment {
    private MaterialEditText etFname, etLname, rtOrientation, etCountryCode, etPhone, etEmail, etDob, etPassword, etCpassword;
    private CircleImageView ciProfileImage;
    private RadioGroup rgSexRadioGroup;
    private RadioButton rbMale, rbFemale;
    private CheckBox checkTerms;
    private Button btSignup;
    private ImageChooser imageChooser = new ImageChooser.Builder(SignupFragment.this).build();
    private boolean isImageSet = false;
    private File imageFile;
    private int mGender;
    private String mDateOfBirth;
    private String deviceToken;
    private String mOrientation;

    /**
     * Clear the string in the editext
     *
     * @param editText : multiple edittexts to be cleared
     */
    public static void clearEditText(final EditText... editText) {
        for (EditText et : editText) {
            et.setText("");
        }
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        imageChooser.onActivityResult(requestCode, resultCode, data);


    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_up, null);
        init(v);
        chooseImage();
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (validateData()) {
                    uploadData();
                }
            }
        });

        return v;
    }

    /**
     * initialization
     *
     * @param v layout view
     */

    private void init(final View v) {

        etFname = (MaterialEditText) v.findViewById(R.id.et_f_name);
        etLname = (MaterialEditText) v.findViewById(R.id.et_l_name);
        etCountryCode = (MaterialEditText) v.findViewById(R.id.et_country_code);
        etPhone = (MaterialEditText) v.findViewById(R.id.et_phone);
        etEmail = (MaterialEditText) v.findViewById(R.id.et_email);
        etDob = (MaterialEditText) v.findViewById(R.id.et_dob);
        etPassword = (MaterialEditText) v.findViewById(R.id.et_password);
        etCpassword = (MaterialEditText) v.findViewById(R.id.et_c_password);
        rtOrientation = (MaterialEditText) v.findViewById(R.id.et_orientation);
        ciProfileImage = (CircleImageView) v.findViewById(R.id.profile_image);
        rgSexRadioGroup = (RadioGroup) v.findViewById(R.id.sex_group);
        rbMale = (RadioButton) v.findViewById(R.id.radio_male);
        rbFemale = (RadioButton) v.findViewById(R.id.radio_female);
        checkTerms = (CheckBox) v.findViewById(R.id.cb_terms);
        btSignup = (Button) v.findViewById(R.id.bt_sign_up);
        rgSexRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup group, @IdRes final int checkedId) {
                if (checkedId == R.id.radio_male) {
                    mGender = 0;
                } else {
                    mGender = 1;
                }
            }
        });
        Log.d("gender", Integer.toString(mGender));
        deviceToken = CommonData.getFCMToken();
        rtOrientation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


            }
        });
    }

    /**
     * choose image
     */
    private void chooseImage() {
        ciProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                imageChooser.selectImage(new ImageChooser.OnImageSelectListener() {
                    @Override
                    public void loadImage(final List<ChosenImage> list) {
                        ChosenImage image = list.get(0);
                        imageFile = new File(String.valueOf(image.getQueryUri()));
                        Glide
                                .with(SignupFragment.this)
                                .load(image.getQueryUri())
                                .centerCrop()
                                .into(ciProfileImage);
                        isImageSet = true;

                    }

                    @Override
                    public void croppedImage(final File mCroppedImage) {

                    }
                });
                imageChooser.onActivityResult(1, 1, null);
            }
        });
    }

    /**
     * @return valid fields
     */

    private boolean validateData() {
        if (!ValidateEditText.checkName(etFname, true)) {
            return false;
        } else if (!ValidateEditText.checkName(etLname, false)) {
            return false;
        } else if (!ValidateEditText.checkPhoneNumber(etPhone)) {
            return false;
        } else if (!ValidateEditText.checkEmail(etEmail)) {
            return false;
        } else if (!ValidateEditText.checkPassword(etPassword, false)) {
            return false;
        } else if (!ValidateEditText.checkPassword(etCpassword, true)) {
            return false;
        } else if (!checkDOB(etDob)) {
            return false;
        } else if (!ValidateEditText.comparePassword(etPassword, etCpassword)) {
            Toast.makeText(getContext(), R.string.error_password_mismatch, Toast.LENGTH_SHORT).show();
            return false;
        } else if (!isImageSet) {
            Toast.makeText(getActivity(), "Please select Image", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!checkTerms.isChecked()) {
            Toast.makeText(getActivity(), "Please select Terms and Cond.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * upload data to server
     */
    private void uploadData() {
        final String mFname, mLname, mDob, mCountryCode, mPhone, mEmail, mPassword;
        mFname = etFname.getText().toString().trim();
        mLname = etLname.getText().toString().trim();
        mDob = etDob.getText().toString().trim();
        mCountryCode = etCountryCode.getText().toString().trim();
        mPhone = etPhone.getText().toString().trim();
        mEmail = etEmail.getText().toString().trim();
        mPassword = etPassword.getText().toString().trim();

        HashMap<String, RequestBody> multipartParams = new MultipartParams.Builder()
                .add("firstName", mFname)
                .add("lastName", mLname)
                .add("dob", mDob)
                .add("countryCode", mCountryCode)
                .add("phoneNo", mPhone)
                .add("email", mEmail)
                .add("password", mPassword)
                .add("language", "EN")
                .add("deviceType", "ANDROID")
                .add("deviceToken", deviceToken)
                .add("appVersion", "v")
                .add("gender", mGender)
                .add("orientation", "Straight")
                .add("profilePic", imageFile).build().getMap();
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.register(multipartParams).enqueue(new ResponseResolver<SignUpResponse>(getActivity(), true, true) {
            @Override
            public void success(final SignUpResponse signUpResponse) {
                CommonData.saveAccessToken(signUpResponse.getData().getAccessToken());
                CommonData.saveUserData(signUpResponse.getData());
                Toast.makeText(getContext(), signUpResponse.getMessage(), Toast.LENGTH_SHORT).show();
                if ("200".equals(signUpResponse.getStatusCode().toString())) {
                    clearEditText(etFname, etLname, etPhone, etDob,
                            etPassword, etCpassword, etEmail);
                    if (signUpResponse.getData().getUserDetails().getPhoneVerified()) {
                        startActivity(new Intent(getActivity(), ProfileCompletenessActivity.class));
                    } else {
                        startActivity(new Intent(getActivity(), PhoneVerificationActivity.class));
                    }
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d(TAG, "failure: Status " + error.getStatusCode());
                Log.d(TAG, "failure: Message" + error.getMessage());
            }
        });

    }

    /**
     * @param editText DOB EDITTEXT
     * @return DOB FORMAT
     */

    private boolean checkDOB(final EditText editText) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String s = editText.getText().toString();
        try {
            Date date = df.parse(s);
            mDateOfBirth = s;
            return true;

        } catch (ParseException e) {
            editText.setError(getString(R.string.invalid_date_error));
            return false;
        }
    }

    /**
     *  instance  Signup fragment
     * @return returns instance of Signup fragment
     */
    public static SignupFragment getInstance() {
        return new SignupFragment();

    }
}

