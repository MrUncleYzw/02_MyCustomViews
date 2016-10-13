package com.shiqkuangsan.mycustomviews.ui.activity;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;

/**
 * Created by shiqkuangsan on 2016/10/13.
 *
 * @author shiqkuangsan
 * @summary 学习RecyclerView的界面, 重要是列表item的布局AutoRatioLayout, 实现根据
 * 具体宽度和比例(布局中属性定义)来达到自动计算高度的效果,图片展示使用CardView
 */
public class RecyclerViewActivity extends BaseActivity {

    private Toolbar toolbar;

    @Override
    public void initView() {
        setContentView(R.layout.activity_recycler);

        init();
    }

    private void init() {
        // ToolBar的基础上支持ActionBar,先在style文件中样式使用NoActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar_recycler);
        setSupportActionBar(toolbar);

        // ActionBar支持DrawerLayout
        DrawerLayout drawerlayout = (DrawerLayout) findViewById(R.id.drawer_recycler);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);

        // 往主页面添加数据,主要用于显示新组件RecyclerView/CardView
        FrameLayout frame_main = (FrameLayout) findViewById(R.id.frame_recycler_main);

//        frame_main.addView(view_content);
    }


    @Override
    public void initDataAndListener() {

    }

    @Override
    public void processClick(View view) {

    }
}
