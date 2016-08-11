package com.shiqkuangsan.mycustomviews.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by shiqkuangsan on 2016/5/4.
 */
// 基类Activity 
public abstract class BaseActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initListener();
    }

    public abstract void initView();
    public abstract void initData();
    public abstract void initListener();
    public abstract void processClick(View view);

    @Override
    public void onClick(View v) {
        processClick(v);
    }
}
