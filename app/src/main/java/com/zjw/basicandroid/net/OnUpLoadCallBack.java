package com.zjw.basicandroid.net;

public interface OnUpLoadCallBack {
        void onOk(String response);
        void onError(int code, String errorMessage);
        void upProgress(int progress);

    }