package com.shiqkuangsan.mycustomviews.bean;

/**
 * Created by shiqkuangsan on 2016/9/24.
 */


import org.xutils.http.annotation.HttpResponse;

/**
 * 省数据的bean类,添加注解做json解析封装,便可在CallBack泛型直接返回集合
 */
@HttpResponse(parser =  JsonResponseParser.class)
public class Province {

    public Integer id;
    public String name;

    @Override
    public String toString() {
        return "[ id: " + id +
                ",  name: " + name +
                " ]";
    }
}
