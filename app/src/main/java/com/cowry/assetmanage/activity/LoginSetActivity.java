package com.cowry.assetmanage.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.widgets.DateUtils;
import com.cowry.assetmanage.widgets.MyUtil;
import com.cowry.assetmanage.widgets.PreferencesUtils;
import com.cowry.assetmanage.widgets.ToastUtils;
import com.cowry.assetmanage.widgets.UIEditView;

/**
 * Created by acer on 2016/7/23.
 */
public class LoginSetActivity extends BaseActivity implements View.OnClickListener{
    TextView tvCancel;
    TextView tvSave;
    Button btnInput;
    TextView tvAuthStatus;
    TextView tvStartTime;
    TextView tvStopTime;
    @Override
    public int setLayout() {
        return R.layout.activity_login_set;
    }

    @Override
    public void findView() {
        tvCancel = (TextView) findViewById(R.id.tvCancel);
        tvSave = (TextView) findViewById(R.id.tvSave);
        btnInput = (Button) findViewById(R.id.btnInput);
        tvAuthStatus = (TextView) findViewById(R.id.tvAuthStatus);
        tvStartTime = (TextView) findViewById(R.id.tvStartTime);
        tvStopTime = (TextView) findViewById(R.id.tvStopTime);
    }

    @Override
    public void setListener() {
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tvSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnInput.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        setAppTitle("设置");
        if (PreferencesUtils.getInstance().getBoolean("IS_ACTIVE",false)){
            tvAuthStatus.setText("已授权");
            tvStartTime.setText(DateUtils.ToYMD(PreferencesUtils.getInstance().getLong("START_TIME")));
            tvStopTime.setText(DateUtils.ToYMD(PreferencesUtils.getInstance().getLong("STOP_TIME")));
        }else {
            tvAuthStatus.setText("未授权");
            btnInput.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnInput:
                final UIEditView dialog = new UIEditView(this,"请输入您的授权码",getString(R.string.cancel),getString(R.string.confirm));
                dialog.show();
                dialog.setClicklistener(new UIEditView.ClickListenerInterface() {
                    @Override
                    public void doRight() {
                        showWaiting();
                        MyUtil.hintKbTwo(LoginSetActivity.this);
                        new Handler() {
                            @Override
                            public void handleMessage(Message msg) {
                                cancelWaiting();
                                ToastUtils.getInstance().showToast("授权成功");
                                PreferencesUtils.getInstance().putBoolean("IS_ACTIVE", true);
                                PreferencesUtils.getInstance().putLong("START_TIME", System
                                        .currentTimeMillis());
                                PreferencesUtils.getInstance().putLong("STOP_TIME",System.currentTimeMillis()+86400000l*90);
                                tvAuthStatus.setText("已授权");
                                btnInput.setVisibility(View.GONE);
                                tvStartTime.setText(DateUtils.ToYMD(System
                                        .currentTimeMillis()));
                                tvStopTime.setText(DateUtils.ToYMD(System.currentTimeMillis()+86400000l*90));
                            }
                        }.sendEmptyMessageDelayed(0,1000);
                    }
                });
                break;
        }
    }
}
