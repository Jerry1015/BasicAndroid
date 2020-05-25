package com.zjw.basicandroid.basic;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.IBinder;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.zjw.basicandroid.R;
import com.zjw.basicandroid.module.login.LoginActivity;
import com.zjw.basicandroid.net.NetworkManager;
import com.zjw.basicandroid.net.OnRequestCallBack;
import com.zjw.basicandroid.utils.InputMethodUtils;
import com.zjw.basicandroid.utils.LogUtil;
import com.zjw.basicandroid.utils.ToastUtil;

import java.util.List;
import java.util.Map;


public abstract class BaseActivity extends AppCompatActivity {

    private ProgressDialog loadingDialog;
    private TextView loading_tv;
    protected Context mContext;
    private View dialogLayout;
    private AlertDialog hintDialog;
    public boolean isDestory;
    private boolean isShowingTokanHint;
    private AlertDialog mTokenHintDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MainApplication.addActivity(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        hideBottomUIMenu();
        mContext = this;
    }


    @Override
    protected void onStop() {
        super.onStop();
        InputMethodUtils.hideSoftInput(this);
    }

    protected void onDestroy() {
        /*//强制content view尽早释放
        setContentView(new View(this));*/ //CP
        isDestory = true;
        hideProgress();
        if (hintDialog != null && hintDialog.isShowing()) {
            hintDialog.dismiss();
        }
        if (mTokenHintDialog != null && mTokenHintDialog.isShowing()) {
            mTokenHintDialog.dismiss();
        }
        MainApplication.removeActivity(this);
        super.onDestroy();
        System.gc();
    }

    protected void hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT >= 19) {
            //for new api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View
                    .SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }


    /*------------------------沉浸式状态栏------------------------*/
    public void translucentStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int flagTranslucentStatus = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            int flagTranslucentNavigation = WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentNavigation;
                window.setAttributes(attributes);
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            } else {
                Window window = getWindow();
                WindowManager.LayoutParams attributes = window.getAttributes();
                attributes.flags |= flagTranslucentStatus | flagTranslucentNavigation;
                window.setAttributes(attributes);
            }
        }
    }

    /*------------------------控件绑定------------------------*/
    protected <T extends View> T $(int resId) {
        return (T) super.findViewById(resId);
    }

    public <T extends View> T $(int resId, View rootView) {
        if (rootView == null) {
            throw new IllegalStateException("Content view not yet created");
        }
        return (T) rootView.findViewById(resId);
    }

    /*------------------------页面跳转------------------------*/
    public void startActivity(Class<?> clz) {
        startActivity(new Intent(this, clz));
    }

    public void startActivityForResult(Class<?> clz, int requestCode) {
        startActivityForResult(new Intent(this, clz), requestCode);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /*------------------------网络请求------------------------*/
    public void post(String api, Map<String,String> params, OnRequestCallBack callBack){
        NetworkManager.INSTANCE.post(api,params,callBack);
    }
    public void post(String tag,String api, Map<String,String> params, OnRequestCallBack callBack){
        NetworkManager.INSTANCE.post(tag,api,params,callBack);
    }


    /*------------------------Toast方法------------------------*/
    public void showToast(String message) {
        ToastUtil.showToast(mContext, message);
    }

    /*------------------------弹窗提示------------------------*/
    public void showHintDialog(String message) {
        if (isFinishing()) return;
        if (hintDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage(message);
            builder.setPositiveButton("确定", null);
            hintDialog = builder.show();
        } else {
            hintDialog.setMessage(message);
            hintDialog.show();
        }
    }

    /*------------------------加载提示 loading 方法------------------------*/
    public void showProgress(String message) {
        showProgress(message, true);
    }

    public void showProgress() {
        showProgress("请稍后...", true);
    }

    public void showProgress(boolean isCancelable) {
        showProgress("请稍后...", isCancelable);
    }

    public void showProgress(String message, boolean isCancelable) {
        if (isFinishing()) return;
        if (loadingDialog == null) {
            LayoutInflater inflater = LayoutInflater.from(this);
            // 得到加载view
            dialogLayout = inflater.inflate(R.layout.layout_view_loading, null);
            loading_tv = (TextView) dialogLayout.findViewById(R.id.loading_tv);
            loadingDialog = new ProgressDialog(this, R.style.transparent_dialog_theme);
            loadingDialog.setCancelable(isCancelable);
            loadingDialog.setCanceledOnTouchOutside(isCancelable);
            loadingDialog.show();
            loadingDialog.setContentView(dialogLayout);// 设置布局 需要在show后执行 否则报requestFeature() must be called before adding content
            loadingDialog.setOnDismissListener(dialog -> onLoadingDismiss());
        }
        loadingDialog.setCancelable(isCancelable);
        loadingDialog.setCanceledOnTouchOutside(isCancelable);
        loading_tv.setText(message);
        if (getBaseContext() != null && !loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void onLoadingDismiss() {

    }

    public void hideProgress() {
        if (loadingDialog != null) {
            loadingDialog.dismiss();
        }
    }


    /*------------------------ 验证码倒计时方法------------------------*/
    public void countDownReSend(final TextView textView, long sec) {
        if (textView == null) return;
        showToast("验证码发送成功，请注意查收");
        textView.setTag(textView.getTextColors());
        textView.setEnabled(false);
        new CountDownTimer(sec * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (isDestory) {
                    cancel();
                    return;
                }
                textView.setText((millisUntilFinished / 1000) + "秒倒计时");
            }

            public void onFinish() {
                if (isDestory)
                    return;
                textView.setText("重新获取");
                textView.setEnabled(true);
                textView.setSelected(false);
            }
        }.start();
    }

    /*------------------------弹出登录失效弹窗------------------------*/
    public void showTokenInvalidHintDialog() {
        if (isShowingTokanHint) return;
        if (isFinishing()) return;
        isShowingTokanHint = true;
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("您的登录凭证已失效，请重新登录！");
        builder.setCancelable(false);
        builder.setOnDismissListener(dialog -> {
            isShowingTokanHint = false;
        });
        builder.setNegativeButton("", (dialog, which) -> {

        });
        builder.setPositiveButton("登录", (dialog, which) -> {
            startActivity(LoginActivity.class);
        });
        mTokenHintDialog = builder.show();
    }

    /*------------------------校验方法------------------------*/
    protected boolean isEmpty(String value) {
        return TextUtils.isEmpty(value);
    }

    protected <T> boolean isEmpty(List<T> value) {
        return value == null || value.size() == 0;
    }

    protected <T> boolean isEmpty(Map<T, T> value) {
        return value == null || value.size() == 0;
    }

    protected boolean isNull(Object o) {
        return o == null;
    }

    /*------------------------日志方法------------------------*/
    protected void logE(String s) {
        LogUtil.e(getClass().getSimpleName(), s);
    }
    protected void logE(String tag, String s) {
        LogUtil.e(tag, s);
    }


    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }

        }
        return super.dispatchTouchEvent(me);
    }



    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
