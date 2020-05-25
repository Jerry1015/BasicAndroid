package com.zjw.basicandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

import com.zjw.basicandroid.basic.MainApplication;

import java.util.ArrayList;


/**
 *
 * SharedPreferences 设置管理类
 */
public class SPUtil {
    private static SharedPreferences mPrefs;


    // 一些比较通用的值 --------
    /*主题*/
    public static String theme = "light";
    private static ArrayList<OnSharedPreferenceChangeListener> mListeners = new ArrayList<>();



    //fix 4.29： bug2 
    private static SharedPreferences getSharedPreferences() {
        if (mPrefs == null) {
            mPrefs = PreferenceManager.getDefaultSharedPreferences(MainApplication.getAppContext());
        }
        return mPrefs;
    }

    public static SharedPreferences getPrivatePreferences(String name) {
        return MainApplication.getAppContext().getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    public static void addOnSharedPreferenceChanged(OnSharedPreferenceChangeListener l) {
        mListeners.add(l);
    }

    static OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener = new OnSharedPreferenceChangeListener() {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            onPreferenceChanged(sharedPreferences, key); //先要保证本类是最新的，才能让其它类用
            int size = mListeners.size();
            for (int i = 0; i < size; i++) {
                mListeners.get(i).onSharedPreferenceChanged(sharedPreferences, key);
            }
        }
    };

    private static void onPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        //重新读取配置到变量属性
        if (key == null || "theme".equals(key))
            theme = getSharedPreferences().getString("theme", theme);

    }

    public static void cleanAll() {
        getSharedPreferences().edit().clear().commit();
    }


    public static String getString(String key, String defaultValue) {
        return getSharedPreferences().getString(key, defaultValue);
    }

    public static boolean setString(String key, String value) {
        boolean result = getSharedPreferences().edit().putString(key, value).commit();
        if (result)
            onPreferenceChanged(getSharedPreferences(), key);
        return result;
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSharedPreferences().getBoolean(key, defaultValue);
    }

    public static boolean setBoolean(String key, boolean value) {
        boolean result = getSharedPreferences().edit().putBoolean(key, value).commit();
        if (result)
            onPreferenceChanged(getSharedPreferences(), key);
        return result;
    }

    public static long getLong(String key, long defaultValue) {
        return getSharedPreferences().getLong(key, defaultValue);
    }

    public static boolean setLong(String key, long value) {
        boolean result = getSharedPreferences().edit().putLong(key, value).commit();
        if (result)
            onPreferenceChanged(getSharedPreferences(), key);
        return result;
    }

    public static int getInt(String key, int defaultValue) {
        return getSharedPreferences().getInt(key, defaultValue);
    }

    public static boolean setInt(String key, int value) {
        boolean result = getSharedPreferences().edit().putInt(key, value).commit();
        if (result)
            onPreferenceChanged(getSharedPreferences(), key);
        return result;
    }


    public static double getDouble(String key, double defaultValue) {
        return Double.valueOf(getString(key, String.valueOf(defaultValue)));
    }

    public static boolean setDouble(String key, double value) {
        return setString(key, String.valueOf(value));
    }

    public static boolean remove(String key) {
        return getSharedPreferences().edit().remove(key).commit();
    }
}
