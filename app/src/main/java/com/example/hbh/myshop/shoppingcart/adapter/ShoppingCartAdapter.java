package com.example.hbh.myshop.shoppingcart.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hbh.myshop.R;
import com.example.hbh.myshop.home.bean.GoodDetailsBean;
import com.example.hbh.myshop.utils.CacheStorage;
import com.example.hbh.myshop.utils.ServerUrl;
import com.example.numlibrary.NumController;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.MyViewHolder> {

    private CheckBox checkBoxAll;
    private CheckBox deleteBoxAll;
    private TextView totalPrices;
    private Context sContext;
    private List<GoodDetailsBean> beanList;

    public ShoppingCartAdapter(Context context, List<GoodDetailsBean> beans, TextView totalPrice, CheckBox checkboxAll, CheckBox cbAllDelete) {
        this.sContext = context;
        this.beanList = beans;
        this.totalPrices = totalPrice;
        this.checkBoxAll = checkboxAll;
        this.deleteBoxAll = cbAllDelete;
        // 默认全选中
        setAllItemCheckTrue();
        checkBoxAllItem();
        setCheckBoxListener();
        setTotalPrices();
    }

    private void setAllItemCheckTrue() {
        for (GoodDetailsBean bean : beanList) {
            bean.setSelected(true);
        }
    }

    private void setCheckBoxListener() {
        setOnItemCheckListener(position -> {
            GoodDetailsBean bean = beanList.get(position);
            bean.setSelected(!bean.isSelected());
            notifyItemChanged(position);
            checkBoxAllItem();
            setTotalPrices();
        });

        checkBoxAll.setOnClickListener(v -> {
            setAllItemSelected(checkBoxAll.isChecked());
            setTotalPrices();
        });

        deleteBoxAll.setOnClickListener(v -> {
            setAllItemSelected(deleteBoxAll.isChecked());
        });
    }

    public void setAllItemSelected(boolean checked) {
        for (int i = 0; i < beanList.size(); i++) {

            beanList.get(i).setSelected(checked);
            notifyItemChanged(i);

        }
    }


    public void checkBoxAllItem() {
        int count = 0;
        for (GoodDetailsBean bean : beanList) {
            if (bean.isSelected()) {
                count++;
            } else {
                checkBoxAll.setChecked(false);
                deleteBoxAll.setChecked(false);
            }
        }
        if (count == beanList.size()) {
            checkBoxAll.setChecked(true);
            deleteBoxAll.setChecked(true);
        }
        if (beanList==null || beanList.size()<1){
            checkBoxAll.setChecked(false);
            deleteBoxAll.setChecked(false);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(sContext, R.layout.cart_good_list, null);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHoler, int i) {
        GoodDetailsBean bean = beanList.get(i);
        if (bean != null) {
            myViewHoler.cb_cart.setChecked(bean.isSelected());
            Glide.with(sContext).load(ServerUrl.BASE_IMAGE + bean.getFigure()).into(myViewHoler.iv_icon);
            myViewHoler.tv_desc_list.setText(bean.getName());
            myViewHoler.tv_price_list.setText(bean.getCover_price());
            myViewHoler.numController_view.setValue(bean.getNumber());
            myViewHoler.numController_view.setNumberChangeListener(value -> {
                bean.setNumber(value);
                notifyItemChanged(i);
                CacheStorage.getInstance().updateGoodData(bean);
                setTotalPrices();
            });
        }
    }

    public void setTotalPrices() {
        double total = 0;
        for (GoodDetailsBean bean : beanList) {
            if (bean.isSelected()) {
                total += Double.valueOf(bean.getCover_price()) * Double.valueOf(bean.getNumber());
            }
        }
        totalPrices.setText(String.valueOf(total));
    }


    @Override
    public int getItemCount() {
        return beanList.size();
    }

    public void deleteItem() {
        for (int i = 0; i < beanList.size(); i++){
            GoodDetailsBean bean = beanList.get(i);
            if (bean.isSelected()){
                beanList.remove(bean);
                CacheStorage.getInstance().deleteGoodData(bean);
                notifyItemRemoved(i);
                i--;
            }
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private CheckBox cb_cart;
        private ImageView iv_icon;
        private TextView tv_desc_list;
        private TextView tv_price_list;
        private NumController numController_view;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cb_cart = itemView.findViewById(R.id.cb_cart_list);
            iv_icon = itemView.findViewById(R.id.iv_cart_icon);
            tv_desc_list = itemView.findViewById(R.id.tv_desc_list);
            tv_price_list = itemView.findViewById(R.id.tv_price_list);
            numController_view = itemView.findViewById(R.id.numController_view);
            itemView.setOnClickListener(v -> {
                if (onItemCheckListener != null) {
                    onItemCheckListener.onItemChecked(getLayoutPosition());
                }
            });
        }
    }

    public interface OnItemCheckListener {
        void onItemChecked(int position);
    }

    private OnItemCheckListener onItemCheckListener;

    public void setOnItemCheckListener(OnItemCheckListener listener) {
        this.onItemCheckListener = listener;
    }

}
