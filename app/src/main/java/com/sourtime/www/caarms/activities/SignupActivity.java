package com.sourtime.www.caarms.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.fragments.SignupFragment1;
import com.sourtime.www.caarms.fragments.SignupFragment2;
import com.sourtime.www.caarms.managers.DatabaseManager;
import com.sourtime.www.caarms.managers.SurveyManager;
import com.sourtime.www.caarms.managers.UserManager;
import com.sourtime.www.caarms.models.Question;
import com.sourtime.www.caarms.models.Survey;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by user on 27/09/2016.
 */

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";

    public static final String PREF_FIRST_LOGIN = "prefIsFirstLogin";
    public static final String PREF_IS_SIGNED_UP = "prefIsSignedUp";
    public static final String PREF_HAS_OPENED_APP_BEFORE = "prefHasOpenedApp"; //if first time, then create admin user

    private static final String BASE_URL = "https://irttd.herokuapp.com/";
    private static final String SIGNUP_EXTENSION = "users";

    private Button bSignup;

    private UserManager mUserManager;
    private DatabaseManager mDatabaseManager;
    private SurveyManager surveyManager;

    private ViewPager viewPager;
    private TabLayout tabLayout;

    private SharedPreferences prefs;

    @Bind(R.id.fab)
    FloatingActionButton fab;

    //    public static String userName="Username";
//    public static String pin="999999";
//    public static String email="testing@test.com";
//    public static String friend="";
//    public static String contactName="Contact Name";
//    public static String contactEmail="Contact Email";
//    public static String contactNumber="";
    public static String userName="";
    public static String pin="";
    public static String email="";
    public static String etOne="";
    public static String etTwo="";
    public static String etThree="";
    public static String etFour="";
    public static String etFive="";

    public static ArrayList<ArrayList<String>> surveys;
    public static ArrayList<Survey> surveyConstructors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserManager = UserManager.getInstance(this);
        mDatabaseManager = DatabaseManager.getInstance(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean hasOpenedApp = prefs.getBoolean(PREF_HAS_OPENED_APP_BEFORE, false);
        if (hasOpenedApp == false){
            mUserManager.createAdminUser();
            prefs.edit().putBoolean(PREF_HAS_OPENED_APP_BEFORE, true).apply();
        }

        
        setContentView(R.layout.activity_signup);

//        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (surveyConstructors == null) {
            surveyConstructors = new ArrayList<Survey>();
            for (int i = 0; i<4; i++){
                Survey s = new Survey();
                surveyConstructors.add(s);
            }
        }

        if (surveys == null) {
            surveys = new ArrayList<ArrayList<String>>();
            for (int i = 0; i < 4; i++) {
                ArrayList<String> survey = new ArrayList<String>();
                for (int j = 0; j < 10; j++) {
                    survey.add(j, "Question "+ (j+1));
                }
                surveys.add(i, survey);
            }
        }

        setUpViews();

        ButterKnife.bind(this);

    }

    @OnClick(R.id.fab)
    public void onFabClicked() {


        View focusView = null;
        boolean cancel = false;

        if (!isPasswordValid(pin)){
            cancel = true;
        }else if (!isUsernameValid(userName)){
            cancel = true;
        }

        if (cancel){
            Snackbar.make(findViewById(R.id.coordinatorLayoutSignup),"Check all fields", Snackbar.LENGTH_LONG).show();
            return;
        }

        if (!mUserManager.createUser(userName,email,pin)){
            Snackbar.make(findViewById(R.id.coordinatorLayoutSignup), "Try different Username",Snackbar.LENGTH_LONG).show();
        }else{
            int login = mUserManager.login(email,pin,false,false);
            if (login == 2){
                Log.i(TAG,"Login successful");

                int currentuserId = (int) UserManager.getmCurrentUserId();

//                Snackbar.make(findViewById(R.id.coordinatorLayoutSignup),"Login successful",Snackbar.LENGTH_LONG).show();

                int latestSurveyId = 0;
                DatabaseManager.SurveyCursor surveyCursor = mDatabaseManager.querySurveys();
                while(surveyCursor.moveToNext()){
                    Survey survey = surveyCursor.getSurvey();
                    latestSurveyId = (int)survey.getmId();
                }

                int currentSurveyId = latestSurveyId + 1;

                for (int i = 0; i < surveys.size(); i++){
                    ArrayList<String> currentSurvey = surveys.get(i);
                    Survey surveyInsert = new Survey(currentuserId);
                    mDatabaseManager.insertSurvey(surveyInsert,currentuserId);
                    for (int j = 0; j < currentSurvey.size(); j++){
                        String questionText = currentSurvey.get(j);
                        if (questionText.equals("") || questionText.equals(" ")) continue;
                        Question questionInsert = new Question(questionText,currentSurveyId);
                        mDatabaseManager.insertQuestion(questionInsert,currentSurveyId);
                    }
                    currentSurveyId++;
                }
                Log.i(TAG,"surveys added");


                Log.i(TAG,"inteventions added");

                prefs.edit().putBoolean(PREF_IS_SIGNED_UP,true).apply();
                Intent i = new Intent(this, WelcomeActivity.class);
                startActivity(i);
                this.finish();

            }else{
                Snackbar.make(findViewById(R.id.coordinatorLayoutSignup),"Login error",Snackbar.LENGTH_LONG).show();
            }

        }
    }


    private void setUpViews() {
        if (viewPager.getAdapter() == null) {
            FragmentsAdapter adapter = new FragmentsAdapter(getSupportFragmentManager());
            viewPager.setAdapter(adapter);
        }
        tabLayout.setupWithViewPager(viewPager);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1){
                    fab.setVisibility(View.VISIBLE);
                } else {
                    fab.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private boolean isPasswordValid(String password) {
        return password.length() == 6;
    }

    private boolean isUsernameValid(String username) {
        return username.length() >= 4 && username.length() <=12;
    }


    class FragmentsAdapter extends FragmentPagerAdapter {

        private String[] titles = getResources().getStringArray(R.array.signup_tabs);

        public FragmentsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new SignupFragment1();
                case 1:
                    return new SignupFragment2();

            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public int getCount() {
            return titles.length;
        }


    }


}
