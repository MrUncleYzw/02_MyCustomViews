package com.shiqkuangsan.mycustomviews.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.ChosePicUtil;

import java.io.File;

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
    private ImageView iv_image;

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
                ChosePicUtil.startActivityFor(ChosePicUtil.MATCHING_CODE_GALLERY, this);
                break;

            // 拍照获取
            case R.id.btn_camera_chose:
                ChosePicUtil.startActivityFor(ChosePicUtil.MATCHING_CODE_CAMERA, this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 使用ChosePicUtil   方式1
        Bitmap bitmap = ChosePicUtil.getBitmapFromResult(requestCode, resultCode, data, this, false, true);
        if (bitmap != null)
            iv_image.setImageBitmap(bitmap);
//        String path = ChosePicUtil.getPathFromResult(requestCode, resultCode, data, this);
//        iv_image.setImageBitmap(BitmapFactory.decodeFile(path));
//        ChosePicUtil.deleteTemp();
        super.onActivityResult(requestCode, resultCode, data);
    }


}
