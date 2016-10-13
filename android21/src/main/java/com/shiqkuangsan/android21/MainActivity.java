package com.shiqkuangsan.android21;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 5.0新特性界面
 */
public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerlayout;
    private FrameLayout frame_content;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.ll_main_root).setBackgroundColor(0xffffd4d4);
        }

        initView();
    }

    private void initView() {

        // ToolBar的基础上支持ActionBar,先在style文件中样式使用NoActionBar,然后代码手动设置支持ActionBar
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        // ActionBar显示返回键,添加了沉浸式NoActionBar,这里返回null, 设置SupportActionBar但是下面使用DrawerLayout
        // ActionBar的返回键又没用了
//        actionBar = getSupportActionBar();
//        if (actionBar != null)
//            actionBar.setDisplayHomeAsUpEnabled(true);

        // ActionBar支持DrawerLayout
        drawerlayout = (DrawerLayout) findViewById(R.id.main_drawerlayout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        toggle.syncState();
        drawerlayout.addDrawerListener(toggle);

        // 往主页面添加数据,主要用于显示新组件RecyclerView
        frame_content = (FrameLayout) findViewById(R.id.main_content);
        View view_content = View.inflate(this, R.layout.layout_main_content, null);

        // 找到RecyclerView,设置setLayoutManager(),设置适配器
//        recyclerview = (RecyclerView) view_content.findViewById(R.id.content_recyclerview);
//        recyclerview.setLayoutManager(new LinearLayoutManager(this));
//        recyclerview.setAdapter(new MyRecyclerAdapter());

        ListView lv_content = (ListView) view_content.findViewById(R.id.lv_content);
        lv_content.setAdapter(new MyAdapter());

        frame_content.addView(view_content);
    }

    public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return datas.length;
        }

        @Override
        public Object getItem(int position) {
            return datas[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final View view = View.inflate(MainActivity.this, R.layout.item_recycler, null);
            return view;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // 加载菜单
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case R.id.menu_listh:
//                recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
//                recyclerview.setAdapter(new MyRecyclerAdapter());
//                break;
//
//            case R.id.menu_grid:
//                recyclerview.setLayoutManager(new GridLayoutManager(this, 2));
//                recyclerview.setAdapter(new MyRecyclerAdapter());
//                break;
//
//            case R.id.menu_gridh:
//                recyclerview.setLayoutManager(new GridLayoutManager(this, 2, GridLayoutManager.HORIZONTAL, false));
//                recyclerview.setAdapter(new MyRecyclerAdapter());
//                break;
//
//            case R.id.menu_falls:
//                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
//                recyclerview.setAdapter(new MyRecyclerAdapter());
//                break;
//
//            case R.id.menu_fallsh:
//                recyclerview.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
//                recyclerview.setAdapter(new MyRecyclerAdapter());
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    String[] datas = {"数据1", "数据2", "数据3", "数据4", "数据5", "数据6", "数据7", "数据8", "数据9", "数据10",
            "数据11", "数据12", "数据13", "数据14", "数据15", "数据16", "数据17", "数据18", "数据19", "数据20"
    };

    public class MyRecyclerAdapter extends RecyclerView.Adapter<MyRecyclerAdapter.MyViewHolder> {

        // 绘制View
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = View.inflate(MainActivity.this, R.layout.item_recycler, null);
            return new MyViewHolder(view);
        }

        // 设置数据
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv_recycler.setText(datas[position]);
        }

        @Override
        public int getItemCount() {
            return datas.length;
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            public final ImageView iv_recycler;
            public final TextView tv_recycler;

            public MyViewHolder(View itemView) {
                super(itemView);
                iv_recycler = (ImageView) itemView.findViewById(R.id.iv_recycler);
                tv_recycler = (TextView) itemView.findViewById(R.id.tv_recycler);
            }
        }
    }
}
