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

public class ChannelViewAdapter extends BaseAdapter {
    private List<ResultDataBean.ResultBean.ChannelInfoBean> channel_info;
    private Context mContext;

    public ChannelViewAdapter(Context cContext, List<ResultDataBean.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = cContext;
        this.channel_info = channel_info;
    }

    @Override
    public int getCount() {
        return channel_info.size();
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
        ChannelViewHolder holder;
        if (convertView==null){
            view = LayoutInflater.from(mContext).inflate(R.layout.home_channel_item, null);
            holder = new ChannelViewHolder();
            holder.icon = view.findViewById(R.id.channel_item_icon);
            holder.text = view.findViewById(R.id.channel_item_text);
            view.setTag(holder);
        }else {
            view = convertView;
            holder = (ChannelViewHolder) view.getTag();
        }

        Glide.with(mContext).load(ServerUrl.BASE_IMAGE + channel_info.get(position).getImage()).into(holder.icon);
        holder.text.setText(channel_info.get(position).getChannel_name());
        return view;
    }

    private class ChannelViewHolder {
        ImageView icon;
        TextView text;
    }
}
