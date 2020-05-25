package com.zjw.basicandroid.basic;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.zjw.basicandroid.helper.UserHelper;

import java.util.ArrayList;


public class MainApplication extends Application {

    private final static ArrayList<BaseActivity> activitys = new ArrayList<>();
    private static MainApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

    }


    //全局记录activity
    public static void addActivity(BaseActivity context) {
        activitys.add(context);
    }
    public static void removeActivity(BaseActivity context) {
        activitys.remove(context);
    }
    public static void finishAllActivity() {
        Activity activity;
        for (int i = activitys.size() - 1; i >= 0; i--) {
            activity = activitys.get(i);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    /**
     * 显示登录凭证失效弹窗提醒在当前显示的界面上
     */
    public static void showTokenInvalidHint(){
        BaseActivity activity = activitys.get(activitys.size()==0?0:activitys.size() - 1);
        UserHelper.loginOut();
        activity.showTokenInvalidHintDialog();
    }




    public static MainApplication getInstance() {
        return instance;
    }
    public static Context getAppContext() {
        return instance.getApplicationContext();
    }


}
