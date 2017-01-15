package com.sourtime.www.caarms.models;

/**
 * Created by user on 27/09/2016.
 */

public class User {

    private long mId;
    private String mUsername;
    private String mPin;
    private String mEmail;
    private String number;


    public User(){
        mId = -1;

    }

    public User(String username, String email, String pin){
        mId = -1;
        mUsername = username;
        mEmail = email;
        mPin = pin;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
    }

    public String getmUsername() {
        return mUsername;
    }

    public void setmUsername(String mUsername) {
        this.mUsername = mUsername;
    }

    public String getmPin() {
        return mPin;
    }

    public void setmPin(String mPin) {
        this.mPin = mPin;
    }



    public String getmEmail() {
        return mEmail;
    }

    public void setmEmail(String mEmail) {
        this.mEmail = mEmail;
    }
}
