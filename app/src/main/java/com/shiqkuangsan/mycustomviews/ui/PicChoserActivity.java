package com.shiqkuangsan.mycustomviews.ui;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.ChosePicUtil;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by dell on 2016/9/6.
 */

/**
 * 图片选择演示界面
 */
public class PicChoserActivity extends BaseActivity {

    private static final int PHOTO_REQUEST_GALLERY = 100;
    private static final int PHOTO_REQUEST_CAREMA = 101;
    private static final int PHOTO_REQUEST_CUT = 102;

    private Button btn_gallery_chose;
    private Button btn_camera_chose;
    /**
     * 临时file对象,记录图片路径
     */
    private File tempFile;
    private ImageView iv_image;
    private Bitmap bitmap;

    @Override
    public void initView() {
        setContentView(R.layout.activity_picchoser);

        btn_gallery_chose = (Button) findViewById(R.id.btn_gallery_chose);
        btn_camera_chose = (Button) findViewById(R.id.btn_camera_chose);
        iv_image = (ImageView) findViewById(R.id.iv_image);
    }

    @Override
    public void initDataAndListener() {
        btn_gallery_chose.setOnClickListener(this);
        btn_camera_chose.setOnClickListener(this);
    }


    @Override
    public void processClick(View view) {
        switch (view.getId()) {
            // 从相册获取
            case R.id.btn_gallery_chose:
                // 激活系统图库，选择一张图片
//                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
//                galleryIntent.setType("image/*");
//                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
//                startActivityForResult(galleryIntent, PHOTO_REQUEST_GALLERY);
                ChosePicUtil.startActivityFor(ChosePicUtil.MATCHING_CODE_GALLERY, this);
                break;

            // 拍照获取
            case R.id.btn_camera_chose:
                // 激活相机
//                Intent cameraIntent = new Intent("android.media.action.IMAGE_CAPTURE");
//                // 判断存储卡是否可以用，可用进行存储
//                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                    tempFile = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
//                    // 从文件中创建uri
//                    Uri uri = Uri.fromFile(tempFile);
//                    cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                   // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
//                  startActivityForResult(cameraIntent, PHOTO_REQUEST_CAREMA);
//                }
                ChosePicUtil.startActivityFor(ChosePicUtil.MATCHING_CODE_CAMERA, this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // 从相册返回的数据
//        if (requestCode == PHOTO_REQUEST_GALLERY) {
//            if (data != null) {
//                // 需要裁剪嘛?
////                 crop(uri);
//
//                Uri uri = data.getData();
//                // 得到图片的全路径
//                ContentResolver resolver = getContentResolver();
//                try {
//                    if (bitmap != null)
//                        bitmap.recycle();
//                    bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
//                    if (bitmap != null)
//                        iv_image.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            // 从相机返回的数据
//        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
//            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
//                // 裁剪
//                // crop(Uri.fromFile(tempFile));
//
//                Uri uri = Uri.fromFile(tempFile);
//                ContentResolver resolver = getContentResolver();
//                try {
//                    if (bitmap != null)
//                        bitmap.recycle();
//                    bitmap = BitmapFactory.decodeStream(resolver.openInputStream(uri));
//                    if (bitmap != null)
//                        iv_image.setImageBitmap(bitmap);
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                Toast.makeText(PicChoserActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
//            }
//
//            // 从剪切图片返回的数据(暂时不需要剪切)
//        } else if (requestCode == PHOTO_REQUEST_CUT) {
//            if (data != null) {
//                if (bitmap != null)
//                    bitmap.recycle();
//                bitmap = data.getParcelableExtra("data");
//                if (bitmap != null)
//                    iv_image.setImageBitmap(bitmap);
//            }
//            try {
//                // 将临时文件删除
//                tempFile.delete();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }

        // 使用ChosePicUtil
//        Bitmap bitmap = ChosePicUtil.getBitmapFromResult(requestCode, resultCode, data, this, false);
//        if (bitmap != null)
//            iv_image.setImageBitmap(bitmap);
        ChosePicUtil.getStringFromResult(requestCode, resultCode, data, this, true);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
