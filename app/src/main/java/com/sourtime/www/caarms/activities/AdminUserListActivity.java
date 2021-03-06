package com.sourtime.www.caarms.activities;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.fragments.AdminUserListFragment;

/**
 * Created by user on 03/02/2017.
 */

public class AdminUserListActivity extends AppCompatActivity {

    private static final String TAG = "AdminUserActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "oncreate called");
        setContentView(R.layout.activity_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        AdminUserListFragment fragment = new AdminUserListFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onresume called");

    }


}