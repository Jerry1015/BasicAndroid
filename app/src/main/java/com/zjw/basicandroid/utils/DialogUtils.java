package com.zjw.basicandroid.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zjw.basicandroid.R;

import java.util.List;

public class DialogUtils {


    public static void showDialog(Context context, String content, final DialogImpl listener) {
        showDialog(context, content, "确定", "取消", true, listener);
    }

    public static void showDialog(Context context, String content, boolean isCancelable, final DialogImpl listener) {
        showDialog(context, content, "确定", "取消", isCancelable, listener);
    }

    public static void showDialog(Context context, String content, String ok, String cancel, final DialogImpl listener) {
        showDialog(context, content, ok, cancel, true, listener);
    }

    public static void showDialog(Context context, String content, String ok, String cancel, boolean isCancelable, final DialogImpl listener) {
        View view = View.inflate(context, R.layout.layout_dialog_default, null);
        TextView messagetv = view.findViewById(R.id.messageTv);
        Button cancelBtn = view.findViewById(R.id.cancelBtn);
        Button okBtn = view.findViewById(R.id.okBtn);
        Dialog dialog = showFullScreenViewDialog(context, view, isCancelable);
        messagetv.setText(content);
        okBtn.setText(ok);
        if (TextUtils.isEmpty(cancel)) {
            cancelBtn.setVisibility(View.INVISIBLE);
        } else {
            cancelBtn.setText(cancel);
        }
        view.setOnClickListener(v -> {
            dialog.dismiss();
        });
        cancelBtn.setOnClickListener(v -> {
            listener.onCancel();
            dialog.dismiss();
        });
        okBtn.setOnClickListener(v -> {
            listener.onOk();
            dialog.dismiss();
        });
    }

    public static Dialog showHintDialog(Context context, String content, String ok, final DialogImpl listener) {
        View view = View.inflate(context, R.layout.layout_dialog_hint, null);
        TextView messagetv = view.findViewById(R.id.messageTv);
        Button okBtn = view.findViewById(R.id.okBtn);
        Dialog dialog = showFullScreenViewDialog(context, view, true);
        messagetv.setText(content);
        okBtn.setText(ok);
        view.setOnClickListener(v -> {
            dialog.dismiss();
        });
        okBtn.setOnClickListener(v -> {
            listener.onOk();
            dialog.dismiss();
        });
        return dialog;
    }

    public static Dialog showListMenuDialog(Context context, String title, List<String> menus, final DialogListImpl impl) {
        View view = View.inflate(context, R.layout.layout_dialog_list_menu, null);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        TextView title_tv = view.findViewById(R.id.title_tv);
        TextView cancel = view.findViewById(R.id.cancel);
        BaseQuickAdapter adapter;
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_dialog_list_menu, menus) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, String item) {
                helper.setText(R.id.nameTv, item);
            }
        });
        title_tv.setText(title);
        Dialog dialog = showFullScreenViewDialog(context, view, true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                impl.onItemClick(position);
                dialog.dismiss();
            }
        });
        view.setOnClickListener(v -> {
            dialog.dismiss();
        });
        cancel.setOnClickListener(v -> {
            dialog.dismiss();
        });
        return dialog;
    }

    public interface DialogListImpl {
        void onItemClick(int position);
    }


    public static Dialog showFullScreenViewDialog(Context context, View content, boolean isCancelable) {
        Dialog dialog = new Dialog(context, R.style.transparent_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(isCancelable);         //是否可以取消
        dialog.setContentView(content);
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = UIUtil.getWindowWidth(context); // 宽度
        lp.height = UIUtil.getWindowHeight(context); // 高度
        lp.gravity = Gravity.BOTTOM;
        dialogWindow.setAttributes(lp);
        return dialog;
    }


    public static class DialogImpl {
        public void onOk() {
        }

        public void onCancel() {
        }
    }

}
