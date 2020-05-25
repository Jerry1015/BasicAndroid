package com.zjw.basicandroid.helper.imageHelper;

import android.app.Activity;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.zjw.basicandroid.R;
import com.zjw.basicandroid.utils.DialogUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2018/2/2 0002.
 * 封装选择头像 图片单选 多选 图片多张上传 单张上传
 * 图片选择后要在 onActivityResult 返回 请看示例
 @Override
 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
 super.onActivityResult(requestCode, resultCode, data);
 if (resultCode == RESULT_OK) {
 switch (requestCode) {
 case PictureConfig.CHOOSE_REQUEST:
 selectionMedia = PictureSelector.obtainMultipleResult(data);
 mAdapter.clear();
 mAdapter.addAll(selectionMedia);
 recyclerView.setVisibility(View.VISIBLE);
 break;
 }
 }
 }
 */

public class ImageHelper {

    public static void browseImages(Activity activity, int position , List<LocalMedia> medias){
        PictureSelector.create(activity).themeStyle(R.style.picture_default_style).openExternalPreview(position, medias);
    }
    public static void browseImages(Fragment fragment, int position , List<LocalMedia> medias){
        PictureSelector.create(fragment).themeStyle(R.style.picture_default_style).openExternalPreview(position, medias);
    }

    //选择图片 多张 拍照+图片 selectionMedia选中的图片
    public static void showImagesChoose(Activity activity, List<LocalMedia> selectionMedia){
        DialogUtils.showListMenuDialog(activity, "选择图片", Arrays.asList("拍照", "相册"), new DialogUtils.DialogListImpl() {
            @Override
            public void onItemClick(int position) {
                if(position==0){
                    PictureSelector.create(activity)
                            .openCamera(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .isCompress(true)
                            .isEnableCrop(true)
                            .withAspectRatio(1,1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }else{
                    PictureSelector.create(activity)
                            .openGallery(PictureMimeType.ofImage())
                            .selectionMode(PictureConfig.MULTIPLE)
                            .imageEngine(GlideEngine.createGlideEngine())
                            .isCompress(true)
                            .isEnableCrop(false)
                            .selectionData(selectionMedia)
                            .withAspectRatio(1, 1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });
    }

    //选择图片 多张 拍照+图片 selectionMedia选中的图片
    public static void showImagesChoose(Fragment fragment, List<LocalMedia> selectionMedia){

        DialogUtils.showListMenuDialog(fragment.getContext(), "选择图片", Arrays.asList("拍照", "相册"), new DialogUtils.DialogListImpl() {
            @Override
            public void onItemClick(int position) {
                if(position==0){
                    PictureSelector.create(fragment)
                            .openCamera(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .isCompress(true)
                            .isEnableCrop(true)
                            .withAspectRatio(1,1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }else{
                    PictureSelector.create(fragment)
                            .openGallery(PictureMimeType.ofImage())
                            .selectionMode(PictureConfig.MULTIPLE)
                            .imageEngine(GlideEngine.createGlideEngine())
                            .isCompress(true)
                            .isEnableCrop(false)
                            .selectionData(selectionMedia)
                            .withAspectRatio(1, 1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });
    }

    //选择图片 单张
    public static void showImageChoose(Activity activity){
        showImageChoose(activity,false);
    }
    public static void showImageChoose(Activity activity, boolean isEnableCrop){
        DialogUtils.showListMenuDialog(activity, "选择图片", Arrays.asList("拍照", "相册"), new DialogUtils.DialogListImpl() {
            @Override
            public void onItemClick(int position) {
                if(position==0){
                    PictureSelector.create(activity)
                            .openCamera(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .isCompress(true)
                            .isEnableCrop(isEnableCrop)
                            .withAspectRatio(1, 1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }else{
                    PictureSelector.create(activity)
                            .openGallery(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .isCompress(true)
                            .isEnableCrop(isEnableCrop)
                            .withAspectRatio(1, 1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });
    }

    public static void showImageChoose(Fragment fragment){
        showImageChoose(fragment,false);
    }
    /**
     * 选择图片 单张
     * @param fragment
     */
    public static void showImageChoose(Fragment fragment, boolean isEnableCrop){
        DialogUtils.showListMenuDialog(fragment.getContext(), "选择图片", Arrays.asList("拍照", "相册"), new DialogUtils.DialogListImpl() {
            @Override
            public void onItemClick(int position) {
                if(position==0){
                    PictureSelector.create(fragment)
                            .openCamera(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine())
                            .isCompress(true)
                            .isEnableCrop(isEnableCrop)
                            .withAspectRatio(1, 1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }else{
                    PictureSelector.create(fragment)
                            .openGallery(PictureMimeType.ofImage())
                            .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                            .isCompress(true)
                            .isEnableCrop(isEnableCrop)
                            .withAspectRatio(1, 1)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                }
            }
        });
    }







    //上传单张
//    public static void upLoadImage(String imagePath,OnUpLoadImageCallBack callBack){
//        NetworkManager.INSTANCE.upLoadFile(Apis.uploadImage, "cover_image",new File(imagePath), new OnUpLoadCallBack() {
//            @Override
//            public void onOk(String response) {
//                String file_path = GsonUtils.getStringFromJSON(response, "file_path");
//                callBack.onSuccess(file_path);
//            }
//
//            @Override
//            public void onError(int code, String errorMessage) {
//                callBack.onFail(errorMessage);
////                hideProgress();
////                showHintDialog("上传图片失败：" + errorMessage);
//            }
//
//            @Override
//            public void upProgress(int progress) {
//
//            }
//        });
//    }
//
//    //上传多张
//    public static void upLoadImages(List<LocalMedia> images, OnUpLoadImagesCallBack callBack){
//        List<String> upLoadSuccessImages = new ArrayList<>();
//        upLoad(upLoadSuccessImages,images,images.get(0).getCompressPath(),0,callBack);
//    }
//
//    private static void upLoad(List<String> upLoadSuccessImages,List<LocalMedia> images,String file, int position,OnUpLoadImagesCallBack callBack) {
////        showProgress("正在上传" + (position + 1) + "/" + selectionMedia.size());
//        NetworkManager.INSTANCE.upLoadFile(Apis.uploadImage, "cover_image",new File(file), new OnUpLoadCallBack() {
//            @Override
//            public void onOk(String response) {
//                upLoadSuccessImages.add(GsonUtils.getStringFromJSON(response,"file_path"));
//                int i = position + 1;
//                if (i == images.size()) {
//                    callBack.onSuccess(upLoadSuccessImages);
//                } else {
//                    upLoad(upLoadSuccessImages,images,images.get(i).getCompressPath(),i,callBack);
//                }
//            }
//
//            @Override
//            public void onError(int code, String errorMessage) {
//                callBack.onFail(errorMessage);
////                hideProgress();
////                showHintDialog("上传图片失败：" + errorMessage);
//            }
//
//            @Override
//            public void upProgress(int progress) {
//
//            }
//        });
//    }
//
//    public interface OnUpLoadImagesCallBack{
//        void onSuccess(List<String> images);
//        void onFail(String error);
//    }
//
//    public interface OnUpLoadImageCallBack{
//        void onSuccess(String images);
//        void onFail(String error);
//    }

}
