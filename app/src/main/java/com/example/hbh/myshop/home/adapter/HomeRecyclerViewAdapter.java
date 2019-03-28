package com.example.hbh.myshop.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbh.myshop.R;
import com.example.hbh.myshop.app.GoodsDetailsActivity;
import com.example.hbh.myshop.home.bean.GoodDetailsBean;
import com.example.hbh.myshop.home.bean.ResultDataBean;
import com.example.hbh.myshop.utils.ServerUrl;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;
import com.zhy.magicviewpager.transformer.RotateYTransformer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int BANNER = 0;
    private static final int CHANNEL = 1;
    private static final int ACT = 2;
    private static final int SECKILL = 3;
    private static final int RECOMMEND = 4;
    private static final int HOT = 5;
    private LayoutInflater mLayoutInflater;

    private Context mContext;
    private ResultDataBean.ResultBean mResultBean;

    private int currentType = BANNER;
    private static final String DataBean = "goodDetails";

    public HomeRecyclerViewAdapter(Context context, ResultDataBean.ResultBean resultBean) {
        this.mContext = context;
        this.mResultBean = resultBean;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i==BANNER){
            return new BannerViewHolder(mContext, mLayoutInflater.inflate(R.layout.home_banner,null));
        }else if (i == CHANNEL){
            return  new ChannelViewHolder(mContext, mLayoutInflater.inflate(R.layout.home_channel,null));
        }else if (i == ACT){
            return  new ActViewHolder(mContext, mLayoutInflater.inflate(R.layout.home_act,null));
        } else if (i == SECKILL){
            return  new SecKillViewHolder(mContext, mLayoutInflater.inflate(R.layout.home_seckill,null));
        }else if (i == RECOMMEND){
            return  new RecommendViewHolder(mContext, mLayoutInflater.inflate(R.layout.home_recommand,null));
        }else if (i == HOT){
            return  new HotViewHolder(mContext, mLayoutInflater.inflate(R.layout.home_hot,null));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int pos = getItemViewType(i);
        if (pos==BANNER){
            BannerViewHolder holder = (BannerViewHolder) viewHolder;
            holder.setData(mResultBean.getBanner_info());
        }else if(pos==CHANNEL){
            ChannelViewHolder holder = (ChannelViewHolder) viewHolder;
            holder.setData(mResultBean.getChannel_info());
        }else if(pos==ACT){
            ActViewHolder holder = (ActViewHolder) viewHolder;
            holder.setData(mResultBean.getAct_info());
        }else if(pos==SECKILL){
            SecKillViewHolder holder = (SecKillViewHolder) viewHolder;
            holder.setData(mResultBean.getSeckill_info());
        } else if(pos==RECOMMEND){
            RecommendViewHolder holder = (RecommendViewHolder) viewHolder;
            holder.setData(mResultBean.getRecommend_info());
        }else if(pos==HOT){
            HotViewHolder holder = (HotViewHolder) viewHolder;
            holder.setData(mResultBean.getHot_info());
        }
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case 0:
                currentType = BANNER;
                break;
            case 1:
                currentType = CHANNEL;
                break;
            case 2:
                currentType = ACT;
                break;
            case 3:
                currentType = SECKILL;
                break;
            case 4:
                currentType = RECOMMEND;
                break;
            case 5:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {
        private Context bContext;
        private Banner banner;

        public BannerViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.bContext = mContext;
            this.banner = inflate.findViewById(R.id.home_banner);
        }

        public void setData(List<ResultDataBean.ResultBean.BannerInfoBean> banner_info) {

            List<String> urlImages = new ArrayList<>();
            for (int i = 0; i < banner_info.size();i++){
                String imgUrl = ServerUrl.BASE_IMAGE + banner_info.get(i).getImage();
                urlImages.add(imgUrl);
            }

            banner.setImages(urlImages);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Glide.with(context).load(path).into(imageView);
                }
            });
            banner.setBannerAnimation(Transformer.Accordion);
            banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR);
            banner.start();

            setBannerListener();
        }

        private void setBannerListener() {
            banner.setOnBannerListener(position ->  {
                Toast.makeText(bContext, "position = " + position, Toast.LENGTH_LONG).show();
            });
        }
    }


    private class ChannelViewHolder extends RecyclerView.ViewHolder {
        private GridView gridView;
        private Context cContext;
        private ChannelViewAdapter adapter;

        public ChannelViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.cContext = mContext;
            this.gridView = inflate.findViewById(R.id.home_channel_gv);
        }

        public void setData(List<ResultDataBean.ResultBean.ChannelInfoBean> channel_info) {
            adapter = new ChannelViewAdapter(cContext, channel_info);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener((parent, view, position, id) -> {
                Toast.makeText(cContext, "position = " + position, Toast.LENGTH_LONG).show();
            });
        }
    }

    private class ActViewHolder extends RecyclerView.ViewHolder {
        private Context aContext;
        private ViewPager pager;

        public ActViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.aContext = mContext;
            this.pager = inflate.findViewById(R.id.home_act_vp);
        }

        public void setData(List<ResultDataBean.ResultBean.ActInfoBean> act_info) {
            pager.setOffscreenPageLimit(3);
            pager.setPageMargin(20);
            pager.setPageTransformer(true, new RotateYTransformer());
            pager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
                }

                @Override
                public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                    return view == o;
                }

                @NonNull
                @Override
                public Object instantiateItem(@NonNull ViewGroup container, int position) {
                    ImageView imageView = new ImageView(aContext);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(aContext).load(ServerUrl.BASE_IMAGE + act_info.get(position).getIcon_url()).into(imageView);
                    container.addView(imageView);

                    imageView.setOnClickListener(v -> {
                        Toast.makeText(aContext, "position = " + position, Toast.LENGTH_SHORT).show();
                    });

                    return imageView;
                }

                @Override
                public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                    container.removeView((View) object);
                }
            });

            // 滑动的监听事件
//            pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int i, float v, int i1) {
//
//                }
//
//                @Override
//                public void onPageSelected(int i) {
//                    Toast.makeText(aContext, "position = " + i, Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int i) {
//
//                }
//            });
        }
    }

    private class SecKillViewHolder extends RecyclerView.ViewHolder {
        private Context sContext;
        private TextView timesView;
        private RecyclerView recyclerView;
        private SecKillAdapter adapter;
        private long times =0;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                times = times - 1000;
                SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
                String timeStr = format.format(new Date(times));
                timesView.setText(timeStr);
                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0,1000);
                if (times<0){
                    handler.removeCallbacksAndMessages(null);
                }

            }
        };

        public SecKillViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.sContext = mContext;
            this.timesView = inflate.findViewById(R.id.second_kill_time);
            this.recyclerView = inflate.findViewById(R.id.kill_recyclerView);
        }

        public void setData(ResultDataBean.ResultBean.SeckillInfoBean seckill_info) {
            adapter = new SecKillAdapter(sContext, seckill_info.getList());
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(sContext,LinearLayoutManager.HORIZONTAL, false ));

            adapter.setSecondKillListener(position -> {
                ResultDataBean.ResultBean.SeckillInfoBean.ListBean seckillInfoBean = seckill_info.getList().get(position);
                GoodDetailsBean bean = new GoodDetailsBean();
                bean.setFigure(seckillInfoBean.getFigure());
                bean.setCover_price(seckillInfoBean.getCover_price());
                bean.setName(seckillInfoBean.getName());
                bean.setProduct_id(seckillInfoBean.getProduct_id());
                goToGoodsDetails(bean);
            });
            times = Integer.valueOf(seckill_info.getEnd_time()) - Integer.valueOf(seckill_info.getStart_time());

            handler.sendEmptyMessageDelayed(0, 1000);
        }

    }

    private class RecommendViewHolder extends RecyclerView.ViewHolder {

        private Context rContext;
        private GridView gridView;
        private TextView seeMore;

        public RecommendViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.rContext = mContext;
            this.gridView = inflate.findViewById(R.id.home_recommend_gv);
            this.seeMore = inflate.findViewById(R.id.home_recommend_more);
        }

        public void setData(List<ResultDataBean.ResultBean.RecommendInfoBean> recommend_info) {
            gridView.setAdapter(new RecGridViewAdapter(rContext, recommend_info));
            gridView.setOnItemClickListener((parent, view, position, id) -> {
//                Toast.makeText(rContext, "position = " + position, Toast.LENGTH_SHORT).show();
                ResultDataBean.ResultBean.RecommendInfoBean recommendInfoBean = recommend_info.get(position);
                GoodDetailsBean bean = new GoodDetailsBean();
                bean.setFigure(recommendInfoBean.getFigure());
                bean.setCover_price(recommendInfoBean.getCover_price());
                bean.setName(recommendInfoBean.getName());
                bean.setProduct_id(recommendInfoBean.getProduct_id());
                goToGoodsDetails(bean);
            });
        }
    }

    private class HotViewHolder extends RecyclerView.ViewHolder {

        private Context hContext;
        private GridView hGridView;
        private TextView hSeeMore;

        public HotViewHolder(Context mContext, View inflate) {
            super(inflate);
            this.hContext = mContext;
            this.hGridView = inflate.findViewById(R.id.home_hot_gv);
            this.hSeeMore = inflate.findViewById(R.id.home_hot_more);
        }

        public void setData(List<ResultDataBean.ResultBean.HotInfoBean> hot_info) {
            hGridView.setAdapter(new HotGridViewAdapter(hContext, hot_info));
            hGridView.setOnItemClickListener((parent, view, position, id) -> {
//                Toast.makeText(hContext, "position = " + position, Toast.LENGTH_SHORT).show();
                ResultDataBean.ResultBean.HotInfoBean hotInfoBean = hot_info.get(position);
                GoodDetailsBean bean = new GoodDetailsBean();
                bean.setFigure(hotInfoBean.getFigure());
                bean.setCover_price(hotInfoBean.getCover_price());
                bean.setName(hotInfoBean.getName());
                bean.setProduct_id(hotInfoBean.getProduct_id());
                goToGoodsDetails(bean);
            });
        }
    }

    private void goToGoodsDetails(GoodDetailsBean bean){
        Intent intent = new Intent(mContext, GoodsDetailsActivity.class);
        intent.putExtra(DataBean, bean);
        mContext.startActivity(intent);
    }
}
