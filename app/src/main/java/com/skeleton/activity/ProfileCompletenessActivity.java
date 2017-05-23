package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.adapter.CustomViewPager;
import com.skeleton.fragment.ProfileFormerFragment;
import com.skeleton.fragment.ProfileLaterFragment;

/**
 * ProfileCompletenessActivity
 */
public class ProfileCompletenessActivity extends BaseActivity implements View.OnClickListener {
    private ImageView ivSideMenu, ivBackButton;
    private TextView tvAppBarTitle;
    private Button btSkip;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_completeness);
        init();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.bt_back:
                if (viewPager.getCurrentItem() == 0) {
                    startActivity(new Intent(ProfileCompletenessActivity.this, LoginSignupActivity.class));
                } else if (viewPager.getCurrentItem() == 1) {
                    viewPager.setCurrentItem(0);
                }
                break;
            case R.id.bt_skip:
                if (viewPager.getCurrentItem() == 0) {
                    viewPager.setCurrentItem(1);
                } else if (viewPager.getCurrentItem() == 1) {
                    startActivity(new Intent(ProfileCompletenessActivity.this, MainActivity.class));
                }
                break;
            default:
                break;

        }
    }

    /**
     * initialization
     */
    private void init() {
        ivSideMenu = (ImageView) findViewById(R.id.iv_side_menu);
        tvAppBarTitle = (TextView) findViewById(R.id.tv_appbar);
        ivBackButton = (ImageView) findViewById(R.id.bt_back);
        btSkip = (Button) findViewById(R.id.bt_skip);
        ivSideMenu.setVisibility(View.GONE);
        ivBackButton.setVisibility(View.VISIBLE);
        tvAppBarTitle.setVisibility(View.VISIBLE);
        btSkip.setVisibility(View.VISIBLE);
        tvAppBarTitle.setText(R.string.title_profile_completeness);
        btSkip.setOnClickListener(this);
        ivBackButton.setOnClickListener(this);


        viewPager = (CustomViewPager) findViewById(R.id.profile_viewpager);
        viewPager.setPagingEnabled(false);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                switch (position) {
                    case 0:
                        return ProfileFormerFragment.getInstance();
                    case 1:
                        return ProfileLaterFragment.getInstance();
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
    }

    /**
     * @return view pager
     */
    public ViewPager getViewPager() {
        return viewPager;
    }
}
