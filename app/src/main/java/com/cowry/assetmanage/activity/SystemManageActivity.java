package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.app.CWApplication;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.widgets.UIAlertView;

/**
 * Created by acer on 2016/7/23.
 */
public class SystemManageActivity extends BaseActivity implements View.OnClickListener {
    Button btnLogout;
    LinearLayout ly1;
    LinearLayout ly2;
    LinearLayout ly3;
    @Override
    public int setLayout() {
        return R.layout.activity_system_manage;
    }

    @Override
    public void findView() {
        btnLogout = (Button) findViewById(R.id.btnLogout);
        ly1 = (LinearLayout) findViewById(R.id.ly1);
        ly2 = (LinearLayout) findViewById(R.id.ly2);
        ly3 = (LinearLayout) findViewById(R.id.ly3);
    }

    @Override
    public void setListener() {

        ly1.setOnClickListener(this);
        ly2.setOnClickListener(this);
        ly3.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        setAppTitle("系统管理");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ly1:
                startActivity(new Intent(this,AssetCategoryActivity.class));
                break;
            case R.id.ly2:
                startActivity(new Intent(this,DeptCategoryActivity.class));
                break;
            case R.id.ly3:
                startActivity(new Intent(this,WorkerManageActivity.class));
                break;
            case R.id.btnLogout:
                UIAlertView dialog = new UIAlertView(SystemManageActivity.this,getString(R.string.kindly_reminder),"确认退出系统吗",
                        getString(R.string.cancel),getString(R.string.confirm));
                dialog.show();
                dialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                    @Override
                    public void doRight() {
                        startActivity(new Intent(SystemManageActivity.this, LoginActivity.class));
                        CWApplication.getInstance().closeOtherActivities("LoginActivity");
                    }
                });
                break;
        }
    }
}
