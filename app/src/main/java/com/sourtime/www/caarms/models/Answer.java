package com.sourtime.www.caarms.models;

/**
 * Created by user on 27/09/2016.
 */

public class Answer {

    private long attemptId;
    private long questionId;
    private long optionId;
    private int value;

    public Answer(){

    }

    public Answer(long attemptId, long questionId, int value){
        setAttemptId(attemptId);
        setQuestionId(questionId);
        setValue(value);
    }


    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(long attemptId) {
        this.attemptId = attemptId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public long getOptionId() {
        return optionId;
    }

    public void setOptionId(long optionId) {
        this.optionId = optionId;
    }
}
