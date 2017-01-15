package com.sourtime.www.caarms.managers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sourtime.www.caarms.models.Answer;
import com.sourtime.www.caarms.models.Attempt;
import com.sourtime.www.caarms.models.Question;
import com.sourtime.www.caarms.models.Survey;
import com.sourtime.www.caarms.models.User;

/**
 * Created by user on 27/09/2016.
 */

public class DatabaseManager extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseManager";

    private static final String DB_NAME = "caarms.sqlite";
    private static final int VERSION = 1;

    private static final String CREATE_TABLE = "create table ";
    private static final String TYPE_TEXT = " TEXT";
    private static final String TYPE_VARCHAR = " varchar(100)";
    private static final String TYPE_INTEGER = " INTEGER";
    private static final String TYPE_REAL = " REAL";
    private static final String PRIMARY_KEY_AUTO = " primary key autoincrement";
    private static final String COMMA_SEP = ",";
    private static final String REF = " references ";


    private static final String TABLE_USER = "users";
    private static final String COLUMN_USER_PRIMARY_KEY = "_id";
    private static final String COLUMN_USER_NAME = "name";
    private static final String COLUMN_USER_PIN = "pin";
    private static final String COLUMN_USER_EMAIL = "email";

    private static final String COLUMN_USER_ID = "user_id";

    private static final String TABLE_SURVEY = "surveys";
    private static final String COLUMN_SURVEY_ID = "survey_id";
    private static final String COLUMN_SURVEY_DESCRIPTION = "description";
    private static final String COLUMN_SURVEY_ATTEMPTS = "attempts";
    private static final String COLUMN_SURVEY_AVERAGE = "average";
    private static final String COLUMN_SURVEY_LAST = "last";
    private static final String COLUMN_SURVEY_ACTIVE = "active";
    private static final String COLUMN_SURVEY_LAST_ATTEMPT_TIME = "last_attempt";
    private static final String COLUMN_SURVEY_NEXT_ATTEMPT_TIME = "next_attempt";
    private static final String COLUMN_SURVEY_TIMES_ASKED_TODAY = "asked_today";
    private static final String COLUMN_SURVEY_LAST_ASKED_DAY = "last_asked_on";
    private static final String COLUMN_SURVEY_READY_TO_TAKE = "ready";
    private static final String COLUMN_SURVEY_DONE_ASKING = "done_asking_today";
    private static final String COLUMN_SURVEY_TYPE = "survey_type";

    private static final String TABLE_QUESTION = "questions";
    private static final String COLUMN_QUESTION_ID = "question_id";
    private static final String COLUMN_QUESTION_TEXT = "question_text";
    private static final String COLUMN_QUESTION_TYPE = "question_type";

    private static final String TABLE_ATTEMPT = "attempts";
    private static final String COLUMN_ATTEMPT_ID = "attempt_id";
    private static final String COLUMN_ATTEMPT_DATETIME = "datetime";
    private static final String COLUMN_ATTEMPT_SCORE = "score";

    private static final String TABLE_ANSWER = "answers";
    private static final String COLUMN_ANSWER_TYPE = "answer_type";
    private static final String COLUMN_ANSWER_VALUE = "answer_value";

    private static final String CREATE_TABLE_USER =
            CREATE_TABLE + TABLE_USER + "(" +
                    COLUMN_USER_PRIMARY_KEY + TYPE_INTEGER + PRIMARY_KEY_AUTO + COMMA_SEP +
                    COLUMN_USER_NAME + TYPE_VARCHAR + COMMA_SEP +
                    COLUMN_USER_PIN + TYPE_VARCHAR + COMMA_SEP +
                    COLUMN_USER_EMAIL + TYPE_VARCHAR + ")";

    private static final String CREATE_TABLE_SURVEY =
            CREATE_TABLE + TABLE_SURVEY + "(" +
                    COLUMN_SURVEY_ID + TYPE_INTEGER + PRIMARY_KEY_AUTO + COMMA_SEP +
                    COLUMN_SURVEY_DESCRIPTION + TYPE_VARCHAR + COMMA_SEP +
                    COLUMN_SURVEY_ATTEMPTS + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_AVERAGE + TYPE_REAL + COMMA_SEP +
                    COLUMN_SURVEY_LAST + TYPE_REAL + COMMA_SEP +
                    COLUMN_SURVEY_ACTIVE + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_LAST_ATTEMPT_TIME + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_NEXT_ATTEMPT_TIME + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_TIMES_ASKED_TODAY + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_LAST_ASKED_DAY + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_READY_TO_TAKE + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_DONE_ASKING + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_SURVEY_TYPE + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_USER_ID + TYPE_INTEGER + REF + TABLE_USER+ "(" + COLUMN_USER_PRIMARY_KEY + "))";

    private static final String CREATE_TABLE_QUESTION =
            CREATE_TABLE + TABLE_QUESTION + "(" +
                    COLUMN_QUESTION_ID + TYPE_INTEGER + PRIMARY_KEY_AUTO + COMMA_SEP +
                    COLUMN_QUESTION_TEXT + TYPE_TEXT + COMMA_SEP +
                    COLUMN_QUESTION_TYPE + TYPE_VARCHAR + COMMA_SEP +
                    COLUMN_SURVEY_ID + TYPE_INTEGER + REF + TABLE_SURVEY + "(" + COLUMN_SURVEY_ID + "))";

    private static final String CREATE_TABLE_ATTEMPT =
            CREATE_TABLE + TABLE_ATTEMPT + "(" +
                    COLUMN_ATTEMPT_ID + TYPE_INTEGER + PRIMARY_KEY_AUTO + COMMA_SEP +
                    COLUMN_ATTEMPT_DATETIME + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_ATTEMPT_SCORE + TYPE_REAL + COMMA_SEP +
                    COLUMN_SURVEY_ID + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_USER_ID + TYPE_INTEGER + REF + TABLE_USER + "(" + COLUMN_USER_ID + "))";

    private static final String CREATE_TABLE_ANSWER =
            CREATE_TABLE + TABLE_ANSWER + "(" +
                    COLUMN_QUESTION_ID + TYPE_INTEGER + COMMA_SEP +
                    COLUMN_ANSWER_TYPE + TYPE_VARCHAR + COMMA_SEP +
                    COLUMN_ANSWER_VALUE + TYPE_TEXT + COMMA_SEP +
                    COLUMN_ATTEMPT_ID + TYPE_INTEGER + REF + TABLE_ATTEMPT + "(" + COLUMN_ATTEMPT_ID + "))";


    private static DatabaseManager mInstance;


    public DatabaseManager(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    public static DatabaseManager getInstance(Context c){
        if (mInstance != null){
            return mInstance;
        }else{
            mInstance = new DatabaseManager(c);
            return mInstance;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SURVEY);
        db.execSQL(CREATE_TABLE_QUESTION);
        db.execSQL(CREATE_TABLE_ATTEMPT);
        db.execSQL(CREATE_TABLE_ANSWER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    ////////////////////////////////////////////////////////////////////////////////////////////
                                        /*      USER     */
    ////////////////////////////////////////////////////////////////////////////////////////////

    public long insertUser(User user){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USER_NAME, user.getmUsername());
        cv.put(COLUMN_USER_PIN, user.getmPin());
        cv.put(COLUMN_USER_EMAIL,user.getmEmail());
//        cv.put(COLUMN_USER_FB, user.getFacebook());
        return getWritableDatabase().insert(TABLE_USER,null,cv);
    }

    public UserCursor queryUsers(){
        Cursor wrapped = getReadableDatabase().query(TABLE_USER,null,null,null,null,null,null);
        return new UserCursor(wrapped);
    }

    public UserCursor queryUser(long id){
        Cursor wrapped = getReadableDatabase().query(TABLE_USER,null,"_id = ?",new String[]{String.valueOf(id)},null,null,null,"1");
        return new UserCursor(wrapped);
    }

    public static class UserCursor extends CursorWrapper {

        public UserCursor(Cursor cursor) {
            super(cursor);
        }

        public User getUser(){
            if(isBeforeFirst() || isAfterLast()) return null;

            User user = new User();
            user.setmId(getInt(getColumnIndex(COLUMN_USER_PRIMARY_KEY)));
            user.setmUsername(getString(getColumnIndex(COLUMN_USER_NAME)));
            user.setmPin(getString(getColumnIndex(COLUMN_USER_PIN)));
            user.setmEmail(getString(getColumnIndex(COLUMN_USER_EMAIL)));
//            user.setFacebook(getString(getColumnIndex(COLUMN_USER_FB)));
            return user;
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
                                        /*      SURVEY     */
    ////////////////////////////////////////////////////////////////////////////////////////////



    public long insertSurvey(Survey survey, int userId){
        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_SURVEY_ID,survey.getmId());
        cv.put(COLUMN_SURVEY_DESCRIPTION, survey.getmDescription());
        cv.put(COLUMN_SURVEY_ATTEMPTS,survey.getmId());
        cv.put(COLUMN_SURVEY_AVERAGE, survey.getmDescription());
        cv.put(COLUMN_SURVEY_LAST,survey.getmId());
        cv.put(COLUMN_SURVEY_ACTIVE, survey.getmActive());
        cv.put(COLUMN_SURVEY_LAST_ATTEMPT_TIME, survey.getLast_attempt_time());
        cv.put(COLUMN_SURVEY_NEXT_ATTEMPT_TIME, survey.getNext_attempt_time());
        cv.put(COLUMN_SURVEY_READY_TO_TAKE, survey.getReady_to_take());
        cv.put(COLUMN_SURVEY_TIMES_ASKED_TODAY, survey.getTimes_asked_today());
        cv.put(COLUMN_SURVEY_LAST_ASKED_DAY,survey.getLast_asked_day());
        cv.put(COLUMN_SURVEY_DONE_ASKING,survey.getDone_asking_today());
        cv.put(COLUMN_SURVEY_TYPE, survey.getSurvey_type());
        cv.put(COLUMN_USER_ID, userId);
        return getWritableDatabase().insert(TABLE_SURVEY,null,cv);
    }


    public SurveyCursor queryUserSurveys(long userId){
        Cursor wrapped = getReadableDatabase().query(TABLE_SURVEY,null,COLUMN_USER_ID + " = ?",new String[]{String.valueOf(userId)},null,null,null);
        return new SurveyCursor(wrapped);
    }

    public SurveyCursor querySurveys(){
        Cursor wrapped = getReadableDatabase().query(TABLE_SURVEY,null,null,null,null,null,null);
        return new SurveyCursor(wrapped);
    }

    public SurveyCursor querySurvey(int id){
        Cursor wrapped = getReadableDatabase().query(TABLE_SURVEY,null,COLUMN_SURVEY_ID + " = ?",new String[]{String.valueOf(id)},null,null,null,null);
        return new SurveyCursor(wrapped);
    }


    public void updateSurveyActive(int value, int surveyId, int userId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_ACTIVE + " = " + value +
                " WHERE " + COLUMN_USER_ID + " == " + userId +
                " AND " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void updateSurveyReadyToTake(int value, long surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_READY_TO_TAKE + " = " + value +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void updateSurveyLastTaken(long dateTime, int surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_LAST_ATTEMPT_TIME + " = " + dateTime +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }



    public void updateSurveyTimesAsked(int timesAskedToday, int surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_TIMES_ASKED_TODAY + " = " + timesAskedToday +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void incrementSurveyTimesAsked(int surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_TIMES_ASKED_TODAY + " = " + COLUMN_SURVEY_TIMES_ASKED_TODAY + "+1" +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }



    public void updateLastAskedDay(int askedDayofMonth, int surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_LAST_ASKED_DAY + " = " + askedDayofMonth +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }


    public void updateDoneAskingToday(int done, int surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_DONE_ASKING + " = " + done +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void updateDoneAskingToday(int done){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + COLUMN_SURVEY_DONE_ASKING + " = " + done;


        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void updateSurveyIntervention(int category, int taking, int surveyId){
        String sql = "UPDATE " + TABLE_SURVEY +
                " SET " + "interventions" + category + " = " + taking +
                " WHERE " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public static class SurveyCursor extends CursorWrapper {

        public SurveyCursor(Cursor cursor) {
            super(cursor);
        }

        public Survey getSurvey(){
            if(isBeforeFirst() || isAfterLast()) return null;

            Survey survey = new Survey();
            survey.setmId(getInt(getColumnIndex(COLUMN_SURVEY_ID)));
            survey.setmDescription(getString(getColumnIndex(COLUMN_SURVEY_DESCRIPTION)));
            survey.setmAttempts(getInt(getColumnIndex(COLUMN_SURVEY_ATTEMPTS)));
            survey.setmAverageScore(getDouble(getColumnIndex(COLUMN_SURVEY_AVERAGE)));
            survey.setmLastScore(getDouble(getColumnIndex(COLUMN_SURVEY_LAST)));
            survey.setmActive(getInt(getColumnIndex(COLUMN_SURVEY_ACTIVE)));
            survey.setLast_attempt_time(getLong(getColumnIndex(COLUMN_SURVEY_LAST_ATTEMPT_TIME)));
            survey.setReady_to_take(getInt(getColumnIndex(COLUMN_SURVEY_READY_TO_TAKE)));
            survey.setUser_id(getInt(getColumnIndex(COLUMN_USER_ID)));
            survey.setTimes_asked_today(getInt(getColumnIndex(COLUMN_SURVEY_TIMES_ASKED_TODAY)));

            survey.setLast_asked_day(getInt(getColumnIndex(COLUMN_SURVEY_LAST_ASKED_DAY)));
            survey.setDone_asking_today(getInt(getColumnIndex(COLUMN_SURVEY_DONE_ASKING)));
            survey.setSurvey_type(getInt(getColumnIndex(COLUMN_SURVEY_TYPE)));

            return survey;
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
                                        /*      QUESTION     */
    ////////////////////////////////////////////////////////////////////////////////////////////



    public long insertQuestion(Question question, int surveyId){
        ContentValues cv = new ContentValues();
//        cv.put(COLUMN_QUESTION_ID,question.getId());
        cv.put(COLUMN_QUESTION_TEXT,question.getText());
        cv.put(COLUMN_SURVEY_ID, surveyId);
        return getWritableDatabase().insert(TABLE_QUESTION,null,cv);
    }

    public QuestionCursor querySurveyQuestions(long surveyId){
        Cursor wrapped = getReadableDatabase().query(TABLE_QUESTION,null,COLUMN_SURVEY_ID + " = ?",new String[]{String.valueOf(surveyId)},null,null,null);
        return new QuestionCursor(wrapped);
    }

    public QuestionCursor queryQuestions(){
        Cursor wrapped = getReadableDatabase().query(TABLE_QUESTION,null,null,null,null,null,null);
        return new QuestionCursor(wrapped);
    }

    public void updateQuestion(int questionId, int surveyId, String text){
        String sql = "UPDATE " + TABLE_QUESTION +
                " SET " + COLUMN_QUESTION_TEXT + " = '" +  text +
                "' WHERE " + COLUMN_QUESTION_ID + " == " + questionId +
                " AND " + COLUMN_SURVEY_ID + " == " + surveyId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public static class QuestionCursor extends CursorWrapper {

        public QuestionCursor(Cursor cursor) {
            super(cursor);
        }

        public Question getQuestion(){
            if(isBeforeFirst() || isAfterLast()) return null;

            Question question = new Question();
            question.setId(getInt(getColumnIndex(COLUMN_QUESTION_ID)));
            question.setText(getString(getColumnIndex(COLUMN_QUESTION_TEXT)));
            question.setSurveyId(getInt(getColumnIndex(COLUMN_SURVEY_ID)));
            return question;
        }
    }




    ////////////////////////////////////////////////////////////////////////////////////////////
                                        /*      ATTEMPT     */
    ////////////////////////////////////////////////////////////////////////////////////////////

    public long insertAttempt(Attempt attempt){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SURVEY_ID,attempt.getSurveyId());
        cv.put(COLUMN_USER_ID,attempt.getUser_id());
        cv.put(COLUMN_ATTEMPT_DATETIME,attempt.getAttemptTime());
        cv.put(COLUMN_ATTEMPT_SCORE, attempt.getScore());
        return getWritableDatabase().insert(TABLE_ATTEMPT,null,cv);
    }

    public AttemptCursor queryAttempts(){
        Cursor wrapped = getReadableDatabase().query(TABLE_ATTEMPT,null,null,null,null,null,null);
        return new AttemptCursor(wrapped);
    }

    public AttemptCursor queryUserAttempts(long userId){
        Cursor wrapped = getReadableDatabase().query(TABLE_ATTEMPT,null,COLUMN_USER_ID + " = ?",new String[]{String.valueOf(userId)},null,null,null);
        return new AttemptCursor(wrapped);
    }

    public void updateAttemptScore(int attemptId, int attemptScore){
        String sql = "UPDATE " + TABLE_ATTEMPT +
                " SET " + COLUMN_ATTEMPT_SCORE + " = " + attemptScore +
                " WHERE " + COLUMN_ATTEMPT_ID + " == " + attemptId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public void updateAttemptTime(int attemptId, long dateTime){
        String sql = "UPDATE " + TABLE_ATTEMPT +
                " SET " + COLUMN_ATTEMPT_DATETIME + " = " + dateTime +
                " WHERE " + COLUMN_ATTEMPT_ID + " == " + attemptId;

        mInstance.getWritableDatabase().execSQL(sql);
    }

    public static class AttemptCursor extends CursorWrapper {

        public AttemptCursor(Cursor cursor) {
            super(cursor);
        }

        public Attempt getAttempt(){
            if(isBeforeFirst() || isAfterLast()) return null;

            Attempt attempt = new Attempt();
            attempt.setmId(getInt(getColumnIndex(COLUMN_ATTEMPT_ID)));
            attempt.setSurveyId(getInt(getColumnIndex(COLUMN_SURVEY_ID))); ;
            attempt.setUser_id(getInt(getColumnIndex(COLUMN_USER_ID)));
            attempt.setScore(getDouble(getColumnIndex(COLUMN_ATTEMPT_SCORE)));
            attempt.setAttemptTime(getLong(getColumnIndex(COLUMN_ATTEMPT_DATETIME)));
            return attempt;
        }
    }



    ////////////////////////////////////////////////////////////////////////////////////////////
                                        /*      ANSWER     */
    ////////////////////////////////////////////////////////////////////////////////////////////


    public long insertAnswer(Answer answer){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_ATTEMPT_ID,answer.getAttemptId());
        cv.put(COLUMN_QUESTION_ID,answer.getQuestionId());
//        cv.put(COLUMN_OPTION_ID,answer.getOptionId());
        cv.put(COLUMN_ANSWER_VALUE,answer.getValue());
        return getWritableDatabase().insert(TABLE_ANSWER,null,cv);
    }

    public AnswerCursor queryAnswers(){
        Cursor wrapped = getReadableDatabase().query(TABLE_ANSWER,null,null,null,null,null,null);
        return new AnswerCursor(wrapped);
    }

    public static class AnswerCursor extends CursorWrapper {

        public AnswerCursor(Cursor cursor) {
            super(cursor);
        }

        public Answer getAnswer(){
            if(isBeforeFirst() || isAfterLast()) return null;

            Answer answer = new Answer();
            answer.setAttemptId(getInt(getColumnIndex(COLUMN_ATTEMPT_ID))); ;
            answer.setQuestionId(getInt(getColumnIndex(COLUMN_QUESTION_ID)));
//            answer.setOptionId(getInt(getColumnIndex(COLUMN_OPTION_ID)));
            answer.setValue(getInt(getColumnIndex(COLUMN_ANSWER_VALUE)));
            return answer;
        }
    }
}
