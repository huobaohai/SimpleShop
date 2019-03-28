package com.example.hbh.myshop.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbh.myshop.R;
import com.example.hbh.myshop.home.bean.GoodDetailsBean;
import com.example.hbh.myshop.utils.CacheStorage;
import com.example.hbh.myshop.utils.ServerUrl;

public class GoodsDetailsActivity extends Activity implements View.OnClickListener {

    private ImageButton goodInfoBack;
    private ImageButton goodInfoMore;
    private LinearLayout detailMore;
    private TextView moreShare;
    private TextView moreSearch;
    private TextView moreHome;
    private ImageView goodInfoImage;
    private TextView goodInfoName;
    private TextView goodInfoDesc;
    private TextView goodInfoPrice;
    private TextView goodInfoStyle;
    private TextView goodInfoService;
    private TextView goodInfoStore;
    private TextView goodInfoCart;
    private Button goodInfoAddGood;
    private boolean ifMore = false;
    private GoodDetailsBean goodBean;

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_goods_detail);

        goodBean = (GoodDetailsBean) getIntent().getSerializableExtra("goodDetails");

        findViews();
        setGoodDetailInfo();
        setListener();

    }

    private void setGoodDetailInfo() {

        Glide.with(this).load(ServerUrl.BASE_IMAGE+goodBean.getFigure()).into(goodInfoImage);
        goodInfoName.setText(goodBean.getName());
        goodInfoPrice.setText(goodBean.getCover_price());
    }

    private void setListener() {
        goodInfoBack.setOnClickListener(this);
        goodInfoMore.setOnClickListener(this);
        moreShare.setOnClickListener(this);
        moreSearch.setOnClickListener(this);
        moreHome.setOnClickListener(this);
        goodInfoService.setOnClickListener(this);
        goodInfoStore.setOnClickListener(this);
        goodInfoCart.setOnClickListener(this);
        goodInfoAddGood.setOnClickListener(this);
    }

    private void findViews() {
        goodInfoBack = findViewById(R.id.ib_good_info_back);
        goodInfoMore = findViewById(R.id.ib_good_info_more);
        detailMore = findViewById(R.id.ll_detail_more);
        moreShare = findViewById(R.id.goods_more_share);
        moreSearch = findViewById(R.id.goods_more_search);
        moreHome = findViewById(R.id.goods_more_home);
        goodInfoImage = findViewById( R.id.good_info_image );
        goodInfoName = findViewById( R.id.good_info_name );
        goodInfoDesc = findViewById( R.id.good_info_desc );
        goodInfoPrice = findViewById( R.id.good_info_price );
        goodInfoStyle = findViewById( R.id.good_info_style );
        goodInfoService = findViewById( R.id.good_info_service );
        goodInfoStore = findViewById( R.id.good_info_store );
        goodInfoCart = findViewById( R.id.good_info_cart );
        goodInfoAddGood = findViewById( R.id.good_info_add_good );

    }

    @Override
    public void onClick(View v) {
        if (v == goodInfoBack){
            finish();
        }else if (v == goodInfoMore){
            if (!ifMore){
                detailMore.setVisibility(View.VISIBLE);
            }else {
                detailMore.setVisibility(View.GONE);
            }
            ifMore = !ifMore;
        }else if (v == goodInfoService){
            Toast.makeText(this, "客服", Toast.LENGTH_SHORT).show();
        }else if (v == goodInfoStore){
            Toast.makeText(this, "收藏", Toast.LENGTH_SHORT).show();
        }else if (v == goodInfoCart){
            Toast.makeText(this, "购物车", Toast.LENGTH_SHORT).show();
        }else if (v == goodInfoAddGood){
            CacheStorage.getInstance().addGoodData(goodBean);
            Toast.makeText(this, "加入购物车成功", Toast.LENGTH_SHORT).show();
        } else if (v == moreShare){
            Toast.makeText(this, "分享", Toast.LENGTH_SHORT).show();
        } else if (v == moreSearch){
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        }else if (v == moreHome){
            finish();
        }
    }
}
