package com.zjw.basicandroid.helper.imageHelper;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.zjw.basicandroid.R;
import com.zjw.basicandroid.basic.MainApplication;
import com.zjw.basicandroid.utils.UIUtil;


/**
 * Created by Frank on 2017/3/31.
 * Glide 4.0 正式版
 */

public enum GlideHelper {
    INSTANCE;


    //备用 后台头像地址不改变（只做本人头像显示）
    public void loadAvatar(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .circleCrop();   //4.0版本圆形
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    //加载毛玻璃效果
    public static void loadBlurImage(ImageView imageView,String url){
        Glide.with(MainApplication.getAppContext()).load(url).placeholder(R.mipmap.rectangle_default_image)
                .thumbnail(0.1f).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate()
                .apply(RequestOptions.bitmapTransform(new GlideBlurTransformer(MainApplication.getAppContext(), 100, 1)))//模糊效果
                .into(imageView);
    }


    /*-------------------------- url地址图片 ---------------------------------*/
    /**
     * 加载url图片
     *
     * @param imageView
     * @param url
     */
    public void loadImage(ImageView imageView, String url) {
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(new RequestOptions().placeholder(R.mipmap.rectangle_default_image).centerCrop())
                .into(imageView);
    }


    /**
     * 根据设置尺寸加载url
     * @param imageView
     * @param url
     * @param width
     * @param height
     */
    public void loadImage(ImageView imageView, String url, int width, int height) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.rectangle_default_image)
                .centerCrop()
                .override(width,height);
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 带尺寸切角
     * @param imageView
     * @param url
     * @param width
     * @param height
     * @param dp
     */
    public void loadImage(ImageView imageView, String url, int width, int height, int dp) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.rectangle_default_image)
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(dp)))
                .override(width,height);
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 不带尺寸切角
     * @param imageView
     * @param url
     * @param dp
     */
    public void loadImage(ImageView imageView, String url, int dp) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.rectangle_default_image)
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(dp)));
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView
     * @param url
     */
    public void loadCircleImage(ImageView imageView, String url) {
        RequestOptions options = new RequestOptions()
                .placeholder(R.mipmap.oval_default_image)
                .centerCrop()
                .circleCrop();   //4.0版本圆形
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }
    /**
     * 带尺寸加载圆形图片
     *
     * @param imageView
     * @param url
     */
    public void loadCircleImage(ImageView imageView, String url, int width, int height) {
        RequestOptions options= new RequestOptions()
                .placeholder(R.mipmap.oval_default_image)
                .centerCrop()
                .circleCrop()
                .override(width,height);
        Glide.with(MainApplication.getAppContext())
                .load(url)
                .apply(options)
                .into(imageView);
    }


    /*-------------------------- 资源图片 ---------------------------------*/
    /**
     * 加载url图片
     *
     * @param imageView
     * @param resIcon
     */
    public void loadImage(ImageView imageView, int resIcon) {
        Glide.with(MainApplication.getAppContext())
                .load(resIcon)
                .apply(new RequestOptions().centerCrop())
                .into(imageView);
    }

    /**
     * 根据设置尺寸加载url
     * @param imageView
     * @param resIcon
     * @param width
     * @param height
     */
    public void loadImage(ImageView imageView, int resIcon, int width, int height) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .override(width,height);
        Glide.with(MainApplication.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }

    /**
     * 带尺寸切角
     * @param imageView
     * @param resIcon
     * @param width
     * @param height
     * @param dp
     */
    public void loadImage(ImageView imageView, int resIcon, int width, int height, int dp) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(dp)))
                .override(width,height);
        Glide.with(MainApplication.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }

    /**
     * 不带尺寸切角
     * @param imageView
     * @param resIcon
     * @param dp
     */
    public void loadImage(ImageView imageView, int resIcon, int dp) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .transform(new RoundedCorners(UIUtil.dp2px(dp)));
        Glide.with(MainApplication.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param imageView
     * @param resIcon
     */
    public void loadCircleImage(ImageView imageView, int resIcon) {
        RequestOptions options = new RequestOptions()
                .centerCrop()
                .circleCrop();   //4.0版本圆形
        Glide.with(MainApplication.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }
    /**
     * 带尺寸加载圆形图片
     *
     * @param imageView
     * @param resIcon
     */
    public void loadCircleImage(ImageView imageView, int resIcon, int width, int height) {
        RequestOptions options
                = new RequestOptions()
                .centerCrop()
                .circleCrop()
                .override(width,height);
        Glide.with(MainApplication.getAppContext())
                .load(resIcon)
                .apply(options)
                .into(imageView);
    }




    //验证图片地址
   /* public GlideUrl getGlideUrl(String url) {
        //Authorization 请求头信息
        LazyHeaders headers = new LazyHeaders.Builder()
                .addHeader("Authorization", "http://cdn.dotasell.com")
                .build();
        return new GlideUrl(url, headers);
    }*/


}
