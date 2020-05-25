package com.zjw.basicandroid.net;

public interface OnRequestCallBack {
        void onOk(int code, String msg, String data);
        void onError(int errorCode, String errorMessage);

    }