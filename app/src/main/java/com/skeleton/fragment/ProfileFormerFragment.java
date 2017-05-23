package com.skeleton.fragment;


import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.skeleton.R;
import com.skeleton.activity.ProfileCompletenessActivity;
import com.skeleton.database.CommonData;
import com.skeleton.model.profileConstants.ProfileConstantsResponse;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonResponse;
import com.skeleton.retrofit.MultipartParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;
import com.skeleton.util.ValidateEditText;
import com.skeleton.util.customview.MaterialEditText;

import java.util.HashMap;
import java.util.List;

import okhttp3.RequestBody;

/**
 * ProfileFormerFragment
 */
public class ProfileFormerFragment extends BaseFragment implements View.OnClickListener {
    private MaterialEditText etRelation, etEthnicity, etReligion, etHeight, etBodyType, etSmoking, etDrinking;
    private View vRelation, vEthnicity, vReligion, vHeight, vBodyType, vSmoking, vDrinking;
    private List<String> mRelationList, mEthnicityList, mReligionList, mHeightList,
            mBodyTypeList, mSmokingList, mDrinkingList;
    private String mAccessKey;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_former, null);
        init(v);
        getProfileConstants();
        return v;
    }

    /**
     * initialization
     *
     * @param v view
     */
    private void init(final View v) {
        etRelation = (MaterialEditText) v.findViewById(R.id.et_relation);
        etEthnicity = (MaterialEditText) v.findViewById(R.id.et_ethnicity);
        etReligion = (MaterialEditText) v.findViewById(R.id.et_religion);
        etHeight = (MaterialEditText) v.findViewById(R.id.et_height);
        etBodyType = (MaterialEditText) v.findViewById(R.id.et_body_type);
        etSmoking = (MaterialEditText) v.findViewById(R.id.et_smoking);
        etDrinking = (MaterialEditText) v.findViewById(R.id.et_drinking);
        //views
        vRelation = v.findViewById(R.id.v_relation);
        vEthnicity = v.findViewById(R.id.v_ethnicity);
        vReligion = v.findViewById(R.id.v_religion);
        vHeight = v.findViewById(R.id.v_height);
        vBodyType = v.findViewById(R.id.v_body_type);
        vSmoking = v.findViewById(R.id.v_smoking);
        vDrinking = v.findViewById(R.id.v_drinking);
        //setting on click listener
        etRelation.setOnClickListener(this);
        etEthnicity.setOnClickListener(this);
        etReligion.setOnClickListener(this);
        etHeight.setOnClickListener(this);
        etBodyType.setOnClickListener(this);
        etSmoking.setOnClickListener(this);
        etDrinking.setOnClickListener(this);
        mAccessKey = "bearer " + CommonData.getUserData().getAccessToken();
    }

    /**
     * get profile constants
     */
    private void getProfileConstants() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getConstants().enqueue(new ResponseResolver<ProfileConstantsResponse>(getActivity(), true, true) {
            @Override
            public void success(final ProfileConstantsResponse profileConstantsResponse) {
                if ("200" .equals(profileConstantsResponse.getStatusCode().toString())) {
                    mRelationList = profileConstantsResponse.getData().getRelationshipHistory();
                    mEthnicityList = profileConstantsResponse.getData().getEthnicity();
                    mReligionList = profileConstantsResponse.getData().getReligion();
                    mHeightList = profileConstantsResponse.getData().getHeight();
                    mBodyTypeList = profileConstantsResponse.getData().getBodyType();
                    mSmokingList = profileConstantsResponse.getData().getSmoking();
                    mDrinkingList = profileConstantsResponse.getData().getDrinking();
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d("profilefail", "error");

            }
        });
    }

    /**
     * updating user profile attributes
     */
    private void updateProfile() {
        if (validateData()) {
            Log.d("rishu", mAccessKey);
            HashMap<String, RequestBody> hashMapProfile = new MultipartParams.Builder()
                    .add("relationshipHistory", etRelation.getText().toString())
                    .add("ethnicity", etEthnicity.getText().toString())
                    .add("religion", etReligion.getText().toString())
                    .add("height", etHeight.getText().toString())
                    .add("bodyType", etBodyType.getText().toString())
                    .add("smoking", etSmoking.getText().toString())
                    .add("drinking", etDrinking.getText().toString())
                    .add("step1CompleteOrSkip", true)
                    .build().getMap();

            ApiInterface apiInterface = RestClient.getApiInterface();
            apiInterface.updateProfile(mAccessKey, hashMapProfile).enqueue(new ResponseResolver<CommonResponse>(getActivity(), true, true) {
                @Override
                public void success(final CommonResponse commonResponse) {
                    Activity activity = getActivity();
                    if (activity instanceof ProfileCompletenessActivity) {
                        ProfileCompletenessActivity profileActivity = (ProfileCompletenessActivity) activity;
                        ViewPager viewPager = profileActivity.getViewPager();
                        if (viewPager != null) {
                            viewPager.setCurrentItem(1);
                        }
                    }
                }

                @Override
                public void failure(final APIError error) {

                }
            });
        } else {
            Toast.makeText(getActivity(), "Please Select all", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * @return profile complete validation
     */
    public boolean validateData() {
        if (!ValidateEditText.genericEmpty(etRelation)) {
            if (!ValidateEditText.genericEmpty(etEthnicity)) {
                if (!ValidateEditText.genericEmpty(etReligion)) {
                    if (!ValidateEditText.genericEmpty(etHeight)) {
                        if (!ValidateEditText.genericEmpty(etBodyType)) {
                            if (!ValidateEditText.genericEmpty(etSmoking)) {
                                if (!ValidateEditText.genericEmpty(etSmoking)) {
                                    return true;
                                }
                            }
                            return false;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }


    /**
     * @param indicator view indicator
     * @param isEnable  view color
     */

    public static void enableIndictor(final View indicator, final boolean isEnable) {
        if (isEnable) {
            indicator.setBackgroundResource(R.drawable.pill_profile_completeness_selected);
        } else {
            indicator.setBackgroundResource(R.drawable.pill_profile_completeness_background);
        }
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.et_relation:
                final CharSequence[] csRelation = mRelationList.toArray(new CharSequence[mRelationList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csRelation, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etRelation.setText(mRelationList.get(which));
                        enableIndictor(vRelation, true);
                    }
                }).create().show();
                break;
            case R.id.et_ethnicity:
                final CharSequence[] csEthnicity = mEthnicityList.toArray(new CharSequence[mEthnicityList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csEthnicity, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etEthnicity.setText(mEthnicityList.get(which));
                        enableIndictor(vEthnicity, true);
                    }
                }).create().show();
                break;
            case R.id.et_religion:
                final CharSequence[] csReligion = mReligionList.toArray(new CharSequence[mReligionList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csReligion, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etReligion.setText(mReligionList.get(which));
                        enableIndictor(vReligion, true);
                    }
                }).create().show();
                break;
            case R.id.et_height:
                final CharSequence[] csHeight = mHeightList.toArray(new CharSequence[mHeightList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csHeight, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etHeight.setText(mHeightList.get(which));
                        enableIndictor(vHeight, true);
                    }
                }).create().show();
                break;
            case R.id.et_body_type:
                final CharSequence[] csBodyType = mBodyTypeList.toArray(new CharSequence[mBodyTypeList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csBodyType, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etBodyType.setText(mBodyTypeList.get(which));
                        enableIndictor(vBodyType, true);
                    }
                }).create().show();
                break;
            case R.id.et_smoking:
                final CharSequence[] csSmoking = mSmokingList.toArray(new CharSequence[mSmokingList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csSmoking, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etSmoking.setText(mSmokingList.get(which));
                        enableIndictor(vSmoking, true);
                    }
                }).create().show();
                break;
            case R.id.et_drinking:
                final CharSequence[] csDrinking = mDrinkingList.toArray(new CharSequence[mDrinkingList.size()]);
                new AlertDialog.Builder(getContext()).setItems(csDrinking, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, final int which) {
                        etDrinking.setText(mDrinkingList.get(which));
                        enableIndictor(vDrinking, true);
                    }
                }).create().show();
                break;
            case R.id.bt_next:
                updateProfile();
                break;
            default:
                break;
        }
    }

    /**
     *  Former fragment instance
     * @return instance of Former fragment
     */
    public static ProfileFormerFragment getInstance() {
        return new ProfileFormerFragment();

    }

}
