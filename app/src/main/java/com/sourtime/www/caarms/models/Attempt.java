package com.sourtime.www.caarms.models;

/**
 * Created by user on 27/09/2016.
 */

public class Attempt {

    private long mId;
    private long surveyId;
    private long attemptTime;
    private double score;
    private long user_id;

    public Attempt(){

    }

    public Attempt(long surveyId, long user_id, long dateTime){
        setSurveyId(surveyId);
        setUser_id(user_id);
        setAttemptTime(dateTime);
        setScore(0);
    }


    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(long surveyId) {
        this.surveyId = surveyId;
    }

    public long getAttemptTime() {
        return attemptTime;
    }

    public void setAttemptTime(long attemptTime) {
        this.attemptTime = attemptTime;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }
}
