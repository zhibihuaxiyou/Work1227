package com.bwie.work1227.mvp.view;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public interface IView<T> {
    void getDataSuccess(T data);
    void getDataFailed(String error);
}
