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

public class HotGridViewAdapter extends BaseAdapter {

    private Context mContext;
    private List<ResultDataBean.ResultBean.HotInfoBean> info;

    public HotGridViewAdapter(Context hContext, List<ResultDataBean.ResultBean.HotInfoBean> hot_info) {
        this.mContext = hContext;
        this.info = hot_info;
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
        HotViewHolder holder;
        if (convertView!=null){
            view = convertView;
            holder = (HotViewHolder) view.getTag();
        }else{
            view = LayoutInflater.from(mContext).inflate(R.layout.home_hot_items,null);
            holder = new HotViewHolder();
            holder.imageView = view.findViewById(R.id.hot_info_icon);
            holder.desc = view.findViewById(R.id.hot_info_desc);
            holder.price = view.findViewById(R.id.hot_info_price);

            view.setTag(holder);
        }

        Glide.with(mContext).load(ServerUrl.BASE_IMAGE+info.get(position).getFigure()).into(holder.imageView);
        holder.desc.setText(info.get(position).getName());
        holder.price.setText(info.get(position).getCover_price());
        return view;
    }


    private class HotViewHolder {
        ImageView imageView;
        TextView desc;
        TextView price;
    }
}
