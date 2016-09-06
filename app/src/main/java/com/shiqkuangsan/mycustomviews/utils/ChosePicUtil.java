package com.shiqkuangsan.mycustomviews.utils;

/**
 * Created by dell on 2016/9/6.
 */

import android.app.Activity;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.ui.PicChoserActivity;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * 选择图片的工具类
 * 1.在你的两个选择按钮调用startActivityFor方法传参选择获取方式
 */
public class ChosePicUtil {

    /**
     * 从图库获取的匹配码
     */
    public static final int MATCHING_CODE_GALLERY = 0;

    /**
     * 从相机获取的匹配码
     */
    public static final int MATCHING_CODE_CAMERA = 1;

    /**
     * 从图库获取的请求码
     */
    public static final int REQUEST_CODE_GALLERY = 100;

    /**
     * 从相机获取的请求码
     */
    public static final int REQUEST_CODE_CAMERA = 200;

    /**
     * 从相机获取的请求码
     */
    public static final int REQUEST_CODE_CROP = 300;

    /**
     * 临时存储的文件对象
     */
    public static File tempFile;


    /**
     * 根据不同匹配码确定是从图库选择还是相机获取,并启动相应activity
     *
     * @param MATCHING_CODE 匹配码   MATCHING_CODE_GALLERY - 图库获取, MATCHING_CODE_CAMERA - 相机获取
     */
    public static void startActivityFor(int MATCHING_CODE, Activity activity) {
        switch (MATCHING_CODE) {
            case MATCHING_CODE_GALLERY:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                activity.startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY);
                break;

            case MATCHING_CODE_CAMERA:
                // 激活相机并判断存储卡是否可以用
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
                    if (tempFile != null)
                        tempFile = null;
                    // 创建资源标识,设置额外信息
                    tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                    Uri uri = Uri.fromFile(tempFile);
                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    activity.startActivityForResult(cameraIntent, REQUEST_CODE_CAMERA);
                } else
                    Toast.makeText(activity, "未检测到储存卡", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 根据返回的结果进行不同的解析
     *
     * @param requestCode 请求码
     * @param resultCode  结果码
     * @param data        返回数据(里边封装这一个uri)
     * @param activity    当前activity
     * @param needCrop    获取完之后是否需要裁剪? true - 进入裁剪界面
     * @return Bitmap对象, null - 解析失败
     */
    public static Bitmap getBitmapFromResult(int requestCode, int resultCode, Intent data, Activity activity, boolean needCrop) {
        if (resultCode != Activity.RESULT_OK)
            return null;

        Bitmap bitmap = null;
        ContentResolver resolver = activity.getContentResolver();
        switch (requestCode) {
            // 从图库返回
            case REQUEST_CODE_GALLERY:
                if (data != null) {
                    // 根据返回的数据利用内容解决者解析出bitmap对象
                    Uri uri = data.getData();
                    if (needCrop)
                        crop(uri, activity);

                    else
                        try {
                            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                }
                break;

            // 从相机返回
            case REQUEST_CODE_CAMERA:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if (needCrop)
                        crop(Uri.fromFile(tempFile), activity);

                    else
                        try {
                            bitmap = BitmapFactory.decodeStream(resolver.openInputStream(Uri.fromFile(tempFile)));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }

                }
                break;

            // 从剪切界面返回
            case REQUEST_CODE_CROP:
                if (data != null) {
                    bitmap = data.getParcelableExtra("data");
                }
                try {
                    // 将临时文件删除
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return bitmap;

    }


    /**
     * 剪切图片,可以自定义剪切参数
     *
     * @param uri 数据标识
     */
    private static void crop(Uri uri, Activity activity) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, REQUEST_CODE_CROP);
    }


    /**
     * @param requestCode
     * @param resultCode
     * @param data
     * @param activity
     * @param needCrop
     * @return
     */
    public static String getStringFromResult(int requestCode, int resultCode, Intent data, Activity activity, boolean needCrop) {

        if (resultCode != Activity.RESULT_OK)
            return null;

        String imgPath = null;
        switch (requestCode) {
            // 从图库返回
            case REQUEST_CODE_GALLERY:
                if (data != null) {
                    // 根据返回的数据利用内容解决者解析出bitmap对象
                    Uri uri = data.getData();
                    if (needCrop)
                        crop(uri, activity);
                    else
                        imgPath = setImagePath(uri, activity);
                }
                break;

            // 从相机返回
            case REQUEST_CODE_CAMERA:
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    if (needCrop)
                        crop(Uri.fromFile(tempFile), activity);
                    else
                        imgPath = setImagePath(Uri.fromFile(tempFile), activity);
                }
                break;

            // 从剪切界面返回
            case REQUEST_CODE_CROP:
                if (data != null) {
                    Bundle extras = data.getExtras();
                }
                try {
                    // 将临时文件删除
                    tempFile.delete();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        return imgPath;

    }

    public static String setImagePath(Uri uri, Activity activity) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = activity.managedQuery(uri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
}
