package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/7/22.
 */
public class NewMainActivity extends BaseActivity implements View.OnClickListener {
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    ImageButton btnBack;
    private ACache mCache;
    private List<Bean> beans;
    @Override
    public int setLayout() {
        return R.layout.activity_new_main;
    }

    @Override
    public void findView() {
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);
        btnBack = (ImageButton) findViewById(R.id.btn_back);
    }

    @Override
    public void setListener() {
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
        image5.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        btnBack.setVisibility(View.GONE);
        setAppTitle("资产管理平台");
        mCache = ACache.get(this);
        mCache.clear();
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        if (beans == null) {
            beans = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Bean bean = new Bean();
                bean.setbId("0020160700" + i);
                if (i % 2 == 0) {
                    bean.setbName("acer笔记本");
                    bean.setbBrand("acer");
                } else {
                    bean.setbName("dell商务机");
                    bean.setbBrand("dell");
                }
                bean.setbImg(R.mipmap.computer);
                bean.setbInTime(System.currentTimeMillis() - i * 24 * 60 * 60 *
                        1000);
                bean.setbUser("王晓晓");
                bean.setbAmount(String.valueOf(i));
                if (i % 3 == 0) {
                    bean.setbStatus("送修中");
                } else if (i % 4 == 0) {
                    bean.setbStatus("报废");
                } else if (i % 5 == 0) {
                    bean.setbStatus("借出");
                } else {
                    bean.setbStatus("使用中");
                }
                beans.add(bean);
            }
            String s = JSON.toJSONString(beans, true);
            mCache.put("beans", s, ACache.TIME_WEEK);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.image1:
                intent.setClass(this, ManageActivity.class);
                startActivity(intent);
                break;
            case R.id.image2:
                intent.setClass(this, CheckActivity.class);
                startActivity(intent);
                break;
            case R.id.image3:
                intent.setClass(this, ScrapActivity.class);
                startActivity(intent);
                break;
            case R.id.image4:
                intent.setClass(this, SystemManageActivity.class);
                startActivity(intent);
                break;
            case R.id.image5:
                intent.setClass(this, SystemHelpActivity.class);
                startActivity(intent);
                break;
        }

    }
}
