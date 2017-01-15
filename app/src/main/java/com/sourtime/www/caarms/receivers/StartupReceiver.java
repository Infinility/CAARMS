package com.sourtime.www.caarms.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.sourtime.www.caarms.activities.SignupActivity;
import com.sourtime.www.caarms.services.TimerService;

/**
 * Created by user on 11/10/2016.
 */

public class StartupReceiver extends BroadcastReceiver {

    private static final String TAG = "StartupReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive called.");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

        if((prefs.getBoolean(SignupActivity.PREF_IS_SIGNED_UP,false)) && (TimerService.isAlarmOn(context) == false)){
            Log.d(TAG, "Alarm set");
            TimerService.setServiceAlarm(context,true);
        }
    }
}
