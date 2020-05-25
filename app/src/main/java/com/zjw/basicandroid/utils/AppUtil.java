package com.zjw.basicandroid.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;

import com.zjw.basicandroid.basic.MainApplication;


/**
 * Created by Frank on 2017/3/8.
 */

public enum AppUtil {
    INSTANCE;


    /**
     * 返回当前程序版本名 废弃 使用BuildConfig.VERSION_NAME替代
     */
    public String getAppVersionName(Context context) {
        String versionName = "";
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            LogUtil.e("VersionInfo", "Exception" + e.getMessage());
        }
        return versionName;
    }



    /**
     * 返回当前程序版本号 废弃 使用BuildConfig.VERSION_CODE 替代
     */
    public int getAppVersionCode(Context context) {
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versioncode = pi.versionCode;
            if (versioncode == 0) {
                return 0;
            }
        } catch (Exception e) {
            LogUtil.e("VersionInfo", "Exception" + e.getMessage());
        }
        return versioncode;
    }

    /**
     * 获取设备号 必须申请 android.permission.READ_PHONE_STATE 权限
     * @return
     */
    public static String getDeviceToken() {
        // 获取设备号
        TelephonyManager tm = (TelephonyManager) MainApplication.getAppContext()
                .getSystemService(Context.TELEPHONY_SERVICE);
        //忽略此处代码检测 权限在外部调用时判断
        @SuppressLint("MissingPermission") String device_token = tm.getDeviceId();
        return device_token;
    }



}
