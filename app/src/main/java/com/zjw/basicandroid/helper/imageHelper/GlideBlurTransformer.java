package com.zjw.basicandroid.helper.imageHelper;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.renderscript.RSRuntimeException;

import androidx.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;

import java.security.MessageDigest;


public class GlideBlurTransformer extends BitmapTransformation {
    private int radius;
    private int sampling;
    private Context context;
 
    public GlideBlurTransformer(Context context, int radius, int sampling) {
        this.context = context;
        this.radius = radius;
        this.sampling = sampling;
    }
 
    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerCrop(pool, toTransform, outWidth, outHeight);
        return blurCrop(pool,bitmap);
    }
 
    private Bitmap blurCrop(BitmapPool pool, Bitmap source) {
        if (source == null) return null;
        int width = source.getWidth();
        int height = source.getHeight();
        int scaledWidth = width / sampling;
        int scaledHeight = height / sampling;
 
        Bitmap bitmap = pool.get(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888);
 
        Canvas canvas = new Canvas(bitmap);
        canvas.scale(1 / (float) sampling, 1 / (float) sampling);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(source, 0, 0, paint);
 
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            try {
                bitmap = RSBlur.blur(context, bitmap, radius);
            } catch (RSRuntimeException e) {
                bitmap = FastBlur.doBlur(bitmap, radius, true);
            }
        } else {
            bitmap = FastBlur.doBlur(bitmap, radius, true);
        }
 
        return bitmap;
    }
 

    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {

    }
}
 