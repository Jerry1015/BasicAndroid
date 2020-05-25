package com.zjw.basicandroid.net;

import android.app.Application;
import android.text.TextUtils;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.zjw.basicandroid.basic.MainApplication;
import com.zjw.basicandroid.helper.UserHelper;
import com.zjw.basicandroid.utils.GsonUtils;
import com.zjw.basicandroid.utils.LogUtil;

import java.util.Map;


/**
 */
public enum NetworkManager{
    INSTANCE;
    public void init(Application application) {
        //必须调用初始化
        OkGo.getInstance().init(application);
//        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        //全局的读取超时时间
//        builder.readTimeout(15000, TimeUnit.MILLISECONDS);
//        //全局的写入超时时间
//        builder.writeTimeout(15000, TimeUnit.MILLISECONDS);
//        //全局的连接超时时间
//        builder.connectTimeout(15000, TimeUnit.MILLISECONDS);
    }

    //请求 post
    public void post(String api, Map<String, String> params, OnRequestCallBack callBack) {
        post("",api,params,callBack);
    }
    //请求 post
    public void post(Object tag, String api, Map<String, String> params, OnRequestCallBack callBack) {
        params.put("token", UserHelper.getCurrentToken());
        OkGo.<String>post(api)//
                .tag(tag)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        success(response.body(), api, callBack, params,"post");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        error(response.getException().toString(), api, params, callBack,"post");
                    }

                });

    }

    public void get(final String api, final Map<String, String> params, final OnRequestCallBack callBack) {
        get("",api,params,callBack);
    }
    public void get(Object tag, final String api, final Map<String, String> params, final OnRequestCallBack callBack) {
        params.put("token", UserHelper.getCurrentToken());
        OkGo.<String>get(api)//
                .tag(tag)
                .params(params)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        success(response.body(), api, callBack, params,"get");
                    }

                    @Override
                    public void onError(Response<String> response) {
                        error(response.getException().toString(), api, params, callBack,"get");
                    }

                });

    }

    private void error(String err, String api, Map<String, String> params, OnRequestCallBack callBack, String requestType) {
        if(err.contains("Unable to resolve host")){
            callBack.onError(-999,"网络连接失败，请检查您的网络");
        }else if(err.contains("Failed to connect")||err.contains("failed to connect")){
            callBack.onError(-998,"服务器连接失败，请稍后再试或联系客服");
        }else if(err.contains("timed out")||err.contains("timeout")){
            callBack.onError(-997,"请求超时,请重试");
        }else{
            callBack.onError(-996,err);
        }
        LogUtil.e("okhttp:", "*---------------------------------------------------------------------------*");
        LogUtil.e("okhttp:", "requestUrl("+requestType+"-mode1)= "+ api);
        LogUtil.e("okhttp:", "isSuccess = 请求错误" + err);
        LogUtil.e("okhttp:", "requestParams： " + GsonUtils.serializedToJson(params));
        LogUtil.e("okhttp:", "response= " + err);
        LogUtil.e("okhttp:", "*                                                                      *");

    }

    private void success(String s, String api, OnRequestCallBack callBack, Map<String, String> params, String requestType) {
        LogUtil.e("okhttp:", "*---------------------------------------------------------------------------*");
        LogUtil.e("okhttp:", "requestUrl("+requestType+"-mode1)= " + api);
        if (s == null) {
            LogUtil.e("okhttp:", "isSuccess = true 数据返回失败");
            print(s,params);
            callBack.onError(-1000,"数据返回空");
            return;
        }
        print(s, params);
        String msg = GsonUtils.getStringFromJSON(s, "msg");
        int code = GsonUtils.getIntFromJSON(s, "code");
        if (code==1) {
            LogUtil.e("okhttp:", "isSuccess = true 数据返回正常");
            callBack.onOk(code,msg,GsonUtils.getStringFromJSON(s, "data"));
        } else {
            if(code==401){  //重新登录
                MainApplication.showTokenInvalidHint();
            }
            if(!TextUtils.isEmpty(msg)){
                LogUtil.e("okhttp:", "isSuccess = true 数据返回失败:"+msg);
                callBack.onError(code,msg);
            }else{
                callBack.onError(code,"数据返回异常:"+msg);
            }

        }
    }

    private void print(String s, Map<String, String> params) {
        LogUtil.e("okhttp:", "requestParams： " + GsonUtils.serializedToJson(params));
        LogUtil.e("okhttp:", "response= " + s);
        LogUtil.e("okhttp:", "*----------------------------------------------------------------------------*");
    }



}
