package com.cowry.assetmanage.activity;

import android.view.View;
import android.widget.LinearLayout;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.widgets.ToastUtils;

/**
 * Created by acer on 2016/6/27.
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    private LinearLayout ly1;
    private LinearLayout ly2;
    private LinearLayout ly3;
    @Override
    public int setLayout() {
        return R.layout.activity_setting;
    }

    @Override
    public void findView() {
        ly1 = (LinearLayout) findViewById(R.id.ly1);
        ly2 = (LinearLayout) findViewById(R.id.ly2);
        ly3 = (LinearLayout) findViewById(R.id.ly3);
    }

    @Override
    public void setListener() {
        ly1.setOnClickListener(this);
        ly2.setOnClickListener(this);
        ly3.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        setAppTitle("信息设置");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly1:
                break;
            case R.id.ly2:
                break;
            case R.id.ly3:
                break;
        }
        ToastUtils.getInstance().showToast("暂未实现 敬请期待");
    }
}
