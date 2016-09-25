package com.shiqkuangsan.mycustomviews.db;

import android.content.Context;

import com.shiqkuangsan.mycustomviews.utils.MyLogUtil;
import com.shiqkuangsan.mycustomviews.utils.SimplexUtil;

import org.xutils.DbManager;


/**
 * Created by shiqkuangsan on 2016/9/25.
 */

/**
 * 数据库帮助器
 */
public class SimpleDbHelper {

    /**
     * 数据库管理者,真正实现对数据库增删改查
     */
    private static DbManager manager;

    /**
     * 获取数据库管理者
     *
     * @param context 上下文
     * @return 数据库管理者
     */
    public static DbManager getInstance(Context context) {
        if (manager == null)
            synchronized (SimpleDbHelper.class) {
                if (manager == null) {
                   init(context);
                    MyLogUtil.d("数据库管理者初始化完成...");
                }
            }
        return manager;
    }

    /**
     * 初始化数据库相关配置(名称,所在目录,版本,升级监听)
     *
     * @param context 上下文
     */
    private static void init(Context context) {
        manager = SimplexUtil.getSimpleDbManager("xutils.db", context.getFilesDir(), 1, new DbManager.DbUpgradeListener() {
            @Override
            public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

            }
        });
    }

}
