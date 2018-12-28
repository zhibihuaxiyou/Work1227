package com.bwie.work1227.mvp.callBack;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public interface MyCallBack<T> {
    void onMySuccess(T data);
    void onMyFailed(String error);

}
