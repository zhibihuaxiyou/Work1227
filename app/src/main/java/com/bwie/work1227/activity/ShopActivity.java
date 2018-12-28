package com.bwie.work1227.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;


import com.bwie.work1227.R;
import com.bwie.work1227.fragment.BlankFragment;

import java.util.ArrayList;

//implements IView
public class ShopActivity extends AppCompatActivity  {



    private TabLayout tabLayout;
    private ViewPager viewPager;
    ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
    ArrayList<String> titleList = new ArrayList<String>();
    private ImageView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        mBack = (ImageView) findViewById(R.id.shop_back);
        initData();
        //ctrl+p 提示参数的快捷键
        MPagerAdapter mPagerAdapter = new MPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mPagerAdapter);

        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //让tablayout和Viewpager关联;
        tabLayout.setupWithViewPager(viewPager);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    //初始化数据
    private void initData() {
        for (int i = 0; i < 3; i++) {
            fragmentList.add(new BlankFragment());
            titleList.add("商品");
            titleList.add("详情");
            titleList.add("评论");
        }
    }



    //适配器
    class MPagerAdapter extends FragmentPagerAdapter {

        public MPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        //需要重写个返回标题的方法;
        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
