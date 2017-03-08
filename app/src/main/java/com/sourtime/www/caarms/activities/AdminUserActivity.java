package com.sourtime.www.caarms.activities;

/**
 * Created by user on 03/02/2017.
 */
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
import com.sourtime.www.caarms.fragments.AdminUserAttemptsFragment;
import com.sourtime.www.caarms.fragments.AdminUserListFragment;

public class AdminUserActivity extends AppCompatActivity {

    private static final String TAG = "AdminUserActivity";

    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(TAG, "oncreate called");
        setContentView(R.layout.activity_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        AdminUserAttemptsFragment fragment = new AdminUserAttemptsFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onresume called");

//        if (viewPager.getAdapter() == null) {
//            FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
//            viewPager.setAdapter(adapter);
//            Log.i(TAG, "viewpager adapter set");
//
//        }
//        tabLayout.setupWithViewPager(viewPager);
    }


}