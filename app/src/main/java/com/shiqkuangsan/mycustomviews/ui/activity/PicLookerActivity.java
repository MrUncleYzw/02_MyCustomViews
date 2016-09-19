package com.shiqkuangsan.mycustomviews.ui.activity;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.PicGridAdapter;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shiqkuangsan on 2016/9/pic3.
 */

/**
 * 图片查看界面(类似空间发自拍点击查看)
 */
public class PicLookerActivity extends BaseActivity {

    private GridView gv_pics;
    private List<Drawable> pics = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_piclooker);
        gv_pics = (GridView) findViewById(R.id.gv_pics);
    }

    @Override
    public void initDataAndListener() {

        initPics();

        PicGridAdapter adapter = new PicGridAdapter(pics, PicLookerActivity.this);
        gv_pics.setAdapter(adapter);
        gv_pics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    /**
     * 加载GridView的几张图片
     */
    private void initPics() {
        // TODO: 2016/9/19 改成网络图片地址,使用百度的
        Drawable drawable1 = getResources().getDrawable(R.drawable.pic1);
        pics.add(drawable1);
        Drawable drawable2 = getResources().getDrawable(R.drawable.pic2);
        pics.add(drawable2);
        Drawable drawable3 = getResources().getDrawable(R.drawable.pic3);
        pics.add(drawable3);
        Drawable drawable4 = getResources().getDrawable(R.drawable.pic4);
        pics.add(drawable4);
        Drawable drawable5 = getResources().getDrawable(R.drawable.pic5);
        pics.add(drawable5);
    }

    @Override
    public void processClick(View view) {

    }
}
