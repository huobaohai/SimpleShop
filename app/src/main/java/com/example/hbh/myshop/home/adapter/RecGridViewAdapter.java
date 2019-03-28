package com.example.hbh.myshop.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hbh.myshop.R;
import com.example.hbh.myshop.home.bean.ResultDataBean;
import com.example.hbh.myshop.utils.ServerUrl;

import java.util.List;

public class RecGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<ResultDataBean.ResultBean.RecommendInfoBean> info;

    public RecGridViewAdapter(Context rContext, List<ResultDataBean.ResultBean.RecommendInfoBean> recommend_info) {
        this.mContext = rContext;
        this.info = recommend_info;
    }

    @Override
    public int getCount() {
        return info.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        RViewHolder holder;
        if (convertView!=null){
            view = convertView;
            holder = (RViewHolder) view.getTag();
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.home_recommend_items,null);
            holder = new RViewHolder();
            holder.imageView = view.findViewById(R.id.recommend_info_icon);
            holder.desc = view.findViewById(R.id.recommend_info_desc);
            holder.price = view.findViewById(R.id.recommend_info_price);

            view.setTag(holder);
        }

        Glide.with(mContext).load(ServerUrl.BASE_IMAGE+info.get(position).getFigure()).into(holder.imageView);
        holder.desc.setText(info.get(position).getName());
        holder.price.setText(info.get(position).getCover_price());
        return view;
    }


    private class RViewHolder {
        ImageView imageView;
        TextView desc;
        TextView price;
    }
}
