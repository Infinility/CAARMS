package com.sourtime.www.caarms.services;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.activities.LoginActivity;
import com.sourtime.www.caarms.managers.DatabaseManager;
import com.sourtime.www.caarms.models.Survey;
import com.sourtime.www.caarms.models.User;

import java.util.Calendar;

/**
 * Created by user on 27/09/2016.
 */

public class TimerService extends IntentService {

    private static final String TAG = "TimerService";

    private static final int POLL_INTERVAL =  2 * 60 * 1000; //
    private static final int FOLLOWUP_INTERVAL =  10 * 60 * 1000; //
    private static float SURVEY_INTERVAL = 120 + (float)(Math.random() * ((300 - 120) + 1)); //

    public static final String PREF_IS_ALARM_ON = "isAlarmOn";
    public static final String PREF_LAST_PSYCH_SHOWN_DAY = "psychShownDay";


//    private static final double SURVEY_INTERVAL = 1 / 60; // 1 minute expressed in hours

    public static final String ACTION_SHOW_NOTIFICATION =
            "com.sourtime.www.irtt.SHOW_NOTIFICATION";

    public static final String PERM_PRIVATE = "com.sourtime.www.irtt.PRIVATE";

    public static boolean psychShown;


    private DatabaseManager databaseHelper;
    private SharedPreferences prefs;

    public TimerService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        Log.i(TAG, "TimerService intent received.");

        databaseHelper = DatabaseManager.getInstance(this);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);

        //if system.getcurrentime - prefslastsurveytime >= (1/60) * 60 * 60 * 1000        which is 1 minute
        //notify user to take test

//        SURVEY_INTERVAL = 120 + (float)(Math.random() * ((300 - 120) + 1));

        long currTime = System.currentTimeMillis();

        Calendar rightNow = Calendar.getInstance();
        int hour = rightNow.get(Calendar.HOUR_OF_DAY);
        Log.i(TAG,"Hour: " + hour);

        int today = rightNow.get(Calendar.DAY_OF_MONTH);
        Log.i(TAG,"Day of week in month: " + today);


        if (hour <9 || hour > 23) {
            psychShown = false;
            return;
        }else{
            psychShown = true;
        }


//        String[] msgs = R.array.psyched_strings;

//        if (!psychShown){
//            int random = randomWithRange(1,12);
//            Resources res = getResources();
//            String[] psychEdMsgs = res.getStringArray(R.array.psyched_strings);
//            String msg = psychEdMsgs[random];
//            showPsychEd(msg);
//            psychShown = true;
//        }


        DatabaseManager.SurveyCursor surveyCursor = databaseHelper.querySurveys();
        while(surveyCursor.moveToNext()){
            Survey survey = surveyCursor.getSurvey();

            int surveyId = (int) survey.getmId();
            int timesAskedToday = survey.getTimes_asked_today();
            int lastAskedDay = survey.getLast_asked_day();
            int doneAskingToday = survey.getDone_asking_today();
            int readyTotake = survey.getReady_to_take();


            long time = System.currentTimeMillis();

            Log.d(TAG,"lastAskedDay: " + lastAskedDay);
            Log.d(TAG,"time diff: " + (time - survey.getNext_followup_time()));
            Log.d(TAG,"readyToTake: " + readyTotake);


            if (doneAskingToday > 0 && (lastAskedDay < today)){
                databaseHelper.updateDoneAskingToday(0);
                Log.d(TAG,"reset doneAsking");
            }else if (doneAskingToday > 0 && (lastAskedDay == today)){
                Log.d(TAG,"done asking: next");
                continue;
            }

            if ( (lastAskedDay < today)){

                // these are yesterday's followups so reset them to 0
                Log.d(TAG,"reset followup to 0");
                continue;
            }else if((lastAskedDay == today)  && ((time - survey.getNext_followup_time()) > FOLLOWUP_INTERVAL)){

                //we are following up today
                //do followup
                int type = survey.getSurvey_type();
                prefs.edit().putInt("CurrentFollowup", type).apply();
                notifyUser(survey.getUser_id(), survey.getmId(), "", true);
//                Log.d(TAG, "nextFollowupId: " + nextFollowUpId);
//                Log.d(TAG,"lastAskedDay: " + lastAskedDay);
//                Log.d(TAG,"time diff: " + (time - survey.getNext_followup_time()));
                Log.d(TAG,"Followup Detected");

                continue;
            }
//            if (lastAskedDay == today) continue;

            if (lastAskedDay < today){
                databaseHelper.updateSurveyTimesAsked(0,surveyId);
                databaseHelper.updateLastAskedDay(today, surveyId);
                databaseHelper.updateSurveyLastTaken(System.currentTimeMillis(),surveyId);
                //todo: all surveys set to not ready
                //all followups set to not ready
                databaseHelper.updateSurveyReadyToTake(0,surveyId);
                databaseHelper.updateDoneAskingToday(0,surveyId);
            }


            double timeSinceLast = System.currentTimeMillis() - survey.getLast_attempt_time();
            Log.i(TAG, "time since last survey: " + timeSinceLast);
            double surveyIntervalMilliseconds = SURVEY_INTERVAL * 60 * 1000;
            if (timeSinceLast > surveyIntervalMilliseconds && survey.getmActive() ==1){
                Log.i(TAG, "survey interval: " + surveyIntervalMilliseconds);
                databaseHelper.updateSurveyReadyToTake(1, survey.getmId());
                notifyUser(survey.getUser_id(), survey.getmId(), survey.getmDescription(),false);
            }
        }

    }


    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i  = new Intent(context, TimerService.class);
        PendingIntent pi = PendingIntent.getService(context,0,i,0);

        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC,
                    System.currentTimeMillis(),POLL_INTERVAL,pi);
//            Log.i(TAG, ""+POLL_INTERVAL);
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }

        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON,isOn)
                .commit();

    }

    public static boolean isAlarmOn(Context context){
        Intent i = new Intent(context, TimerService.class);
        PendingIntent pi  = PendingIntent.getService(context,0,i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    private void notifyUser(int userId, long surveyId, String surveyDescription, boolean followup){
        Log.i(TAG, "Notify user called.");

        DatabaseManager.UserCursor userCursor = databaseHelper.queryUser(userId);
        userCursor.moveToFirst();
        User user = userCursor.getUser();

        String username = user.getmUsername();
        long id = user.getmId();
        userCursor.close();
        Log.i(TAG, "Username: " + username);


        Resources r = getResources();
        PendingIntent pi = PendingIntent
                .getActivity(this, 0, new Intent(this, LoginActivity.class), 0);

        Notification notification;
        notification = new NotificationCompat.Builder(this)
                    .setTicker(username + ": " + r.getString(R.string.notification_ticker_take_survey_title))
                    .setSmallIcon(android.R.drawable.ic_menu_agenda)
                    .setContentTitle(username + ": " + r.getString(R.string.notification_content_take_survey_title))
                    .setContentText(surveyDescription + " " + r.getString(R.string.notification_content_take_survey_text))
                    .setContentIntent(pi)
                    .setLights(Color.BLUE,500,500)
                    .setAutoCancel(true)
                    .build();


//        showBackgroundNotification(0,notification);

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

//        int requestCode = userId * 100 + (int)surveyId;
        int requestCode = userId * 100;
        notificationManager.notify(requestCode,notification);
    }

    private void showBackgroundNotification(int requestCode, Notification notification){
        Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
        i.putExtra("REQUEST_CODE",requestCode);
        i.putExtra("NOTIFICATION",notification);

        sendOrderedBroadcast(i,PERM_PRIVATE,null,null,
                Activity.RESULT_OK,null,null);
    }


    private int randomWithRange(int min, int max)
    {
        int range = (max - min) + 1;
        return (int)(Math.random() * range) + min;
    }
}
