package com.example.numlibrary;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class NumController extends LinearLayout implements View.OnClickListener {

    private ImageView add;
    private ImageView sub;
    private TextView num;

    private int value = 1;
    private int minValue = 1;
    private int maxValue = 5;

    public NumController(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(VERTICAL);
        addView(View.inflate(context, R.layout.controller, null));
        add = findViewById(R.id.num_add);
        sub = findViewById(R.id.num_sub);
        num = findViewById(R.id.num_value);

        setValue(getValue());


        add.setOnClickListener(this);
        sub.setOnClickListener(this);
    }

    public NumController(Context context) {
        super(context);
    }

    public NumController(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        if (v == add){
            addNumber();
        }if (v == sub){
            subNumber();
        }
    }

    private void subNumber() {
        if (value > minValue){
            value --;
        }
        if (numberChangeListener!=null){
            numberChangeListener.onNumberChange(value);
        }
        setValue(value);
    }

    private void addNumber() {
        if (value< maxValue){
            value++;
        }
        if (numberChangeListener!=null){
            numberChangeListener.onNumberChange(value);
        }
        setValue(value);
    }

    public int getValue() {
        String valueStr = num.getText().toString().trim();
        if (valueStr!=null && valueStr!=""){
            value = Integer.valueOf(valueStr);
        }
        return value;
    }

    public void setValue(int value) {
        num.setText(value+"");
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnNumberChangeListener{
        void onNumberChange(int value);
    }

    public void setNumberChangeListener(OnNumberChangeListener numberChangeListener) {
        this.numberChangeListener = numberChangeListener;
    }

    private OnNumberChangeListener numberChangeListener;


}
