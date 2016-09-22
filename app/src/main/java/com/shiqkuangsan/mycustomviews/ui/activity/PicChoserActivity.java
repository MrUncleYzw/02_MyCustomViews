package com.shiqkuangsan.mycustomviews.ui.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.ChosePicUtil;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

import static android.R.attr.start;

/**
 * Created by dell on 2016/9/6.
 */

/**
 * 图片选择演示界面,其中有个按钮使用三方类库可以选图演示发说说
 */
public class PicChoserActivity extends BaseActivity {

    private Button btn_gallery_chose;
    private Button btn_camera_chose;
    /**
     * 临时file对象,记录图片路径
     */
    private ImageView iv_image;
    private Button btn_lib_chose;

    @Override
    public void initView() {
        setContentView(R.layout.activity_picchoser);

        btn_gallery_chose = (Button) findViewById(R.id.btn_gallery_chose);
        btn_camera_chose = (Button) findViewById(R.id.btn_camera_chose);
        btn_lib_chose = (Button) findViewById(R.id.btn_lib_chose);
        iv_image = (ImageView) findViewById(R.id.iv_image);
    }

    @Override
    public void initDataAndListener() {
        btn_gallery_chose.setOnClickListener(this);
        btn_camera_chose.setOnClickListener(this);
        btn_lib_chose.setOnClickListener(this);
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

            // 使用人家的类库
            case R.id.btn_lib_chose:
                startActivity(new Intent(this, MomentListActivity.class));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // 使用ChosePicUtil   方式1
        Bitmap bitmap = ChosePicUtil.getBitmapFromResult(requestCode, resultCode, data, this, false, true);
        if (bitmap != null)
            iv_image.setImageBitmap(bitmap);

        // 使用ChosePicUtil   方式2
//        String path = ChosePicUtil.getPathFromResult(requestCode, resultCode, data, this);
//        iv_image.setImageBitmap(BitmapFactory.decodeFile(path));
//        ChosePicUtil.deleteTemp();
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        ChosePicUtil.onActRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
