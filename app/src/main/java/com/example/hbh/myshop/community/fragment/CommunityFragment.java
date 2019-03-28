package com.example.hbh.myshop.community.fragment;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.example.hbh.myshop.base.BaseFragment;

public class CommunityFragment extends BaseFragment {

    private TextView textView;

    @Override
    protected View initView() {
        textView = new TextView(context);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public void initData(){
        textView.setText("社区！");
    }
}