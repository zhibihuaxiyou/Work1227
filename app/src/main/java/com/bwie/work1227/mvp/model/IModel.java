package com.bwie.work1227.mvp.model;

import com.bwie.work1227.mvp.callBack.MyCallBack;

import java.util.Map;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public interface IModel {

    void goodsData(String url, Map<String, String> map, Class clazz, MyCallBack callBack);
}
