package com.shiqkuangsan.mycustomviews.ui.activity;

import android.os.SystemClock;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.shiqkuangsan.mycustomviews.MyApplication;
import com.shiqkuangsan.mycustomviews.R;
import com.shiqkuangsan.mycustomviews.adapter.SimpleRecyclerAdapter;
import com.shiqkuangsan.mycustomviews.base.BaseActivity;
import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;

import org.xutils.DbManager;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import static com.shiqkuangsan.mycustomviews.R.color.swipe_schema_blue;
import static com.shiqkuangsan.mycustomviews.R.color.swipe_schema_green;

/**
 * Created by shiqkuangsan on 2016/10/13.
 *
 * @author shiqkuangsan
 * @summary 学习RecyclerView的界面, 重要是列表item的布局AutoRatioLayout, 实现根据
 * 具体宽度和比例(布局中属性定义)来达到自动计算高度的效果,图片展示使用CardView
 */
public class RecyclerViewActivity extends BaseActivity {

    private Toolbar toolbar;
    @ViewInject(R.id.frame_recycler_main)
    private FrameLayout frame_main;
    private RecyclerView recycler_main;
    private SwipeRefreshLayout swipe_main;

    @Override
    public void initView() {
        // 页面主要承担了两个framlayout,一个是主页面,另一个是drawer
        setContentView(R.layout.activity_recycler);
        x.view().inject(this);

        // ToolBar的基础上支持ActionBar,先在style文件中样式使用NoActionBar
        toolbar = (Toolbar) findViewById(R.id.toolbar_recycler);
        setSupportActionBar(toolbar);

        // ActionBar支持DrawerLayout
        DrawerLayout drawerlayout = (DrawerLayout) findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);
    }

    @Override
    public void initDataAndListener() {
        // 填充主页面数据
        fillContentView();

        // 填充drawer页面数据
        fillDrawerView();
    }

    private void fillContentView() {
        View view = View.inflate(this, R.layout.layout_recycler_main, null);

        // 初始化SwipeRefreshLayout
        swipe_main = (SwipeRefreshLayout) view.findViewById(R.id.swipe_recycler_main);
        swipe_main.setColorSchemeResources(R.color.swipe_schema_red, swipe_schema_blue, R.color.swipe_schema_green);
        swipe_main.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MyApplication.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showToast("刷新成功");
                        swipe_main.setRefreshing(false);
                    }
                }, 1500);
            }
        });

        // 初始化RecyclerView
        recycler_main = (RecyclerView) view.findViewById(R.id.recycler_recycler_main);
        // 设置布局管理者,采用线性布局管理者,竖直排列,不用反转(反转就是数据反序)
        recycler_main.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        SimpleRecyclerAdapter adapter = new SimpleRecyclerAdapter(this, new SimpleRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                MyLogUtil.d("当前点击位置: " + position);
            }

            @Override
            public void onLongClick(int position) {
            }
        });
        recycler_main.setAdapter(adapter);

        // 将填充好的View放到主页面上
        frame_main.addView(view);
    }

    private void fillDrawerView() {
        // 预留
    }

    @Override
    public void processClick(View view) {

    }


}
