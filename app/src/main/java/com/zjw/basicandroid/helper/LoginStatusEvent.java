package com.zjw.basicandroid.helper;


/**
 * Created by FrankZhang on 2017/9/12.
 */

public class LoginStatusEvent {

    public enum LoginStatus {
        LOGIN , LOGINOUT;
    }

    private LoginStatus loginStatus;
    public LoginStatusEvent(LoginStatus status){
        loginStatus = status;
    }

    public LoginStatus getLoginStatus(){
        return loginStatus;
    }
}
