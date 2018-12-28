package com.bwie.work1227.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;


/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :   轮播图 适配器
 */
public class MyPagerAdapter extends PagerAdapter {
    private Context mContext;
    private String[] mSplit;

    public MyPagerAdapter( Context context, String[] split) {
        mContext = context;
        mSplit = split;
    }


    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(mContext);
        Glide.with(mContext).
                load(mSplit[position % mSplit.length]).
                into(imageView);
        container.addView(imageView);
        return imageView;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
