package com.skeleton.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.skeleton.R;
import com.skeleton.activity.MainActivity;
import com.skeleton.adapter.ProfileRecyclerAdapter;
import com.skeleton.database.CommonData;
import com.skeleton.model.category.Category;
import com.skeleton.model.category.CategoryResponse;
import com.skeleton.retrofit.APIError;
import com.skeleton.retrofit.ApiInterface;
import com.skeleton.retrofit.CommonParams;
import com.skeleton.retrofit.ResponseResolver;
import com.skeleton.retrofit.RestClient;

import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * ProfileLaterFragment
 */
public class ProfileLaterFragment extends BaseFragment implements View.OnClickListener {
    private RecyclerView recyclerView;
    private String mAccessToken;
    private View vIndicator1, vIndicator2, vIndicator3, vIndicator4, vIndicator5;
    private Button btSave;
    private List<Category> mSelectedCat;
    private final int maxInterestSelected = 5;


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_later, null);
        init(v);
        return v;
    }

    /**
     * @param v view
     */
    private void init(final View v) {

        recyclerView = (RecyclerView) v.findViewById(R.id.profile_completeness_rv);
        mAccessToken = "bearer " + CommonData.getUserData().getAccessToken();
        vIndicator1 = v.findViewById(R.id.view_1);
        vIndicator2 = v.findViewById(R.id.view_2);
        vIndicator3 = v.findViewById(R.id.view_3);
        vIndicator4 = v.findViewById(R.id.view_4);
        vIndicator5 = v.findViewById(R.id.view_5);
        btSave = (Button) v.findViewById(R.id.bt_save);
        btSave.setOnClickListener(this);
        getCatagoryList();
    }

    /**
     * Validate data
     *
     * @return data is validated or not
     */
    private boolean validateData() {
        ProfileRecyclerAdapter pAdapter = (ProfileRecyclerAdapter) recyclerView.getAdapter();
        mSelectedCat = pAdapter.getSelectedCategories();
        return !(mSelectedCat == null || mSelectedCat.size() != maxInterestSelected);
    }

    /**
     * getCatagoryList
     */
    private void getCatagoryList() {
        ApiInterface apiInterface = RestClient.getApiInterface();
        apiInterface.getCategory(mAccessToken, "INTEREST").enqueue(new ResponseResolver<CategoryResponse>(getActivity(), true, true) {
            @Override
            public void success(final CategoryResponse categoryResponse) {
                Log.d(TAG, "success: " + categoryResponse.getStatusCode());
                if ("200".equals(categoryResponse.getStatusCode().toString())) {
                    List<Category> listCategories = categoryResponse.getData().getCategories();
                    Log.d(TAG, "list: " + listCategories.toString());
                    ProfileRecyclerAdapter adapter = new ProfileRecyclerAdapter(ProfileLaterFragment.this,
                            listCategories);
                    recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d(TAG, "failure: Status " + error.getStatusCode());
                Log.d(TAG, "failure: Message" + error.getMessage());
            }
        });
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.bt_save:
                if (validateData()) {
                    updateProfile();
                } else {
                    Toast.makeText(getActivity(), "select 5", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    /**
     * glows up the indicaton on item select
     *
     * @param count refers enabled indicators
     */
    public void glowIndicator(final int count) {
        switch (count) {
            case 0:
                ProfileFormerFragment.enableIndictor(vIndicator1, false);
                ProfileFormerFragment.enableIndictor(vIndicator2, false);
                ProfileFormerFragment.enableIndictor(vIndicator3, false);
                ProfileFormerFragment.enableIndictor(vIndicator4, false);
                ProfileFormerFragment.enableIndictor(vIndicator5, false);
                break;
            case 1:
                ProfileFormerFragment.enableIndictor(vIndicator1, true);
                ProfileFormerFragment.enableIndictor(vIndicator2, false);
                ProfileFormerFragment.enableIndictor(vIndicator3, false);
                ProfileFormerFragment.enableIndictor(vIndicator4, false);
                ProfileFormerFragment.enableIndictor(vIndicator5, false);
                break;

            case 2:
                ProfileFormerFragment.enableIndictor(vIndicator1, true);
                ProfileFormerFragment.enableIndictor(vIndicator2, true);
                ProfileFormerFragment.enableIndictor(vIndicator3, false);
                ProfileFormerFragment.enableIndictor(vIndicator4, false);
                ProfileFormerFragment.enableIndictor(vIndicator5, false);
                break;
            case 3:
                ProfileFormerFragment.enableIndictor(vIndicator1, true);
                ProfileFormerFragment.enableIndictor(vIndicator2, true);
                ProfileFormerFragment.enableIndictor(vIndicator3, true);
                ProfileFormerFragment.enableIndictor(vIndicator4, false);
                ProfileFormerFragment.enableIndictor(vIndicator5, false);
                break;
            case 4:
                ProfileFormerFragment.enableIndictor(vIndicator1, true);
                ProfileFormerFragment.enableIndictor(vIndicator2, true);
                ProfileFormerFragment.enableIndictor(vIndicator3, true);
                ProfileFormerFragment.enableIndictor(vIndicator4, true);
                ProfileFormerFragment.enableIndictor(vIndicator5, false);
                break;
            case 5:
                ProfileFormerFragment.enableIndictor(vIndicator1, true);
                ProfileFormerFragment.enableIndictor(vIndicator2, true);
                ProfileFormerFragment.enableIndictor(vIndicator3, true);
                ProfileFormerFragment.enableIndictor(vIndicator4, true);
                ProfileFormerFragment.enableIndictor(vIndicator5, true);
                break;
            default:

        }
    }

    /**
     * updating profile Credentials
     */
    private void updateProfile() {
        String[] arrayIds = new String[maxInterestSelected];
        Gson gson = new GsonBuilder().create();
        ApiInterface updateProfile = RestClient.getApiInterface();

        for (int i = 0; i < mSelectedCat.size(); i++) {
            arrayIds[i] = mSelectedCat.get(i).getId();

        }
        String jsonArray = gson.toJson(arrayIds);
        HashMap<String, String> hashMap = new CommonParams.Builder()
                .add("interestCategories", jsonArray)
                .add("step2CompleteOrSkip", true)
                .build().getMap();
        updateProfile.selectCategories(mAccessToken, hashMap).enqueue(new ResponseResolver<CategoryResponse>(getActivity(), true, true) {
            @Override
            public void success(final CategoryResponse categoryResponse) {
                if ("200".equals(String.valueOf(categoryResponse.getStatusCode()))) {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    getActivity().finish();
                }
            }

            @Override
            public void failure(final APIError error) {
                Log.d("rishurishu", "Fail");
            }
        });
    }

    /**
     * Later fragment instance
     *
     * @return instance of Later fragment
     */
    public static ProfileLaterFragment getInstance() {
        return new ProfileLaterFragment();

    }
}
