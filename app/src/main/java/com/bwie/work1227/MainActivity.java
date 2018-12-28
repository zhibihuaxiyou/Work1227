package com.bwie.work1227;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.bwie.work1227.activity.ShopActivity;
import com.bwie.work1227.adapter.MyGoodsAdapter;
import com.bwie.work1227.api.Apis;
import com.bwie.work1227.api.Constants;
import com.bwie.work1227.bean.GoodsBean;
import com.bwie.work1227.mvp.presenter.IPresenterImplement;
import com.bwie.work1227.mvp.view.IView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IView {

    //初始化控件
    @BindView(R.id.main_iv_back)
    ImageView mMainIvBack;
    @BindView(R.id.main_et_txt)
    EditText mMainEtTxt;
    @BindView(R.id.main_iv_dan)
    ImageView mMainIvDan;
    @BindView(R.id.main_layout_1)
    LinearLayout mMainLayout1;
    @BindView(R.id.main_layout_2)
    LinearLayout mMainLayout2;
    @BindView(R.id.main_rv)
    RecyclerView mMainRv;
    private MyGoodsAdapter mMyGoodsAdapter;
    private IPresenterImplement mIPresenterImplement;

    private List<GoodsBean.DataBean> mDataBeans = new ArrayList<>();
    private Map<String, String> mMap;
    private boolean isChecked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //绑定    ButterKnife
        ButterKnife.bind(this);
        //线性布局管理器
        mMainRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        mIPresenterImplement = new IPresenterImplement(this);
        mMap = new HashMap<>();
        mMap.put(Constants.POST_BODY_KEY_GOODS_KEYWORDS, "手机");
        mMap.put(Constants.POST_BODY_KEY_GOODS_PAGE, "1");
        mIPresenterImplement.goodsDatas(Apis.GOODS_POST_URL, mMap, GoodsBean.class);
    }

    //点击事件
    @OnClick({R.id.main_iv_back, R.id.main_iv_dan})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.main_iv_back://退出
                this.finish();
                break;
            case R.id.main_iv_dan://点击切换视图
                checkIv(v);
                break;
        }
    }

    //成功
    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof GoodsBean) {
            GoodsBean goodsBean = (GoodsBean) data;
            List<GoodsBean.DataBean> beans = goodsBean.getData();
            mDataBeans = beans;
            //创建适配器
            mMyGoodsAdapter = new MyGoodsAdapter(this, beans);
            mMainRv.setAdapter(mMyGoodsAdapter);
            mMyGoodsAdapter.setGoodsClick(new MyGoodsAdapter.goodsClick() {
                @Override
                public void onClicked(int position) {
                    startActivity(new Intent(MainActivity.this, ShopActivity.class));
                }
            });
        }
    }

    //失败
    @Override
    public void getDataFailed(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    //点击切换视图
    public void checkIv(View v) {
        if (isChecked == false) {
            //点击后想要变成什么要的布局样式就搞一个你的需求
            mMainRv.setLayoutManager(new GridLayoutManager(this, 2));
            //给布尔值重新赋值
            isChecked = true;
            //给点击按钮的图片重新赋值
            mMainIvDan.setImageResource(R.drawable.duo);
        } else if (isChecked == true) {
            mMainRv.setLayoutManager(new LinearLayoutManager(this));
            //给布尔值重新赋值
            isChecked = false;
            //给点击按钮的图片重新赋值
            mMainIvDan.setImageResource(R.drawable.dan);
        }
    }

    /**
     * 防止内存泄露
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mIPresenterImplement!=null) {
            mIPresenterImplement.onDetach();
        }
    }
}
