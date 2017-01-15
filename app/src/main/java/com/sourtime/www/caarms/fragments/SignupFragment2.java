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

public class SignupFragment2 extends Fragment {

    private static final String TAG = "SignupFragment2";

    private View rootView;

    @Bind(R.id.etOne)
    EditText etOne;
    @Bind(R.id.etTwo)
    EditText etTwo;
    @Bind(R.id.etThree)
    EditText etThree;
    @Bind(R.id.etFour)
    EditText etFour;
    @Bind(R.id.etFive)
    EditText etFive;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_signup2, container, false);

        ButterKnife.bind(this,rootView);

        if (SignupActivity.etOne.length() > 2) etOne.setText(SignupActivity.etOne);
        etOne.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SignupActivity.etOne = editable.toString();
            }
        });

        if (SignupActivity.etTwo.length() > 2) etTwo.setText(SignupActivity.etTwo);
        etTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SignupActivity.etTwo = editable.toString();
            }
        });

        if (SignupActivity.etThree.length() > 2) etThree.setText(SignupActivity.etThree);
        etThree.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SignupActivity.etThree = editable.toString();
            }
        });

        if (SignupActivity.etFour.length() > 2) etFour.setText(SignupActivity.etFour);
        etFour.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                SignupActivity.etFour = editable.toString();
            }
        });


        return rootView;

    }

}
