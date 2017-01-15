package com.sourtime.www.caarms.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.activities.WelcomeActivity;
import com.sourtime.www.caarms.managers.DatabaseManager;
import com.sourtime.www.caarms.managers.SurveyManager;
import com.sourtime.www.caarms.managers.UserManager;
import com.sourtime.www.caarms.models.Question;
import com.sourtime.www.caarms.services.TimerService;
import com.squareup.okhttp.OkHttpClient;

import java.util.Calendar;
import java.util.List;

/**
 * Created by user on 12/10/2016.
 */

public class SurveyFragment extends Fragment {

    private static final String TAG = "SurveyFragment";

    private TextView tvQuestion, tvSeekbarValue;
    private EditText etOne, etTwo, etThree, etFour, etFive, etSix, etSeven, etEight, etNine, etTen, etEleven;
    private SurveyManager surveyManager;
    private DatabaseManager databaseManager;
    private UserManager userManager;
    private List<Question> questionList;
    private int currentQuestion;
    private int currentScore;
    private int surveyLength;
    private OkHttpClient okClient;

    private static final String BASE_URL = "https://irttd.herokuapp.com/";
    private static final String ATTEMPT_EXTENSION = "attempts";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_survey, container, false);

//            surveyManager = SurveyManager.getInstance(getActivity());
//        surveyManager.addUser();
//        surveyManager.addSurveys();
//        surveyManager.addSurveysForCurrentUser();
        databaseManager = DatabaseManager.getInstance(getActivity());
        userManager = UserManager.getInstance(getActivity());


        return v;
    }





}