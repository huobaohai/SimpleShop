package com.example.hbh.myshop.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class CacheUtils {

    public static String getDataString(Context context, String key){
        SharedPreferences sp = context.getSharedPreferences("myShop",Context.MODE_PRIVATE);
        return sp.getString(key, "");
    }

    public static void saveDataString(Context context, String key,String value){
        SharedPreferences sp = context.getSharedPreferences("myShop",Context.MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

}
