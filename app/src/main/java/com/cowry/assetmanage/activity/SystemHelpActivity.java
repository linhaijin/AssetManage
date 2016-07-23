package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;

/**
 * Created by acer on 2016/7/23.
 */
public class SystemHelpActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout ly1;
    LinearLayout ly2;
    LinearLayout ly3;
    LinearLayout ly4;
    LinearLayout ly5;
    @Override
    public int setLayout() {
        return R.layout.activity_system_help;
    }

    @Override
    public void findView() {
        ly1 = (LinearLayout) findViewById(R.id.ly1);
        ly2 = (LinearLayout) findViewById(R.id.ly2);
        ly3 = (LinearLayout) findViewById(R.id.ly3);
        ly4 = (LinearLayout) findViewById(R.id.ly4);
        ly5 = (LinearLayout) findViewById(R.id.ly5);
    }

    @Override
    public void setListener() {
        ly1.setOnClickListener(this);
        ly2.setOnClickListener(this);
        ly3.setOnClickListener(this);
        ly4.setOnClickListener(this);
        ly5.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        setAppTitle("系统帮助");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly1:
                startActivity(new Intent(this,SoftAuthActivity.class));
                break;
            case R.id.ly2:
                startActivity(new Intent(this,HelpActivity.class));
                break;
            case R.id.ly4:
                startActivity(new Intent(this,VersionActivity.class));
                break;
            case R.id.ly5:
                startActivity(new Intent(this,FactoryActivity.class));
                break;
        }
    }
}
