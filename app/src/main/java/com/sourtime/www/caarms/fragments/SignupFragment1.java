package com.sourtime.www.caarms.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.activities.SignupActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 11/10/2016.
 */

public class SignupFragment1 extends Fragment {

    private static final String TAG = "SignupFragment1";

    //    private String mUsername, mEmail, mPin, mFriend;
    private View rootView;

    @Bind(R.id.etUsername)
    EditText etUsername;
    @Bind(R.id.etEmail)
    EditText etEmail;
    @Bind(R.id.etPin)
    EditText etPin;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_signup1, container, false);

        ButterKnife.bind(this,rootView);

        if (SignupActivity.userName.length() > 2) etUsername.setText(SignupActivity.userName);
        etUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SignupActivity.userName = s.toString();
                Log.i(TAG, "username: " + SignupActivity.userName);
            }
        });

        if (SignupActivity.email.length() > 2) etEmail.setText(SignupActivity.email);
        etEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SignupActivity.email = s.toString();
                Log.i(TAG,"username: " + SignupActivity.email);
            }
        });

        if (SignupActivity.pin.length() > 2) etPin.setText(SignupActivity.pin);
        etPin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                SignupActivity.pin = s.toString();
                Log.i(TAG,"username: " + SignupActivity.pin);
            }
        });


        return rootView;

    }
}
