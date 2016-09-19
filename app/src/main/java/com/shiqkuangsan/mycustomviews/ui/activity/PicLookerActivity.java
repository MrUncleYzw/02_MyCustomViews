package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.PicGridAdapter;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.photoview.Info;
import com.shiqkuangsan.mycustomviews.photoview.PhotoView;
import com.shiqkuangsan.mycustomviews.photoview.ViewPagerFragment;

import java.util.ArrayList;

/**
 * Created by shiqkuangsan on 2016/9/pic3.
 */

/**
 * 图片查看界面(类似空间发自拍点击查看)
 */
public class PicLookerActivity extends BaseActivity {

    private GridView gv_pics;
    private ArrayList<String> picsList = new ArrayList<>();

    @Override
    public void initView() {
        setContentView(R.layout.activity_piclooker);
        gv_pics = (GridView) findViewById(R.id.gv_pics);
    }

    private ArrayList<Info> imgImageInfos = new ArrayList<>();

    @Override
    public void initDataAndListener() {

        initPics();

        // 设置GridView的适配器
        PicGridAdapter adapter = new PicGridAdapter(picsList, PicLookerActivity.this);
        gv_pics.setAdapter(adapter);
        gv_pics.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imgs", picsList);

                bundle.putParcelable("info", ((PhotoView) view).getInfo());
                bundle.putInt("position", position);
                imgImageInfos.clear();
                for (int i = 0; i < picsList.size(); i++) {
                    if (i < parent.getFirstVisiblePosition() || i > parent.getLastVisiblePosition()) {
                        imgImageInfos.add(new Info());
                    } else {
                        imgImageInfos.add(((PhotoView) parent.getChildAt(i - parent.getFirstVisiblePosition())).getInfo());
                    }
                }
                parent.getChildAt(position);
                bundle.putParcelableArrayList("infos", imgImageInfos);
                getSupportFragmentManager().beginTransaction().replace(Window.ID_ANDROID_CONTENT, ViewPagerFragment.getInstance(bundle), "ViewPagerFragment")
                        .addToBackStack(null).commit();
            }
        });
    }

    /**
     * 加载GridView的几张图片
     */
    private void initPics() {
        picsList.add("http://g.hiphotos.baidu.com/zhidao/pic/item/80cb39dbb6fd526616bf1d96a918972bd507369f.jpg");
        picsList.add("http://i1.hdslb.com/video/44/44aaafda3a3b58345d6788990b7cffac.jpg");
        picsList.add("http://img3.duitang.com/uploads/item/201509/23/20150923142544_ANiv8.jpeg");
        picsList.add("http://img3.duitang.com/uploads/item/201307/16/20130716192747_xSzXX.jpeg");
        picsList.add("http://c.hiphotos.baidu.com/zhidao/pic/item/a8773912b31bb0510a0a938e357adab44aede06e.jpg");
    }

    @Override
    public void processClick(View view) {

    }
}
