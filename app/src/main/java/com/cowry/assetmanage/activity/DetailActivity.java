package com.cowry.assetmanage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.MainFragAdapter;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.fragment.AttachMsgFragment;
import com.cowry.assetmanage.fragment.BasicMsgFragment;
import com.cowry.assetmanage.fragment.DetailMsgFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class DetailActivity extends BaseActivity implements View.OnClickListener{
    private Button btnBasic;
    private Button btnDetail;
    private Button btnAttach;
    private ViewPager viewPager;
    private MainFragAdapter adapter;
    private List<Fragment> fragments;
    private List<Button> buttons;
    @Override
    public int setLayout() {
        return R.layout.activity_detail;
    }

    @Override
    public void findView() {
        btnBasic = (Button) findViewById(R.id.btnBasic);
        btnDetail = (Button) findViewById(R.id.btnDetail);
        btnAttach = (Button) findViewById(R.id.btnAttach);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        buttons = new ArrayList<>();
        buttons.add(btnBasic);
        buttons.add(btnDetail);
        buttons.add(btnAttach);
    }

    @Override
    public void setListener() {
        for (int i=0;i<buttons.size();i++){
            buttons.get(i).setOnClickListener(this);
        }
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < buttons.size(); i++) {
                    if (i == position) {
                        buttons.get(i).setSelected(true);
                    }else {
                        buttons.get(i).setSelected(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void underCreate() {
        setAppTitle("详细信息");
        Bundle data = getIntent().getBundleExtra("data");


        fragments = new ArrayList<>();
        BasicMsgFragment basicMsgFrag = new BasicMsgFragment();
        DetailMsgFragment detailMsgFragment = new DetailMsgFragment();
        AttachMsgFragment attachMsgFragment = new AttachMsgFragment();
        basicMsgFrag.setArguments(data);
        detailMsgFragment.setArguments(data);
        attachMsgFragment.setArguments(data);
        fragments.add(basicMsgFrag);
        fragments.add(detailMsgFragment);
        fragments.add(attachMsgFragment);
        adapter = new MainFragAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        btnBasic.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBasic:
                viewPager.setCurrentItem(0);
                break;
            case R.id.btnDetail:
                viewPager.setCurrentItem(1);
                break;
            case R.id.btnAttach:
                viewPager.setCurrentItem(2);
                break;
        }
    }
}
