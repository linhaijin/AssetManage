package com.cowry.assetmanage.activity;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;

/**
 * Created by acer on 2016/7/23.
 */
public class WorkerManageActivity extends BaseActivity {
    @Override
    public int setLayout() {
        return R.layout.activity_worker_manage;
    }

    @Override
    public void findView() {

    }

    @Override
    public void setListener() {

    }

    @Override
    public void underCreate() {
        setAppTitle("员工信息管理");
    }
}
