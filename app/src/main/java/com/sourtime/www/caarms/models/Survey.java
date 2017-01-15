package com.sourtime.www.caarms.models;

/**
 * Created by user on 27/09/2016.
 */

public class Survey {

    private long mId;
    private String mDescription;
    private int mAttempts;
    private double mAverageScore;
    private double mLastScore;
    private int mActive;
    private int user_id;
    private long last_attempt_time;
    private long next_attempt_time;
    private int ready_to_take;
    private int distress_level;
    private int times_asked_today;
    private int last_asked_day;
    private long next_followup_time;
    private int done_asking_today;
    private int survey_type;



    public Survey(){
        mId = -1;
        mDescription = "Survey";
        mActive = 0;
        mAttempts = 0;
        mAverageScore = 0;
        mLastScore = 0;
        user_id = 0;
        distress_level = 0;
        ready_to_take = 1;
        distress_level = 0;
        times_asked_today = 0;
        last_asked_day = 0;
        next_followup_time = 0;
        done_asking_today = 0;


    }

    public Survey(int userId){
        mId = -1;
        mDescription = "Survey";
        mAttempts = 0;
        mAverageScore = 0;
        mLastScore = 0;
        user_id = userId;
        mActive = 1;
        last_attempt_time = System.currentTimeMillis();
//        last_attempt_time = (1/60) * 60 * 60 * 1000; // 1 minute ago
        ready_to_take = 1;
        distress_level = 0;
        times_asked_today = 0;
        last_asked_day = 0;
        next_followup_time = 0;
        done_asking_today = 0;
        survey_type = 0;

    }

    public Survey(int userId, int surveyNumber, int type){
        mId = -1;
        mDescription = "Survey " + surveyNumber;
        mAttempts = 0;
        mAverageScore = 0;
        mLastScore = 0;
        user_id = userId;
        mActive = 0;
        last_attempt_time = System.currentTimeMillis();
//        last_attempt_time = (1/60) * 60 * 60 * 1000; // 1 minute ago
        ready_to_take = 1;
        distress_level = 0;
        times_asked_today = 0;
        last_asked_day = 0;
        next_followup_time = 0;
        done_asking_today = 0;
        survey_type = type;

    }

    public Survey(long id, String description, int attempts, double average, double last, int active, int userid){
        mId = id;
        mDescription = description;
        mAttempts = attempts;
        mAverageScore = average;
        mLastScore = last;
        mActive = active;
        user_id = userid;
        last_attempt_time = System.currentTimeMillis();
//        last_attempt_time = (1/60) * 60 * 60 * 1000; // 1 minute ago
        ready_to_take = 1;
        distress_level = 0;
        times_asked_today = 0;
        last_asked_day = 0;
        next_followup_time = 0;
        done_asking_today = 0;

    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public int getmAttempts() {
        return mAttempts;
    }

    public void setmAttempts(int mAttempts) {
        this.mAttempts = mAttempts;
    }

    public double getmAverageScore() {
        return mAverageScore;
    }

    public void setmAverageScore(double mAverageScore) {
        this.mAverageScore = mAverageScore;
    }

    public double getmLastScore() {
        return mLastScore;
    }

    public void setmLastScore(double mLastScore) {
        this.mLastScore = mLastScore;
    }

    public int getmActive() {
        return mActive;
    }

    public void setmActive(int mActive) {
        this.mActive = mActive;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public long getLast_attempt_time() {
        return last_attempt_time;
    }

    public void setLast_attempt_time(long last_attempt_time) {
        this.last_attempt_time = last_attempt_time;
    }

    public int getReady_to_take() {
        return ready_to_take;
    }

    public void setReady_to_take(int ready_to_take) {
        this.ready_to_take = ready_to_take;
    }

    public int getDistress_level() {
        return distress_level;
    }

    public void setDistress_level(int distress_level) {
        this.distress_level = distress_level;
    }


    public long getNext_attempt_time() {
        return next_attempt_time;
    }

    public void setNext_attempt_time(long next_attempt_time) {
        this.next_attempt_time = next_attempt_time;
    }

    public int getTimes_asked_today() {
        return times_asked_today;
    }

    public void setTimes_asked_today(int times_asked_today) {
        this.times_asked_today = times_asked_today;
    }


    public int getLast_asked_day() {
        return last_asked_day;
    }

    public void setLast_asked_day(int last_asked_day) {
        this.last_asked_day = last_asked_day;
    }

    public long getNext_followup_time() {
        return next_followup_time;
    }

    public void setNext_followup_time(long next_followup_time) {
        this.next_followup_time = next_followup_time;
    }

    public int getDone_asking_today() {
        return done_asking_today;
    }

    public void setDone_asking_today(int done_asking_today) {
        this.done_asking_today = done_asking_today;
    }

    public int getSurvey_type() {
        return survey_type;
    }

    public void setSurvey_type(int survey_type) {
        this.survey_type = survey_type;
    }


}

