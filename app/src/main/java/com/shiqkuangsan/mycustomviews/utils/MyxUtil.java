package com.shiqkuangsan.mycustomviews.utils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.util.Map;

/**
 * Created by shiqkuangsan on 2016/9/24.
 */

/**
 * 简单的xUtils使用工具类
 */
public class MyxUtil {

    /**
     * 发送一个get请求
     *
     * @param url      请求链接
     * @param headers  请求头参数,不需要就给null
     * @param bodys    请求体参数,不需要就给null
     * @param callback 请求回调,简单的可以直接new SimpleRequstCallBack(),自己重写需要的方法
     * @param <T>      请求返回类型,这个很重要,一般都写String然后回拿到json数据的字符串, 然后自行解析.
     *                 但是比如说我要直接获得一个集合,或者直接获得一个bean类,那么你的bean类就得
     *                 添加注解@HttpResponse(parser =  JsonResponseParser.class),利用fastjson返回几行代码搞定
     *                 这里一定要注意你的bean类要和json数据对应
     * @return 请求Cancelable, 可以调用cancel()方法中断请求
     */
    public static <T> Callback.Cancelable sendGet(String url, Map<String, String> headers,
                                                  Map<String, String> bodys, Map<String, String> querys, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (headers != null && headers.size() > 0)
            for (Map.Entry<String, String> entry : headers.entrySet())
                params.addHeader(entry.getKey(), entry.getValue());
        if (querys != null && querys.size() > 0)
            for (Map.Entry<String, String> entry : querys.entrySet())
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
        if (bodys != null && bodys.size() > 0)
            for (Map.Entry<String, String> entry : bodys.entrySet())
                params.addBodyParameter(entry.getKey(), entry.getValue());

        return x.http().get(params, callback);
    }

    /**
     * 发送一个post请求
     *
     * @param url      请求链接
     * @param headers  请求头参数,不需要就给null
     * @param bodys    请求体参数,不需要就给null
     * @param callback 请求回调,简单的可以直接new SimpleRequstCallBack()
     * @param <T>      请求返回类型,这个很重要,一般都写String然后回拿到json数据的字符串, 然后自行解析.
     *                 但是比如说我要直接获得一个集合,或者直接获得一个bean类,那么你的bean类就得
     *                 添加注解@HttpResponse(parser =  JsonResponseParser.class),利用fastjson返回几行代码搞定
     *                 这里一定要注意你的bean类要和json数据对应
     * @return 请求Cancelable, 可以调用cancel()方法中断请求
     */
    public static <T> Callback.Cancelable sendPost(String url, Map<String, String> headers,
                                                   Map<String, String> bodys, Map<String, String> querys, Callback.CommonCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        if (headers != null && headers.size() > 0)
            for (Map.Entry<String, String> entry : headers.entrySet())
                params.addHeader(entry.getKey(), entry.getValue());
        if (querys != null && querys.size() > 0)
            for (Map.Entry<String, String> entry : querys.entrySet())
                params.addQueryStringParameter(entry.getKey(), entry.getValue());
        if (bodys != null && bodys.size() > 0)
            for (Map.Entry<String, String> entry : bodys.entrySet())
                params.addHeader(entry.getKey(), entry.getValue());

        return x.http().post(params, callback);
    }

    /**
     * 请求的时候使用该回调,可以自定义重写方法,需要谁重写谁
     *
     * @param <T> 返回泛型
     */
    public static class SimpleRequstCallBack<T> implements Callback.CommonCallback<T> {

        @Override
        public void onSuccess(T result) {
        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {
        }

        @Override
        public void onCancelled(CancelledException cex) {
        }

        @Override
        public void onFinished() {
        }
    }


    /**
     * 上传一个文件的方法,请求如果需要其他参数的话,方法还是需要修改的
     *
     * @param url       上传url
     * @param paramName 参数名
     * @param file      文件对象
     * @param callback  请求回调,简单的可以直接new SimpleUploadCallBack(),自己重写需要的方法
     * @param <T>       返回泛型
     * @return Cancelable对象, 可以调用cancel()方法撤销请求
     */
    public static <T> Callback.Cancelable uploadFile(String url, String paramName, File file, Callback.ProgressCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter(paramName, file);
        return x.http().post(params, callback);
    }

    /**
     * 下载一个文件的方法,请求如果需要其他参数的话,方法还是需要修改的
     *
     * @param url      上传url
     * @param filePath 文件的保存路径
     * @param callback 请求回调,简单的可以直接new SimpleUploadCallBack(),自己重写需要的方法
     * @param <T>      返回泛型
     * @return Cancelable对象, 可以调用cancel()方法撤销请求
     */
    public static <T> Callback.Cancelable downloadFile(String url, String filePath, Callback.ProgressCallback<T> callback) {
        RequestParams params = new RequestParams(url);
        //设置断点续传
        params.setAutoResume(true);
        params.setSaveFilePath(filePath);
        return x.http().get(params, callback);
    }

    /**
     * 上传的时候使用该回调,可自定义重写方法
     *
     * @param <T> 返回泛型
     */

    public static abstract class SimpleFileCallBack<T> implements Callback.ProgressCallback<T> {
        @Override
        public void onWaiting() {

        }

        @Override
        public void onStarted() {

        }

        @Override
        public void onLoading(long total, long current, boolean isDownloading) {

        }

        @Override
        public void onSuccess(T result) {

        }

        @Override
        public void onError(Throwable ex, boolean isOnCallback) {

        }

        @Override
        public void onCancelled(CancelledException cex) {

        }

        @Override
        public void onFinished() {

        }
    }
}
