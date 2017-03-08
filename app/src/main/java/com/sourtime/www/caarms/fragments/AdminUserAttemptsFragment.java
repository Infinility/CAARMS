package com.sourtime.www.caarms.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.activities.AdminUserActivity;
import com.sourtime.www.caarms.managers.DatabaseManager;
import com.sourtime.www.caarms.managers.SurveyManager;
import com.sourtime.www.caarms.managers.UserManager;
import com.sourtime.www.caarms.models.Attempt;
import com.sourtime.www.caarms.models.User;

import com.sourtime.www.caarms.R;
import com.sourtime.www.caarms.activities.LoginActivity;
import com.sourtime.www.caarms.activities.ResultsActivity;
import com.sourtime.www.caarms.activities.SignupActivity;
import com.sourtime.www.caarms.models.Attempt;
import com.sourtime.www.caarms.models.Attempt;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by user on 03/02/2017.
 */

public class AdminUserAttemptsFragment extends Fragment {
    private static final String TAG = "AdminAttemptsFragment";

    private RecyclerView recyclerView;
    private DatabaseManager databaseHelper;
    private SurveyManager surveyManager;
    private List<Attempt> attemptsList;
    private AttemptsListAdapter adapter;
    private UserManager userManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        StaggeredGridLayoutManager layoutManager;

        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        databaseHelper = DatabaseManager.getInstance(getActivity());
        surveyManager = SurveyManager.getInstance(getActivity());
        userManager = UserManager.getInstance(getActivity());

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (recyclerView.getAdapter() == null) {
            Log.d(TAG, "adapter is null");
            adapter = new AttemptsListAdapter(getAttemptList());
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private List<Attempt> getAttemptList(){
//        int currentUser = (int)surveyManager.getCurrentUserId();
        int currentUser = (int)userManager.getmCurrentUserId();
        Log.i(TAG, "Current user: " + currentUser);

        DatabaseManager.AttemptCursor cursor = databaseHelper.queryUserAttempts(currentUser);

//        attemptsList.clear();
        attemptsList = new ArrayList<Attempt>();
        while (cursor.moveToNext()){
            Attempt attempt = cursor.getAttempt();
            attemptsList.add(attempt);
//            Log.i(TAG, "Attempt user: " + survey.getUser_id());
            Log.i(TAG, "Attempt id: " + attempt.getmId());

        }
        cursor.close();

        Collections.reverse(attemptsList);


        return attemptsList;
    }

    class AttemptsListAdapter extends RecyclerView.Adapter<AttemptsListAdapter.CustomViewHolder> {

        private List<Attempt> attempts;
        private int userID;
        String attemptNum = "Attempt ";

        public AttemptsListAdapter(List<Attempt> attempts){
            super();
            this.attempts = attempts;
            this.userID = (int) UserManager.getmCurrentUserId();
        }

        @Override
        public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = getActivity().getLayoutInflater().inflate(R.layout.item_admin_attempt, parent, false);
            return new CustomViewHolder(v);
        }

        @Override
        public void onBindViewHolder(CustomViewHolder holder, int position) {
            Attempt attempt = attempts.get(position);
//            Log.d(TAG,"Position: " + position);
//            Log.d(TAG,"Binder Attempt Description: " + survey.getmDescription());
            int pos = attempts.size() - position;
            String att = getResources().getString(R.string.admin_attempt_number) + " " + pos;
            holder.attemptSurvey.setText(att);
//            holder.attemptScore.setText("Score: " + String.valueOf(attempt.getScore()));

            long millisecondTime = attempt.getAttemptTime();
            String date = getDate(millisecondTime, "dd/MM/yyyy hh:mm");

            holder.attemptTime.setText(date);


        }

        @Override
        public int getItemCount() {
            return attempts.size();
        }



        public String getDate(long milliSeconds, String dateFormat)
        {
            // Create a DateFormatter object for displaying date in specified format.
//            SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
            DateFormat formatter = DateFormat.getDateTimeInstance();
            formatter.setTimeZone(TimeZone.getTimeZone("GMT"));

            // Create a calendar object that will convert the date and time value in milliseconds to date.
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(milliSeconds);
            return formatter.format(calendar.getTime());
        }

        class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            TextView attemptSurvey;
            //            TextView attemptScore;
            TextView attemptTime;


            public CustomViewHolder(View itemView) {
                super(itemView);
                attemptSurvey = (TextView) itemView.findViewById(R.id.attempt_survey);
//                attemptScore = (TextView) itemView.findViewById(R.id.attempt_score);
                attemptTime = (TextView) itemView.findViewById(R.id.attempt_time);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Log.i(TAG, "clicked");
//                final int attemptId = getAdapterPosition() + 1;
//                DatabaseHelper.AnswerTextCursor answerTextCursor = databaseHelper.queryAnswerTextsForAttempt(attemptId);
                Attempt selectedAttempt = attemptsList.get(getAdapterPosition());
                int attemptId = (int) selectedAttempt.getmId();


//                DatabaseHelper.AnswerTextCursor answerTextCursor = databaseHelper.queryAnswerTextsForAttempt(attemptId);
//                while (answerTextCursor.moveToNext()){
//                    AnswerText at = answerTextCursor.getAnswerText();
//                    Log.i(TAG, "qt: " + at.getQuestionId() + " anstext: " + at.getValue() + " attemptId: " + at.getAttemptId());
//                }
//                answerTextCursor.close();
//
//                DatabaseHelper.AnswerNumericalCursor answerNumericalCursor = databaseHelper.queryAnswerNumericalsForAttempt(attemptId);
//                while (answerNumericalCursor.moveToNext()){
//                    AnswerNumerical an = answerNumericalCursor.getAnswerNumerical();
//                    Log.i(TAG,"qn: " + an.getQuestionId() + " ansNum: " + an.getValue() + " attemptId: " + an.getAttemptId());
//                }
//                answerNumericalCursor.close();

                // Create new fragment and transaction
//                Fragment rf = ResultsFragment.newInstance(attemptId);
//                FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//                transaction.replace(R.id.fragmentContainer, rf);
//                transaction.addToBackStack(null);
//
//                // Commit the transaction
//                transaction.commit();

//                ResultsFragment rf = ResultsFragment.newInstance(attemptId);
                Intent i = new Intent(getActivity(), ResultsActivity.class);
                String attId  = "attemptId";
                i.putExtra(attId,attemptId);
                startActivity(i);
            }
        }
    }
}