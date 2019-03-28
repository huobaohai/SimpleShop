package com.example.hbh.myshop.home.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.example.hbh.myshop.R;
import com.example.hbh.myshop.base.BaseFragment;
import com.example.hbh.myshop.home.adapter.HomeRecyclerViewAdapter;
import com.example.hbh.myshop.home.bean.ResultDataBean;
import com.example.hbh.myshop.utils.ServerUrl;

import java.io.IOException;;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class HomeFragment extends BaseFragment {

    private static final String TAG = "HomeFragment";

    private TextView search_tv_id;
    private TextView message_tv_id;
    private RecyclerView recycle_view;
    private ImageButton home_to_top;

    private ResultDataBean.ResultBean resultBean;

    @Override
    protected View initView() {
        View view = View.inflate(context,R.layout.fragment_home,null);
        search_tv_id = view.findViewById(R.id.search_tv_id);
        message_tv_id = view.findViewById(R.id.message_tv_id);
        recycle_view = view.findViewById(R.id.recycle_view);
        home_to_top = view.findViewById(R.id.home_to_top);

        setListener();
        return view;
    }

    private void setListener() {

        home_to_top.setOnClickListener(v -> {
            recycle_view.scrollToPosition(0);
        });

        search_tv_id.setOnClickListener(v -> {
                Toast.makeText(getContext(),"搜索", Toast.LENGTH_LONG).show();
        });

        message_tv_id.setOnClickListener(v -> {
            Toast.makeText(getContext(),"我收到的消息", Toast.LENGTH_LONG).show();
        });

    }

    public void initData(){
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(ServerUrl.HOME_URL).build();
            try {
                Response response = client.newCall(request).execute();
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putString("result", response.body().string());
                msg.what = 1;
                msg.setData(bundle);
                handler.sendMessage(msg);

            } catch (IOException e) {
                Log.e(TAG, "initData: " + e.getMessage() );
            }
        }).start();
    }

    private void parseResponseJson(String string) {
        ResultDataBean resultDataBean = JSON.parseObject(string, ResultDataBean.class);
        resultBean = resultDataBean.getResult();
        if (resultBean!=null){
            recycle_view.setAdapter(new HomeRecyclerViewAdapter(context, resultBean));
            GridLayoutManager manager = new GridLayoutManager(context, 1);
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int i) {
                    if (i >= 4){
                        home_to_top.setVisibility(View.VISIBLE);
                    }else {
                        home_to_top.setVisibility(View.GONE);
                    }
                    return 1;
                }
            });
            recycle_view.setLayoutManager(manager);
        }
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String res = msg.getData().getString("result");
                    parseResponseJson(res);
            }
        }
    };
}