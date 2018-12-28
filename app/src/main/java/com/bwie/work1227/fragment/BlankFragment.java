package com.bwie.work1227.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.work1227.R;
import com.bwie.work1227.adapter.MyPagerAdapter;
import com.bwie.work1227.api.Apis;
import com.bwie.work1227.api.Constants;
import com.bwie.work1227.bean.ShopBean;
import com.bwie.work1227.mvp.presenter.IPresenterImplement;
import com.bwie.work1227.mvp.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author : 张腾
 * @date : 2018/12/28.
 * desc :
 */
public class BlankFragment extends Fragment implements IView {

    @BindView(R.id.shop_vp)
    ViewPager mShopVp;
    @BindView(R.id.shop_title)
    TextView mShopTitle;
    @BindView(R.id.shop_subhead)
    TextView mShopSubhead;
    @BindView(R.id.shop_price)
    TextView mShopPrice;
    private IPresenterImplement mIPresenterImplement;
    private MyPagerAdapter mMyPagerAdapter;
    private String[] mSplit;
    private String num = "1";

    private Handler han = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int i = mShopVp.getCurrentItem();
            i++;
            mShopVp.setCurrentItem(i);
            //设置自动轮播
            send();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view, container, false);
        ButterKnife.bind(this, view);
        mIPresenterImplement = new IPresenterImplement(this);
        Map<String, String> map = new HashMap<>();
        map.put(Constants.POST_BODY_KEY_SHOP_PID, num);
        mIPresenterImplement.goodsDatas(Apis.SHOP_POST_URL, map, ShopBean.class);
        send();
        mShopVp.setCurrentItem(1000);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ShopBean) {
            ShopBean shopBean = (ShopBean) data;
            ShopBean.DataBean beanData = shopBean.getData();
            String images = beanData.getImages();
            for (int i = 0; i < 3; i++) {
                mSplit = images.split("\\|");
            }
            mMyPagerAdapter = new MyPagerAdapter(getActivity(), mSplit);
            mShopTitle.setText(beanData.getTitle());
            mShopSubhead.setText(beanData.getSubhead());
            mShopPrice.setText("￥：" + beanData.getPrice() + "");
            mShopVp.setAdapter(mMyPagerAdapter);
        }
    }

    //设置自动轮播
    private void send() {
        new Thread() {
            @Override
            public void run() {
                han.sendEmptyMessageDelayed(0, 3000);
            }
        }.start();
    }

    @Override
    public void getDataFailed(String error) {

    }

}
