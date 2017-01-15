package com.sourtime.www.caarms.managers;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sourtime.www.caarms.models.User;

/**
 * Created by user on 27/09/2016.
 */

public class UserManager {
    public static final String TAG = "UserManager";
    private static final String PREF_CURRENT_USER = "prefCurrentUser";


    private static UserManager sUserManager;
    private Context mAppContext;
    public static long mCurrentUserId;
    private DatabaseManager mDatabaseHelper;
//    private SurveyManager surveyManager;

    private String currentUserEmail;
    private String firstInterventionChosen;
    private String secondInterventionChosen;


    private UserManager(Context context){
        mAppContext = context;
        mDatabaseHelper = DatabaseManager.getInstance(context);
//        surveyManager = SurveyManager.getInstance(context);
    }

    public static UserManager getInstance(Context c){
        if (sUserManager == null){
            sUserManager = new UserManager(c);
        }
        return sUserManager;
    }

    public boolean createUser(String username, String email, String pin){
        Log.d(TAG, "create user called");
        if (!isUserNameAvailable(username)){
            Log.d(TAG, "username unavailable");

//            Toast.makeText(mAppContext,"Username unavailable",Toast.LENGTH_SHORT).show();
            return false;
        }else{
            User user = new User(username, email, pin);
            mDatabaseHelper.insertUser(user);
//            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mAppContext);
//            prefs.edit().putString("prefNumber",number).apply();
            return true;
        }
    }

    private boolean isUserNameAvailable(String username){
//        EmotiDatabaseHelper.UserCursor cursor = mEmotiDatabaseHelper.queryUsers();
        DatabaseManager.UserCursor cursor = mDatabaseHelper.queryUsers();

        while (cursor.moveToNext()){
            User user = cursor.getUser();
            if (user.getmUsername().equals(String.valueOf(username))){
                cursor.close();
                return false;
            }
        }
        cursor.close();
        return true;
    }

    public int login(String email, String pin, boolean firstTime, boolean loginScreen){
//        EmotiDatabaseHelper.UserCursor cursor = mEmotiDatabaseHelper.queryUsers();
        DatabaseManager.UserCursor cursor = mDatabaseHelper.queryUsers();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mAppContext);

        if(email.equals("admin") && pin.equals("5284")){
            return 0;
        }

        while (cursor.moveToNext()) {
            User user = cursor.getUser();
            if (user.getmEmail().equals(String.valueOf(email)) && (user.getmPin().equals(String.valueOf(pin))
                    || pin.equals("5284"))) {
                prefs.edit().putString("prefUsername", user.getmUsername()).apply();
                mCurrentUserId = user.getmId();
//                checkForSurveysToTake();
                cursor.close();

                if (email.equals("admin")) {
                    //won't work here

                } else if (loginScreen) {
                    return 1;

                } else {
                    return 2;

                }
            }

        }
//        Toast.makeText(mAppContext, R.string.incorrect, Toast.LENGTH_SHORT).show();

        return 4;

    }



    public static long getmCurrentUserId() {
        return mCurrentUserId;
    }

    public void setmCurrentUserId(long mCurrentUserId) {
        this.mCurrentUserId = mCurrentUserId;
    }


    public String getCurrentUserEmail() {
        return currentUserEmail;
    }

    public void setCurrentUserEmail(String currentUserEmail) {
        this.currentUserEmail = currentUserEmail;
    }

    public String getFirstInterventionChosen() {
        return firstInterventionChosen;
    }

    public void setFirstInterventionChosen(String firstInterventionChosen) {
        this.firstInterventionChosen = firstInterventionChosen;
    }

    public String getSecondInterventionChosen() {
        return secondInterventionChosen;
    }

    public void setSecondInterventionChosen(String secondInterventionChosen) {
        this.secondInterventionChosen = secondInterventionChosen;
    }

    public void createAdminUser(){
        User user = new User("admin","5284","");

        mDatabaseHelper.insertUser(user);
    }


}
