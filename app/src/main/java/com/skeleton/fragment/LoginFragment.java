package com.skeleton.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.activity.MainActivity;
import com.skeleton.activity.PhoneVerificationActivity;
import com.skeleton.activity.ProfileCompletenessActivity;
import com.skeleton.database.CommonData;
import com.skeleton.model.login.LoginResponse;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.Log;
import com.skeleton.util.ValidateEditText;

import java.util.HashMap;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;
import static com.skeleton.fragment.SignupFragment.clearEditText;

/**
 * MainActivity simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private EditText etUsernme, etPassword;
    private CheckBox checkRememberMe;
    private Button btSignIn, btSignInFb;
    private String mAccessToken;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, null);
        init(v);
        btSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (validateData()) {
                    checkUserExists();
                }
            }
        });
        return v;
    }

    /**
     * @param v layout view
     */
    private void init(final View v) {
        etUsernme = (EditText) v.findViewById(R.id.et_email_phone);
        etPassword = (EditText) v.findViewById(R.id.et_password);
        checkRememberMe = (CheckBox) v.findViewById(R.id.ch_remember);
        btSignIn = (Button) v.findViewById(R.id.bt_sign_in);
        btSignInFb = (Button) v.findViewById(R.id.bt_sign_in_fb);
    }

    /**
     * @return validation true
     */
    private boolean validateData() {
        if (!ValidateEditText.checkEmail(etUsernme)) {
            return false;
        } else if (!ValidateEditText.checkPassword(etPassword, false)) {
            Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    /**
     * login
     */
    private void checkUserExists() {
        HashMap<String, String> hashMap = new CommonParams.Builder()
                .add("email", etUsernme.getText().toString().trim())
                .add("password", etPassword.getText().toString().trim())
                .add("deviceType", "ANDROID")
                .add("language", "EN")
                .add("deviceToken", "tok")
                .add("flushPreviousSessions", true)
                .add("appVersion", "v").build().getMap();

        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.userLogin(hashMap).enqueue(new ResponseResolver<LoginResponse>(getActivity(), true, true) {
            @Override
            public void success(final LoginResponse loginResponse) {
                mAccessToken = loginResponse.getMessage();
                if ("200" .equals(loginResponse.getStatusCode().toString())) {
                    clearEditText(etUsernme, etPassword);
                    CommonData.saveUserData(loginResponse.getData());
                    if (checkRememberMe.isChecked()) {
                        CommonData.saveAccessToken(loginResponse.getData().getAccessToken());
                    }
                    if (!(loginResponse.getData().getUserDetails().getPhoneVerified())) {
                        CommonData.saveUserData(loginResponse.getData());
                        startActivity(new Intent(getActivity(), PhoneVerificationActivity.class));
                    } else if (!(loginResponse.getData().getUserDetails().getStep2CompleteOrSkip() && loginResponse
                            .getData().getUserDetails().getStep1CompleteOrSkip())) {
                        CommonData.saveUserData(loginResponse.getData());
                        startActivity(new Intent(getActivity(), ProfileCompletenessActivity.class));

                    } else {
                        startActivity(new Intent(getActivity(), MainActivity.class));
                    }

                   /* if (loginResponse.getData().getUserDetails().getPhoneVerified()) {
                        if (loginResponse.getData().getUserDetails().getStep1CompleteOrSkip() && loginResponse.
                                getData().getUserDetails().getStep2CompleteOrSkip()) {
                            startActivity(new Intent(getActivity(), MainActivity.class));
                        } else {

                            CommonData.saveAccessToken(loginResponse.getData().getAccessToken());
                            startActivity(new Intent(getActivity(), ProfileCompletenessActivity.class));
                        }
                    } else {
                        startActivity(new Intent(getActivity(), PhoneVerificationActivity.class));
                    }*/
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d(TAG, "failure: " + error.getMessage());
                Log.d(TAG, "failure: " + error.getStatusCode());
            }

        });
    }

    /**
     *  Login fragmeny instance
     * @return instance of login fragment
     */
    public static LoginFragment getInstance() {
        return new LoginFragment();

    }

}
