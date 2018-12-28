package com.bwie.work1227.mvp.presenter;

import com.bwie.work1227.mvp.callBack.MyCallBack;
import com.bwie.work1227.mvp.model.IModelImplement;
import com.bwie.work1227.mvp.view.IView;

import java.util.Map;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public class IPresenterImplement implements IPresenter {

    private IView mIView;
    private IModelImplement mIModelImplement;

    public IPresenterImplement(IView IView) {
        mIView = IView;
        mIModelImplement = new IModelImplement();
    }

    @Override
    public void goodsDatas(String url, final Map<String, String> map, Class clazz) {
        mIModelImplement.goodsData(url, map, clazz, new MyCallBack() {
            @Override
            public void onMySuccess(Object data) {
                mIView.getDataSuccess(data);
            }

            @Override
            public void onMyFailed(String error) {
                mIView.getDataFailed(error);
            }
        });
    }


    //解绑
    public void onDetach() {
        if (mIModelImplement != null) {
            mIModelImplement = null;
        }
        if (mIView != null) {
            mIView = null;
        }
    }
}
