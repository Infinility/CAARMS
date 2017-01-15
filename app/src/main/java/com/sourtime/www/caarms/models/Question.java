package com.sourtime.www.caarms.models;

/**
 * Created by user on 27/09/2016.
 */

public class Question {

    private long Id;
    private String text;
    private int surveyId;

    public Question(){

    }

    public Question(String text, int surveyId){
        setId(-1);
        setText(text);
        setSurveyId(surveyId);
    }

    public Question(long id, String text, int surveyId){
        setId(id);
        setText(text);
        setSurveyId(surveyId);
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        this.Id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }
}

