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
public class SelectPrinterActivity extends BaseActivity{;
    Button btnPrint;
    @Override
    public int setLayout() {
        return R.layout.activity_select_printer;
    }

    @Override
    public void findView() {
        btnPrint = (Button) findViewById(R.id.btnPrint);
    }

    @Override
    public void setListener() {
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWaiting();
                new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        cancelWaiting();
                        ToastUtils.getInstance().showToast("打印完成");
                        finish();
                    }
                }.sendEmptyMessageDelayed(0,2000);
            }
        });
    }

    @Override
    public void underCreate() {
        setAppTitle("选择打印机");
    }
}
