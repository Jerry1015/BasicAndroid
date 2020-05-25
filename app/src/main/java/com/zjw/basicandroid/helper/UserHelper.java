package com.zjw.basicandroid.helper;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;


import com.zjw.basicandroid.module.login.LoginActivity;
import com.zjw.basicandroid.utils.GsonUtils;
import com.zjw.basicandroid.utils.LogUtil;
import com.zjw.basicandroid.utils.SPUtil;

import org.greenrobot.eventbus.EventBus;


/**
 * Created by FrankZhang on 2017/9/12.
 * 用户信息管理类 用户个人信息 用户登录 退出登录 注销第三方sdk 都在这个类处理
 */

public class UserHelper {

    private static final String CURRENT_USER_INFO = "current_user_info";

    public static boolean checkGoLogin(Context context){
        if(!UserHelper.isLogin()){
            Intent intent = new Intent(context, LoginActivity.class);
            context.startActivity(intent);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 登录执行 发送登录成功消息
     * @param userBean
     */
    public static void login(UserBean userBean) {
        saveCurrentUserInfo(userBean);
        EventBus.getDefault().post(new LoginStatusEvent(LoginStatusEvent.LoginStatus.LOGIN));
        //设置别名
//        JPushHelper.setAlias(UserHelper.getUser().getPush_id());
    }

    /**
     * 退出登录执行 发送退出消息
     */
    public static void loginOut() {
        clearCurrentUserInfo();
        EventBus.getDefault().post(new LoginStatusEvent(LoginStatusEvent.LoginStatus.LOGINOUT));
        // 取消别名
//        JPushHelper.setAlias("");
    }



    /**
     * 是否登录
     * @return true yes
     */
    public static boolean isLogin() {
        return  !TextUtils.isEmpty(getCurrentToken());
    }

    /**
     * 缓存登录用户信息 这里只在登录调用
     * @param bean
     */
    public static void saveCurrentUserInfo(UserBean bean) {
        LogUtil.e("frank","保存的登录信息:  "+ GsonUtils.serializedToJson(bean));
        SPUtil.setString(CURRENT_USER_INFO, GsonUtils.serializedToJson(bean));
    }


    /**
     * 获取缓存登录用户信息
     */
    public static UserBean getUser() {
        UserBean userBean = GsonUtils.jsonToBean(SPUtil.getString(CURRENT_USER_INFO, ""), UserBean.class);
        return userBean==null?new UserBean():userBean;
    }

    /**
     * 获取当前的登录凭证
     * @return
     */
    public static String getCurrentToken(){
        UserBean user = getUser();
        return  user==null?"":user.getToken();
    }


    /**
     * 清除缓存登录用户信息
     */
    public static void clearCurrentUserInfo() {
        SPUtil.setString(CURRENT_USER_INFO,"");
    }
}
