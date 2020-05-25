package com.zjw.basicandroid.helper;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;
import com.zjw.basicandroid.R;

import java.util.List;

public class PermissionHelper {
    static PermissionHelper helper;
    public static PermissionHelper getInstance(){
        if(helper==null){
            helper = new PermissionHelper();
        }
        return helper;
    }

    public interface PermissionCallBack{
        void onSuccess();
        void onFail();
    }

    /**
     *申请权限
     * @param context context 一定是activity
     * @param permissionsParams
     */
    public void getPermission(Activity context, PermissionCallBack callBack, String... permissionsParams){
        AndPermission.with(context)
                .runtime()
                .permission(permissionsParams)
                .onGranted(permissions -> {
                    callBack.onSuccess();
                })
                .onDenied(permissions -> {
//                    ToastUtil.showToast(context,context.getResources().getString(R.string.permission_failed));
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        callBack.onFail();
                        showSettingDialog(context, permissions);
                    }
                }).start();
    }

    /**
     *  申请权限组
     * @param context context 一定是activity
     * @param groups
     */
    public void getPermission(Activity context, PermissionCallBack callBack, String[]... groups){
        AndPermission.with(context)
                .runtime()
                .permission(groups)
                .onGranted(permissions -> {
                    callBack.onSuccess();
                })
                .onDenied(permissions -> {
                    callBack.onFail();
//                    ToastUtil.showToast(context,context.getResources().getString(R.string.permission_failed));
                    if (AndPermission.hasAlwaysDeniedPermission(context, permissions)) {
                        showSettingDialog(context, permissions);
                    }
                }).start();
    }

    /**
     //     * Display setting dialog.
     //     //用户始终禁止权限 弹出设置提示框
     //     <string name="message_permission_always_failed" >请到应用设置同意权限申请:\n\n%1$s</string>
     //     */
    private void showSettingDialog(Context context, final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.message_permission_always_failed, TextUtils.join("\n", permissionNames));

        new AlertDialog.Builder(context)
                .setCancelable(false)
                .setTitle(R.string.tip_text)
                .setMessage(message)
                .setPositiveButton(R.string.setting, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setPermission(context);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }

    /**
     * Set permissions.
     */
    private void setPermission(Context context) {
        AndPermission.with(context)
                .runtime()
                .setting()
                .start(0);
    }

}
