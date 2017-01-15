package com.sourtime.www.caarms.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.managers.UserManager;
import com.sourtime.www.caarms.services.TimerService;
import com.squareup.okhttp.OkHttpClient;

/**
 * Created by user on 26/09/2016.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    public static final String PREF_FIRST_LOGIN = "prefIsFirstLogin";

    public static final String PREFERENCE_REMEMBER_USERNAME = "prefRememberUsername";
    public static final String PREFERENCE_REMEMBERED_USERNAME = "prefRememberedUsername";

    public static Resources resources;


    private EditText etUsername;
    private EditText etPin;
    private CheckBox cbRemember;
    private Button bLogin;

    private UserManager userManager;
    private OkHttpClient okClient;

    private CoordinatorLayout coordinatorLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userManager = UserManager.getInstance(this);

        final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        final boolean rememberUsername = prefs.getBoolean(PREFERENCE_REMEMBER_USERNAME,false);

        setContentView(R.layout.activity_login);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);

        resources = this.getResources();

        if((prefs.getBoolean(SignupActivity.PREF_IS_SIGNED_UP,false)) && (TimerService.isAlarmOn(this) == false)){
            TimerService.setServiceAlarm(this,true);
        }


        etUsername = (EditText) findViewById(R.id.etUsername_login);
        etPin = (EditText) findViewById(R.id.etPin_login);
        bLogin = (Button) findViewById(R.id.bLogin);
        cbRemember = (CheckBox) findViewById(R.id.checkboxRemember);

        cbRemember.setChecked(rememberUsername);

        if (rememberUsername){
            String username = prefs.getString(PREFERENCE_REMEMBERED_USERNAME,"");
            etUsername.setText(username);
            etPin.setSelection(0);
        }

        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String e = etUsername.getText().toString();
                String p = etPin.getText().toString();

                if (cbRemember.isChecked()){
                    prefs.edit().putString(PREFERENCE_REMEMBERED_USERNAME,e).apply();
                    prefs.edit().putBoolean(PREFERENCE_REMEMBER_USERNAME, true).apply();
                }else{
                    prefs.edit().putBoolean(PREFERENCE_REMEMBER_USERNAME, false).apply();
                }

                etUsername.setText("");
                etPin.setText("");

                login(e,p);

            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    public void login(String e, String p){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miSignup:
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);

        }
    }
}
