package com.sourtime.www.caarms.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.fragments.SurveyFragment;

import butterknife.ButterKnife;

import static com.sourtime.www.caarms.R.id.frameLayout;

/**
 * Created by user on 11/10/2016.
 */

public class SurveyActivity extends AppCompatActivity {

    private FrameLayout frameLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_survey);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction transaction = fm.beginTransaction();
        SurveyFragment fragment = new SurveyFragment();
        transaction.replace(R.id.frameLayout, fragment);
        transaction.commit();

        ButterKnife.bind(this);

    }


}
