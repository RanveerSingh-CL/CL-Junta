package com.skeleton.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.skeleton.R;
import com.skeleton.database.CommonData;

/**
 * MainActivity
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    private DrawerLayout dlDrawer;
    private ImageView ivSideMenu;
    private TextView tvLogout;

    @Override

    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.iv_side_menu:
                dlDrawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.tv_logout:
                CommonData.clearData();
                startActivity(new Intent(MainActivity.this, LoginSignupActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * inflating menu items in android tool bar
     *
     * @param menu : menu items
     * @return : retutns menu items
     */
    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_items, menu);

        //setting image to first menu item
        MenuItem itemOne = menu.findItem(R.id.item_filter);
        SpannableStringBuilder builderOne = new SpannableStringBuilder("*   Filter");
        // replace "*" with icon
        builderOne.setSpan(new ImageSpan(this, R.drawable.icon_f_i_l_t_e_r), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemOne.setTitle(builderOne);
        //setting image to second menu item

        MenuItem itemTwo = menu.findItem(R.id.item_block);
        SpannableStringBuilder builderTwo = new SpannableStringBuilder("*   Block");
        // replace "*" with icon
        builderTwo.setSpan(new ImageSpan(this, R.drawable.icon_b_l_o_c_k), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemTwo.setTitle(builderTwo);

        //setting image to
        // menu item
        MenuItem itemThree = menu.findItem(R.id.item_report);
        SpannableStringBuilder builderThree = new SpannableStringBuilder("*   Block");
        // replace "*" with icon
        builderThree.setSpan(new ImageSpan(this, R.drawable.icon_r_e_p_o_r_t), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        itemThree.setTitle(builderThree);

        return true;
    }

    /**
     * initialization
     */
    private void init() {
        Toolbar topToolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(topToolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        dlDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ivSideMenu = (ImageView) findViewById(R.id.iv_side_menu);
        tvLogout = (TextView) findViewById(R.id.tv_logout);
        ivSideMenu.setVisibility(View.VISIBLE);
        tvLogout.setOnClickListener(this);
        ivSideMenu.setOnClickListener(this);
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
