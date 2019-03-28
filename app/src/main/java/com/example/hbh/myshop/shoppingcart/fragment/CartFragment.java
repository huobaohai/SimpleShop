package com.example.hbh.myshop.shoppingcart.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.hbh.myshop.R;
import com.example.hbh.myshop.base.BaseFragment;
import com.example.hbh.myshop.home.bean.GoodDetailsBean;
import com.example.hbh.myshop.shoppingcart.adapter.ShoppingCartAdapter;
import com.example.hbh.myshop.utils.CacheUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends BaseFragment {

    private TextView cartEdit;
    private RecyclerView recycler;
    private LinearLayout llCheckAll;
    private CheckBox checkboxAll;
    private TextView totalPrice;
    private Button btnCheckOut;
    private LinearLayout llDelete;
    private CheckBox cbAllDelete;
    private Button btnDelete;
    private Button btnCollection;

    private LinearLayout cart_have_item;

    private LinearLayout ll_empty_cart;
    private ShoppingCartAdapter adapter;

    //编辑状态
    private static final int ACTION_EDIT = 1;
    //完成状态
    private static final int ACTION_COMPLETE = 2;

    @Override
    protected View initView() {
        View view = View.inflate(context, R.layout.fragment_cart,null);

        cartEdit = view.findViewById(R.id.cart_edit);
        recycler = view.findViewById(R.id.cart_recycle_view);
        llCheckAll = view.findViewById(R.id.ll_check_all);
        checkboxAll = view.findViewById(R.id.checkbox_all);
        totalPrice = view.findViewById(R.id.cart_total_price);
        btnCheckOut = view.findViewById(R.id.btn_check_out);
        llDelete =  view.findViewById(R.id.ll_delete);
        cbAllDelete = view.findViewById(R.id.cb_delete_all);
        btnDelete = view.findViewById(R.id.btn_delete);
        btnCollection = view.findViewById(R.id.btn_collection);
        ll_empty_cart = view.findViewById(R.id.ll_empty_shopcart);
        cart_have_item = view.findViewById(R.id.cart_have_item);
        cartEdit.setTag(ACTION_EDIT);
        initListener();
        return view;
    }

    @Override
    public void initData(){
        super.initData();
//        showData();
    }

    private void showData() {
        String jsonStr = CacheUtils.getDataString(context, "json_in_cart");
        List<GoodDetailsBean> beans = new ArrayList<>();
        if (jsonStr!= null && jsonStr != ""){
            beans = new Gson().fromJson(jsonStr, new TypeToken<List<GoodDetailsBean>>(){}.getType());
        }

        if (beans!=null && beans.size()>0){

            ll_empty_cart.setVisibility(View.GONE);
            cart_have_item.setVisibility(View.VISIBLE);
            cartEdit.setVisibility(View.VISIBLE);
            adapter = new ShoppingCartAdapter(context, beans, totalPrice, checkboxAll, cbAllDelete);
            recycler.setAdapter(adapter);
            recycler.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        }else {
            setEmptyLayout();
        }
    }

    private void setEmptyLayout() {
        cartEdit.setVisibility(View.GONE);
        cart_have_item.setVisibility(View.GONE);
        goToCart();
        ll_empty_cart.setVisibility(View.VISIBLE);
    }

    private void initListener() {
        cartEdit.setOnClickListener(v -> {
            if ((int)cartEdit.getTag() == ACTION_EDIT){
                // 点击进入编辑界面
                goToEdit();
                if (adapter!=null){
                    adapter.setAllItemSelected(false);
                    adapter.checkBoxAllItem();
                }
            }else {
                goToCart();
                if (adapter!=null){
                    adapter.setAllItemSelected(true);
                    adapter.checkBoxAllItem();
                    adapter.setTotalPrices();
                }
            }
        });

        btnDelete.setOnClickListener(v -> {
            if (adapter!=null){
                adapter.deleteItem();
                adapter.checkBoxAllItem();
                if (adapter.getItemCount()==0){
                    setEmptyLayout();
                }
            }
        });
    }

    private void goToCart() {
        cartEdit.setTag(ACTION_EDIT);
        cartEdit.setText("编辑");

        llDelete.setVisibility(View.GONE);
        llCheckAll.setVisibility(View.VISIBLE);
    }

    private void goToEdit() {
        cartEdit.setTag(ACTION_COMPLETE);
        cartEdit.setText("完成");

        llCheckAll.setVisibility(View.GONE);
        llDelete.setVisibility(View.VISIBLE);
    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (!hidden){
//            showData();
//        }
//    }

    @Override
    public void onResume() {
        super.onResume();
        showData();
    }
}