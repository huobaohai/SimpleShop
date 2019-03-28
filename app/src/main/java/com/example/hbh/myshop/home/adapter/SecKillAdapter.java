package com.example.hbh.myshop.home.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbh.myshop.R;
import com.example.hbh.myshop.home.bean.ResultDataBean;
import com.example.hbh.myshop.utils.ServerUrl;

import java.util.List;

public class SecKillAdapter extends RecyclerView.Adapter {

    private List<ResultDataBean.ResultBean.SeckillInfoBean.ListBean> info;
    private Context aContext;
    private LayoutInflater inflater;

    public SecKillAdapter(Context sContext, List<ResultDataBean.ResultBean.SeckillInfoBean.ListBean> seckill_info) {
        this.aContext = sContext;
        this.info = seckill_info;
        this.inflater = LayoutInflater.from(aContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyViewHolder(inflater.inflate(R.layout.home_seckill_item, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        MyViewHolder holder = (MyViewHolder) viewHolder;
        Glide.with(aContext).load(ServerUrl.BASE_IMAGE+info.get(i).getFigure()).into(holder.icon);
        holder.CPrice.setText(info.get(i).getCover_price());
        holder.FPrice.setText(info.get(i).getOrigin_price());
    }

    @Override
    public int getItemCount() {
        return info.size();
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView icon;
        TextView CPrice;
        TextView FPrice;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.kill_item_icon);
            CPrice = itemView.findViewById(R.id.kill_item_CPrice);
            FPrice = itemView.findViewById(R.id.kill_item_FPrice);

            itemView.setOnClickListener(v -> {
                if (listener!=null){
                    listener.OnItemClicked(getLayoutPosition());
                }
            });

        }
    }

    // 设置回调方法
    public interface SecondKillListener{
        void OnItemClicked(int position);
    }

    private SecondKillListener listener;

    public void setSecondKillListener(SecondKillListener secondKillListener){
        this.listener = secondKillListener;
    }
}
