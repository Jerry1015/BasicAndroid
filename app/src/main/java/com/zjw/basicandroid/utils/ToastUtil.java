package com.zjw.basicandroid.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zjw.basicandroid.R;

import java.lang.ref.WeakReference;


/**
 * Created by Frank on 2017/6/13.
 */

public class ToastUtil {

    /**
     * 纯文字自定义toast
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message){
        showOne(context,message);
    }

    private static WeakReference<Toast> mToastRef = null;

    /**
     * 自定义Toast 只显示一个
     */
    public static void showOne(Context context, String text) {
        Toast toast;
        if (mToastRef != null && (toast = mToastRef.get()) != null) {
            toast.setDuration(Toast.LENGTH_SHORT);
            TextView tv = (TextView) toast.getView().findViewById(R.id.message_tv);
            tv.setText(text);
        } else {
            toast = new Toast(context);
            LayoutInflater inflate =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflate.inflate(R.layout.toast_layout, null);
            TextView tv = (TextView) view.findViewById(R.id.message_tv);
            tv.setText(text);
            toast.setView(view);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            mToastRef = new WeakReference<>(toast);
        }

        toast.show();
    }


    /**
     * 带图片自定义toast
     * @param context
     * @param message
     * @param resIcon
     */
    public static void showToast(Context context, String message, int resIcon){
        Toast toast = new Toast(context);
        View view = View.inflate(context, R.layout.toast_layout,null);
        TextView messageTv = (TextView) view.findViewById(R.id.message_tv);
        ImageView toast_pic_iv = (ImageView) view.findViewById(R.id.toast_pic_iv);
        if(resIcon!=0){
            toast_pic_iv.setVisibility(View.VISIBLE);
            toast_pic_iv.setImageResource(resIcon);
        }
        messageTv.setText(message);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }
}
