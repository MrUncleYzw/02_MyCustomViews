package com.shiqkuangsan.mycustomviews;

import android.app.Application;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Handler;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiskCache;
import com.nostra13.universalimageloader.cache.disc.impl.ext.LruDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.DiskCacheUtils;

import org.xutils.common.util.DensityUtil;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.io.File;

/**
 * Created by shiqkuangsan on 2016/9/19.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();

        x.Ext.init(this);
        x.Ext.setDebug(true);

        handler = new Handler();
    }

    /**
     * 初始化ImageLoader
     */
    private void initImageLoader() {
        File cacheDir = getCacheDir();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration
                .Builder(getApplicationContext())
                .memoryCacheExtraOptions(480, 800) // max width, max height，即保存的每个缓存文件的最大长宽
                //.discCacheExtraOptions(480, 800, CompressFormat.JPEG, 75, null) // Can slow ImageLoader, use it carefully (Better don't use it)/设置缓存的详细信息，最好不要设置这个
                .threadPoolSize(3)//线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)// 线程优先级
                .denyCacheImageMultipleSizesInMemory()
//                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)) // You can pass your own memory cache implementation/你可以通过自己的内存缓存实现
                .memoryCache(new WeakMemoryCache())
                .memoryCacheSize(2 * 1024 * 1024)// 内存缓存2MB
                .tasksProcessingOrder(QueueProcessingType.LIFO)
//                .diskCache(new LruDiskCache(cacheDir, new Md5FileNameGenerator(),50*1024*1024))// 有异常
                .diskCache(new LimitedAgeDiskCache(cacheDir, 7 * 24 * 60 * 60))// 限制缓存时长
                .diskCacheSize(50 * 1024 * 1024)// 本地缓存50MB
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// MD5加密名字
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple())// 设置默认选项
                .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 20 * 1000)) // connectTimeout (5 s), readTimeout (30 s)超时时间
                //.writeDebugLogs() // Remove for release app
                .build();//开始构建
        ImageLoader.getInstance().init(config);
    }

    /**
     * ImageLoader图片的默认配置
     *
     * @return ImageLoader图片的默认配置
     */
    public static DisplayImageOptions getPicOptionWithLoading() {
        return new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.pic_loading) //设置图片在下载期间显示的图片
                .showImageForEmptyUri(R.drawable.pic_loading_fail)//设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.pic_loading_fail)// 失败时显示的图片
                .bitmapConfig(Bitmap.Config.RGB_565)//
                .cacheOnDisc(true)// 本地缓存
                .cacheInMemory(true)// 内存缓存
                .considerExifParams(true)
                .displayer(new RoundedBitmapDisplayer(10))// 设置半径为10的圆角
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .build();
    }

    private static Handler handler;
    public static Handler getHandler(){
        return handler;
    }

}
