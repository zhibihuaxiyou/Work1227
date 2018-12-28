package com.bwie.work1227.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.work1227.R;
import com.bwie.work1227.bean.GoodsBean;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : 张腾
 * @date : 2018/12/27.
 * desc :
 */
public class MyGoodsAdapter extends RecyclerView.Adapter<MyGoodsAdapter.MyGoodsViewHolder> {

    private Context mContext;
    private List<GoodsBean.DataBean> mDataBeans = new ArrayList<>();

    public MyGoodsAdapter(Context context, List<GoodsBean.DataBean> dataBeans) {
        mContext = context;
        mDataBeans = dataBeans;
    }

    @NonNull
    @Override
    public MyGoodsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.goods_item_view, viewGroup, false);
        return new MyGoodsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyGoodsViewHolder myGoodsViewHolder, final int i) {
        String mImg = mDataBeans.get(i).getImages().split("\\|")[0].replace("https", "http");
        Uri uri = Uri.parse(mImg);
        myGoodsViewHolder.mImageViewImg.setImageURI(uri);
        myGoodsViewHolder.mTextViewTitle.setText(mDataBeans.get(i).getTitle());
        myGoodsViewHolder.mTextViewPrice.setText("￥：" + mDataBeans.get(i).getPrice() + "");
        myGoodsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mGoodsClick.onClicked(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataBeans == null ? 0 : mDataBeans.size();
    }

    public class MyGoodsViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView mImageViewImg;
        TextView mTextViewTitle, mTextViewPrice;

        public MyGoodsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewImg = itemView.findViewById(R.id.goods_img);
            mTextViewTitle = itemView.findViewById(R.id.goods_title);
            mTextViewPrice = itemView.findViewById(R.id.goods_price);
        }
    }

    private goodsClick mGoodsClick;

    public void setGoodsClick(goodsClick goodsClick) {
        mGoodsClick = goodsClick;
    }

    //自定义接口
    public interface goodsClick {
        void onClicked(int position);
    }
}
