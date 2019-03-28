package com.example.hbh.myshop.utils;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseArray;

import com.example.hbh.myshop.app.MyShopApplication;
import com.example.hbh.myshop.home.bean.GoodDetailsBean;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class CacheStorage {

    public static final String JSON_IN_CART = "json_in_cart";
    private static CacheStorage INSTANCE;
    private Context mContext;
    private SparseArray<GoodDetailsBean> sparseArray;

    public CacheStorage(Context context) {
        this.mContext =context;
        this.sparseArray = new SparseArray<>(100);
        addToSparseArray();
    }

    private void addToSparseArray() {
        List<GoodDetailsBean> beans = new ArrayList<>();

        String jsonStr = CacheUtils.getDataString(mContext, JSON_IN_CART).trim();
        if (jsonStr!= null && jsonStr != ""){
            beans = new Gson().fromJson(jsonStr, new TypeToken<List<GoodDetailsBean>>(){}.getType());
        }

        for (GoodDetailsBean bean : beans){
            if (bean!=null){
                sparseArray.append(Integer.valueOf(bean.getProduct_id()), bean);
            }
        }

    }

    public static CacheStorage getInstance(){
        if (INSTANCE==null){
            INSTANCE = new CacheStorage(MyShopApplication.getContext());
        }
        return INSTANCE;
    }

    public void addGoodData(GoodDetailsBean bean){
        GoodDetailsBean data = sparseArray.get(Integer.valueOf(bean.getProduct_id()));
        if (data!=null){
            data.setNumber(data.getNumber()+1);
        }else{
            data = bean;
            data.setNumber(1);
        }
        sparseArray.append(Integer.valueOf(data.getProduct_id()), data);

        saveToLocal();
    }

    public void deleteGoodData(GoodDetailsBean bean){
        sparseArray.remove(Integer.valueOf(bean.getProduct_id()));
        saveToLocal();
    }

    public void updateGoodData(GoodDetailsBean bean){
        sparseArray.put(Integer.valueOf(bean.getProduct_id()), bean);
        saveToLocal();
    }

    private void saveToLocal() {
        List<GoodDetailsBean> beans = new ArrayList<>();
        for (int i=0; i< sparseArray.size();i++){
            beans.add(sparseArray.valueAt(i));

        }
        String dataJson = new Gson().toJson(beans);
        CacheUtils.saveDataString(mContext, JSON_IN_CART, dataJson);

    }

}
