package com.cowry.assetmanage.activity;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;

/**
 * Created by acer on 2016/7/23.
 */
public class FactoryActivity extends BaseActivity {
    @Override
    public int setLayout() {
        return R.layout.activity_factory;
    }

    @Override
    public void findView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void underCreate() {
        setAppTitle("厂家信息");
    }
}
