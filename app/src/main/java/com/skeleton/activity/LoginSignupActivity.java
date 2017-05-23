package com.skeleton.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.skeleton.R;
import com.skeleton.fragment.LoginFragment;
import com.skeleton.fragment.SignupFragment;

/**
 * LoginSignupActivity
 */
public class LoginSignupActivity extends BaseActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        init();
    }

    /**
     * initialization
     */
    private void init() {

        viewPager = (ViewPager) findViewById(R.id.sign_up_viewpager);
        tabLayout = (TabLayout) findViewById(R.id.sign_up_tabs);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(final int position) {
                switch (position) {
                    case 0:
                        return SignupFragment.getInstance();
                    case 1:
                        return LoginFragment.getInstance();
                    default:
                        return null;

                }
            }

            @Override
            public CharSequence getPageTitle(final int position) {
                switch (position) {
                    case 0:
                        return getString(R.string.text_signup);
                    case 1:
                        return getString(R.string.text_login);
                    default:
                        return null;
                }
            }

            @Override
            public int getCount() {
                return 2;
            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }
}
