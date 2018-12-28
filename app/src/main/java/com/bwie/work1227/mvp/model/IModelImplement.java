package com.bwie.work1227.mvp.model;

import com.bwie.work1227.mvp.callBack.MyCallBack;
import com.bwie.work1227.netWork.RetrofitManager;
import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public class IModelImplement<T> implements IModel {

    @Override
    public void goodsData(String url, Map<String, String> map, final Class clazz, final MyCallBack callBack) {

        Map<String, RequestBody> requestBody = RetrofitManager.getManager().getRequestBody(map);
        RetrofitManager.getManager().postFormBody(url, requestBody).setHttpListener(new RetrofitManager.HttpListener() {

            @Override
            public void onSuccess(String data) {
                try {
                    Object o = new Gson().fromJson(data, clazz);
                    if (callBack != null) {
                        callBack.onMySuccess(o);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callBack != null) {
                        callBack.onMyFailed(e.getMessage());
                    }
                }
            }

            @Override
            public void onFailed(String error) {
                if (callBack != null) {
                    callBack.onMyFailed(error);
                }
            }
        });
    }
}
