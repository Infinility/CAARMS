package com.sourtime.www.caarms.managers;

import android.content.Context;

/**
 * Created by user on 27/09/2016.
 */

public class SurveyManager {

    private static final String TAG = "SurveyManager";


    private DatabaseManager mDatabaseManager;
    private UserManager mUserManager;
    private Context mContext;
    //    private int currentUserId;
    private int currentSurvey;
    private int currentQuestion;
    private int currentAttemptId;
    private boolean surveysAvailable;
    private int survey_type;

    private int real_survey_id;
    private int currentScore;

    private static SurveyManager mInstance;

    public SurveyManager(Context c){
        mContext = c;
        mDatabaseManager = DatabaseManager.getInstance(c);
        mUserManager = UserManager.getInstance(c);
//        currentUserId = 1;
        currentSurvey = 0;
        currentQuestion = 0;
        surveysAvailable = false;

    }

    public static SurveyManager getInstance(Context c){
        if (mInstance == null){
            mInstance = new SurveyManager(c);
        }
        return mInstance;
    }


}
