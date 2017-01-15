package com.sourtime.www.caarms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.FrameLayout;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.managers.DatabaseManager;
import com.sourtime.www.caarms.managers.SurveyManager;
import com.sourtime.www.caarms.managers.UserManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 11/10/2016.
 */

public class WelcomeActivity extends AppCompatActivity {

    private Button bTakeSurvey;

    private UserManager userManager;
    private SurveyManager surveyManager;
    private DatabaseManager databaseHelper;

    private final String TAG = "WelcomeActivity";

    private FrameLayout frameLayout;

    @Bind(R.id.btnTakeSurvey)
    Button btnTakeSurvey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);

        userManager = UserManager.getInstance(this);
//        surveyManager = SurveyManager.getInstance(this);
        databaseHelper = DatabaseManager.getInstance(this);

        ButterKnife.bind(this);


    }



    @OnClick(R.id.btnTakeSurvey)
    public void onBtnTakeSurveyClicked() {
        Intent i = new Intent(this, SurveyActivity.class);
        startActivity(i);
        this.finish();

    }

}
