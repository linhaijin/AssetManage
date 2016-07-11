package com.cowry.assetmanage.activity;

import android.view.View;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.widgets.ToastUtils;

/**
 * Created by acer on 2016/6/27.
 */
public class UserCenterActivity extends BaseActivity implements View.OnClickListener {
    private TextView tv1;
    private TextView tv2;
    private TextView tv3;
    private TextView tv4;
    private TextView tv5;
    @Override
    public int setLayout() {
        return R.layout.activity_user_center;
    }

    @Override
    public void findView() {
        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
    }

    @Override
    public void setListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        setAppTitle("个人中心");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv1:
                break;
            case R.id.tv2:
                break;
            case R.id.tv3:
                break;
            case R.id.tv4:
                break;
            case R.id.tv5:
                break;
        }
        ToastUtils.getInstance().showToast("暂未实现 敬请期待");
    }
}
