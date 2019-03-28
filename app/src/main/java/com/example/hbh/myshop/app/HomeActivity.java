package com.example.hbh.myshop.app;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.hbh.myshop.R;
import com.example.hbh.myshop.base.BaseFragment;
import com.example.hbh.myshop.community.fragment.CommunityFragment;
import com.example.hbh.myshop.home.fragment.HomeFragment;
import com.example.hbh.myshop.shoppingcart.fragment.CartFragment;
import com.example.hbh.myshop.type.fragment.TypeFragment;
import com.example.hbh.myshop.user.fragment.UserFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HomeActivity extends FragmentActivity {

    @Bind(R.id.home_radio_group)
    RadioGroup homeRadioGroup;

    private List<BaseFragment> fragmentList;
    private int position;
    private BaseFragment tempFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        initFragment();

        setListener();

    }

    private void setListener() {

        homeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.bottom_home:
                        position = 0;
                        break;
                    case R.id.bottom_type:
                        position = 1;
                        break;
                    case R.id.bottom_find:
                        position = 2;
                        break;
                    case R.id.bottom_shop:
                        position = 3;
                        break;
                    case R.id.bottom_user:
                        position = 4;
                        break;
                    default:
                        position = 0;
                        break;
                }
                BaseFragment fragment = getFragment(position);
                switchFragment(tempFragment, fragment);
            }
        });

        homeRadioGroup.check(R.id.bottom_home);

    }

    private void switchFragment(BaseFragment fragment, BaseFragment toFragment) {
        if (toFragment != tempFragment){
            tempFragment = toFragment;
            if (toFragment!=null){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                if (!toFragment.isAdded()){
                    if (fragment!=null){
                        transaction.hide(fragment);
                    }
                    transaction.add(R.id.home_layout_fragment, toFragment).commit();
                }else{
                    if (fragment!=null){
                        transaction.hide(fragment);
                    }
                    transaction.show(toFragment).commit();
                }
            }
        }
    }

    private BaseFragment getFragment(int pos) {
        if (fragmentList!=null && fragmentList.size()>0){
            BaseFragment fragment = fragmentList.get(pos);
            return fragment;
        }
        return null;
    }

    private void initFragment() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new TypeFragment());
        fragmentList.add(new CommunityFragment());
        fragmentList.add(new CartFragment());
        fragmentList.add(new UserFragment());
    }
}
