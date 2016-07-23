package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.app.CWApplication;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.widgets.DateUtils;
import com.cowry.assetmanage.widgets.PreferencesUtils;
import com.cowry.assetmanage.widgets.ToastUtils;
import com.cowry.assetmanage.widgets.UIAlertView;

/**
 * Created by acer on 2016/7/23.
 */
public class SoftAuthActivity extends BaseActivity {
    Button btnOut;
    TextView tvStartTime;
    TextView tvStopTime;
    @Override
    public int setLayout() {
        return R.layout.activity_soft_auth;
    }

    @Override
    public void findView() {
        btnOut = (Button) findViewById(R.id.btnOut);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvStopTime = (TextView) findViewById(R.id.tvStopTime);
    }

    @Override
    public void setListener() {
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIAlertView dialog = new UIAlertView(SoftAuthActivity.this,getString(R.string.kindly_reminder),"确认取消授权，取消后将清空所有数据",
                        getString(R.string.cancel),getString(R.string.confirm));
                dialog.show();
                dialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                    @Override
                    public void doRight() {
                        PreferencesUtils.getInstance().putBoolean("IS_ACTIVE", false);
                        startActivity(new Intent(SoftAuthActivity.this, LoginActivity.class));
                        CWApplication.getInstance().closeOtherActivities("LoginActivity");
                    }
                });
            }
        });
    }

    @Override
    public void underCreate() {
        setAppTitle("软件授权管理");
        tvStartTime.setText(DateUtils.ToYMD(PreferencesUtils.getInstance().getLong("START_TIME")));
        tvStopTime.setText(DateUtils.ToYMD(PreferencesUtils.getInstance().getLong("STOP_TIME")));
    }
}
