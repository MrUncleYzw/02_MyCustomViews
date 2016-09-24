package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.bean.Province;
import com.shiqkuangsan.mycustomviews.constant.Constant;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.MyxUtil;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Random;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by shiqkuangsan on 2016/9/20.
 */

/**
 * 学习使用xUtils的界面
 */
public class XUtilsActivity extends AppCompatActivity {

    // View注解,需要在Activity的onCreate()方法中注入
    @ViewInject(R.id.btn_xutils_sendget)
    Button btn_request_get;
    @ViewInject(R.id.btn_xutils_sendpost)
    Button btn_request_post;
    @ViewInject(R.id.btn_xutils_upload)
    Button btn_xutils_upload;
    @ViewInject(R.id.btn_xutils_download)
    Button btn_xutils_download;
    @ViewInject(R.id.tv_xutils_progress)
    TextView tv_xutils_progress;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xutils);
        x.view().inject(this);
    }

    // 事件注解,默认type为OnClickListener,你可以自定义
//    @Event(value = {R.id.btn_xutils_sendget, R.id.btn_xutils_sendpost}, type = View.OnClickListener.class)
    @Event(value = {R.id.btn_xutils_sendget, R.id.btn_xutils_sendpost, R.id.btn_xutils_upload, R.id.btn_xutils_download})
    private void processOnclick(View view) {
        switch (view.getId()) {
            // xUtils发送get请求
            case R.id.btn_xutils_sendget:
                sendGetRequest();
                break;

            case R.id.btn_xutils_sendpost:
                showToast("和get请求使用起来都一样");
                break;

            case R.id.btn_xutils_upload:
                upploadFile();
                break;

            case R.id.btn_xutils_download:
                downloadFile();
                break;

        }
    }


    /**
     * 发送get请求
     */
    private void sendGetRequest() {
        MyxUtil.sendGet(Constant.mlnx_province_url, null, null, null, new MyxUtil.SimpleRequstCallBack<List<Province>>() {
            @Override
            public void onSuccess(List<Province> result) {
                if (result != null && result.size() > 0)
                    showToast(result.get(new Random().nextInt(result.size() - 1)).toString());
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                super.onError(ex, isOnCallback);
            }
        });

    }

    /**
     * 上传文件
     */
    private void upploadFile() {
        showToast("没有接口,没法测试");
    }

    /**
     * 下载文件
     */
    private void downloadFile() {
        String path = Environment.getExternalStorageDirectory() + "/kuangsan.jpg";
        File file = new File(path);
        if (file.exists())
            if (file.delete())
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                    MyxUtil.downloadFile(Constant.download_file_url, path, new MyxUtil.SimpleFileCallBack<File>() {
                        @Override
                        public void onStarted() {
                            tv_xutils_progress.setVisibility(View.VISIBLE);
                            tv_xutils_progress.setText("0%");
                        }

                        @Override
                        public void onLoading(long total, long current, boolean isDownloading) {
                            super.onLoading(total, current, isDownloading);
                            tv_xutils_progress.setText(current / total * 100 + "%");
                        }

                        @Override
                        public void onSuccess(File result) {
                            MyLogUtil.d("onSuccess");
                            showToast("下载完成");
                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            MyLogUtil.d("onError");
                            showToast("下载失败");
                        }

                    });
                else
                    showToast("内存设备异常");
    }

    protected Toast toast;

    protected void showToast(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        toast = Toast.makeText(this, msg + "", Toast.LENGTH_SHORT);
        toast.show();
    }

}
