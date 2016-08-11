package com.shiqkuangsan.qqspecialeffects;

import android.graphics.Canvas;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.shiqkuangsan.qqspecialeffects.custom.MyListView;
import com.shiqkuangsan.qqspecialeffects.custom.SwipeLayout;
import com.shiqkuangsan.qqspecialeffects.custom.SwipeLayoutManager;
import com.shiqkuangsan.qqspecialeffects.utils.Dp2Px;

import java.util.ArrayList;

/**
 * QQ特效之侧滑菜单、删除条目、ListView头部放大
 */
public class MainActivity extends SlidingFragmentActivity implements View.OnClickListener {

    private SlidingMenu menu;
    private MyListView lv_main;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.layout_leftmenu);

        initData();
        initSlidingMenu();
        initView();

    }

    ArrayList<String> dataList = new ArrayList<>();

    private void initData() {
        for (int i = 0; i < 30; i++) {
            String s = "我是条目" + i;
            dataList.add(s);
        }
    }


    private void initView() {
        ImageView iv_toggle = (ImageView) findViewById(R.id.iv_titlebar_menu);
        iv_toggle.setOnClickListener(this);

        lv_main = (MyListView) findViewById(R.id.lv_main);
        MyAdapter adapter = new MyAdapter();
        View headerView = View.inflate(this, R.layout.layout_headerview, null);
        ImageView iv_header = (ImageView) headerView.findViewById(R.id.iv_header);
        lv_main.addHeaderView(headerView);
        lv_main.setmImageView(iv_header);
        // 设置滑动到顶部继续滑没有蓝色阴影
        lv_main.setOverScrollMode(ListView.OVER_SCROLL_NEVER);
        lv_main.setAdapter(adapter);
        lv_main.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL)
                    SwipeLayoutManager.getInstance().closeCurrentLayout();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initSlidingMenu() {
        final TextView tv1 = (TextView) findViewById(R.id.tv1);
        tv1.setText("23333");
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv1.setText("sb");
                System.out.println("点你麻痹");
            }
        });
        menu = getSlidingMenu();
        menu.setBehindOffset(Dp2Px.dip2qx(this, 100));
        // 可点击,并且主界面重中间拉就可以拉出侧滑
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        menu.setMode(SlidingMenu.LEFT);
        menu.setBehindScrollScale(0.3f);
        menu.setFadeEnabled(true);
        menu.setFadeDegree(0.6f);
        menu.setBackgroundResource(R.mipmap.daofeng);
        menu.setBehindCanvasTransformer(new SlidingMenu.CanvasTransformer() {
            @Override
            public void transformCanvas(Canvas canvas, float percentOpen) {
                float scale = (float) (percentOpen * 0.25 + 0.75);
                canvas.scale(scale, scale, canvas.getWidth() / 2, canvas.getHeight() / 2);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_titlebar_menu:
                menu.toggle();
                break;
        }
    }


    class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return dataList.size();
        }

        @Override
        public Object getItem(int position) {
            return dataList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            ViewHolder holder;
            if (convertView == null) {
                view = View.inflate(MainActivity.this, R.layout.item_list, null);
                holder = new ViewHolder(view);
                view.setTag(holder);
            } else {
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }

            holder.tv_name.setText(dataList.get(position));
            holder.tv_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("我还是能点的");
                }
            });
            holder.iv_icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("我特么页是能点的");
                }
            });
            holder.custom_swipelayout.setTag(position); // 通过setTag()可以将数据传递,在监听中获取到

            return view;
        }

        class ViewHolder {
            private final TextView tv_name;
            private final TextView tv_up;
            private final TextView tv_delete;
            private final SwipeLayout custom_swipelayout;
            private final ImageView iv_icon;

            public ViewHolder(View view) {
                iv_icon = (ImageView) view.findViewById(R.id.iv_item_icon);
                tv_name = (TextView) view.findViewById(R.id.tv_item_name);
                tv_up = (TextView) view.findViewById(R.id.tv_item_up);
                tv_up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("置顶咯");
                    }
                });
                tv_delete = (TextView) view.findViewById(R.id.tv_item_delete);
                tv_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("删你麻痹");
                    }
                });

                custom_swipelayout = (SwipeLayout) view.findViewById(R.id.custom_swipelayout);
                custom_swipelayout.setOnSwipeStateChangeListener(new SwipeLayout.OnSwipeStateChangeListener() {
                    @Override
                    public void onOpen(Object tag) {
                        System.out.println("第" + (Integer)tag + "打开了");
                    }

                    @Override
                    public void onClose(Object tag) {
                        System.out.println("第" + (Integer)tag + "关闭了");
                    }
                });
            }
        }
    }
}
