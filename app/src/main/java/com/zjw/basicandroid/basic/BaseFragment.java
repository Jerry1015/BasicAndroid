package com.zjw.basicandroid.basic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zjw.basicandroid.net.OnRequestCallBack;
import com.zjw.basicandroid.utils.ToastUtil;

import java.util.Map;


/**
 * Created by Frank on 2017/3/25.
 */

public abstract class BaseFragment extends Fragment{

    protected View rootView;
    private String fragmentName;
    public Activity mContext;
    public BaseActivity mActivity;
    /**BaseActivity */
    public BaseActivity base;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            try {
                rootView = inflater.inflate(getLayoutId(), container, false);
                rootView.setClickable(true);
            } catch (OutOfMemoryError e) {
                System.gc();
                rootView = inflater.inflate(getLayoutId(), container, false);
            }
        }
        /*
         * 避免tab切换的时候重绘
         * 缓存的rootView需要判断是否已经被加过parent， 如果有parent需要从parent删除，
         * 要不然会发生这个rootview已经有parent的错误。
         */
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    public View getRootView() {
        return rootView;
    }

    public void showToast(String message){
        ToastUtil.showToast(getActivityContext(),message);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (BaseActivity) context;
        base =mActivity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mContext = getActivityContext();
        initView();
        fragmentName = this.getClass().toString();
        fragmentName = fragmentName.substring(fragmentName.lastIndexOf(".") + 1);
    }


    /*---控件绑定---*/
    public <T extends View> T $(int resId) {
        return $(resId, rootView);
    }

    public <T extends View> T $(int resId, View rootView) {
        if (rootView == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        return (T) rootView.findViewById(resId);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    public Activity getActivityContext() {
        return mActivity;
    }

    /*------------------------页面跳转------------------------*/
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(getActivityContext(), clz));
    }

    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(new Intent(getActivityContext(), clz), requestCode);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivityContext(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void finishActivity() {
        if (getActivityContext() != null) {
            getActivityContext().finish();
        }
    }

    public void showProgress(){
        mActivity.showProgress();
    }

    public void hideProgress(){
        mActivity.hideProgress();
    }

    public void post(String api, Map<String,String> params, OnRequestCallBack callBack){
        mActivity.post(api, params,callBack);
    }

    public void showHintDialog(String msg){
        mActivity.showHintDialog(msg);
    }

}
