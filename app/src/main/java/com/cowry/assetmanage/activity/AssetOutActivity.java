package com.cowry.assetmanage.activity;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.widgets.ToastUtils;

/**
 * Created by acer on 2016/7/23.
 */
public class AssetOutActivity extends BaseActivity {
    Button btnOut;
    @Override
    public int setLayout() {
        return R.layout.activity_asset_out;
    }

    @Override
    public void findView() {
        btnOut = (Button) findViewById(R.id.btnOut);
    }

    @Override
    public void setListener() {
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaiting();
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        cancelWaiting();
                        ToastUtils.getInstance().showToast("导出成功");
                        finish();
                    }
                }.sendEmptyMessageDelayed(0,1000);
            }
        });
    }

    @Override
    public void underCreate() {
        setAppTitle("资产导出");
    }
}
