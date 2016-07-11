package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.AllocationListAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.app.CWApplication;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.bean.CheckBean;
import com.cowry.assetmanage.widgets.ScrollListView;
import com.cowry.assetmanage.widgets.ToastUtils;
import com.cowry.assetmanage.widgets.UIAlertView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class CheckDeatailActivity extends BaseActivity implements View.OnClickListener{
    private Button btnGenerate;
    private Button btnContinue;
    private Button btnEnd;
    private TextView tvDel;
    private TextView tvNumber;
    private TextView tvDate;
    private TextView tvStatus;
    private ACache mCache;
    private ScrollListView scrollListView;
    private AllocationListAdapter adapter;
    private List<Bean> beans;
    private List<Bean> filterBeans;
    private final  int SCANNIN_GREQUEST_CODE = 1;
    private List<CheckBean> checkBeans;
    private int number;
    @Override
    public int setLayout() {
        return R.layout.activity_check_detail;
    }

    @Override
    public void findView() {
        btnGenerate = (Button) findViewById(R.id.btnGenerate);
        btnContinue = (Button) findViewById(R.id.btnContinue);
        btnEnd = (Button) findViewById(R.id.btnEnd);
        tvDel = (TextView) findViewById(R.id.tvDel);
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        tvDate  = (TextView) findViewById(R.id.tvDate);
        tvStatus = (TextView) findViewById(R.id.tvStatus);
        scrollListView = (ScrollListView) findViewById(R.id.scrollListView);
    }

    @Override
    public void setListener() {
        btnGenerate.setOnClickListener(this);
        btnContinue.setOnClickListener(this);
        btnEnd.setOnClickListener(this);
        tvDel.setOnClickListener(this);
        adapter = new AllocationListAdapter(this);
        scrollListView.setAdapter(adapter);
    }

    @Override
    public void underCreate() {
        setAppTitle("盘点任务单");
        mCache = ACache.get(this);
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        checkBeans = JSON.parseArray(mCache.getAsString("checkBeans"), CheckBean.class);
        Bundle data = getIntent().getBundleExtra("data");
        number = data.getInt("number");
        String category = data.getString("category");
        String date = data.getString("date");
        String status = data.getString("status");
        String str = data.getString("beans");
        tvNumber.setText(String.valueOf(number));
        tvDate.setText(date);
        tvStatus.setText(status);
        if (status.equals("未开始")){
            tvDel.setText("删除");
            tvDel.setTag(0);
            btnGenerate.setVisibility(View.VISIBLE);
        }else if (status.equals("盘点中")){
            tvDel.setVisibility(View.GONE);
            btnContinue.setVisibility(View.VISIBLE);
            btnEnd.setVisibility(View.VISIBLE);

        }else if (status.equals("已完成")){

        }

        if (!status.equals("已完成")){
            tvDel.setText("删除");
            tvDel.setTag("0");
        }else {
            tvDel.setVisibility(View.GONE);
            tvDel.setTag("1");
        }
        filterBeans = new ArrayList<>();
        String spStr[] = str.split(",");
        for (int i=0;i<spStr.length;i++){
            for(int j=0;j<beans.size();j++){
                if (spStr[i].equals(beans.get(j).getbId())){
                    filterBeans.add(beans.get(j));
                    break;
                }
            }
        }
        adapter.refresh(filterBeans);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDel:
                if (v.getTag().equals("0")){
                    UIAlertView dialog = new UIAlertView(this,getString(R.string.kindly_reminder),"确认删除该盘点任务单吗",
                            getString(R.string.cancel),getString(R.string.confirm));
                    dialog.show();
                    dialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                        @Override
                        public void doRight() {
                           List<CheckBean> checkBeans = JSON.parseArray(mCache.getAsString("checkBeans"),
                                    CheckBean.class);
                            for (int i=0;i<checkBeans.size();i++){
                                if (Integer.valueOf(tvNumber.getText().toString())==checkBeans.get(i).getNumber()){
                                    checkBeans.remove(i);
                                    String s = JSON.toJSONString(checkBeans, true);
                                    mCache.put("checkBeans", s, ACache.TIME_WEEK);
                                    setResult(RESULT_OK);
                                    ToastUtils.getInstance().showToast("已删除");
                                    finish();
                                }
                            }
                        }
                    });
                }
                break;
            case R.id.btnGenerate:
                ToastUtils.getInstance().showToast("未经过审核不能生成盘点表");
                break;
            case R.id.btnContinue:
                Intent intent = new Intent(this,MipcaActivityCapture.class);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);

                break;
            case R.id.btnEnd:
                for (int i=0;i<checkBeans.size();i++){
                    if (checkBeans.get(i).getNumber()==number){
                        checkBeans.get(i).setStatus("已完成");
                        break;
                    }
                }
                String beans = JSON.toJSONString(checkBeans, true);
                mCache.put("checkBeans", beans,ACache.TIME_WEEK);
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
                    Bundle bundle = new Bundle();
                    bundle.putString("number",beans.get(0).getbId());
                    bundle.putString("name",beans.get(0).getbName());
                    bundle.putString("brand",beans.get(0).getbBrand());
                    bundle.putString("status",beans.get(0).getbStatus());
                    Intent intent = new Intent(CheckDeatailActivity.this,DetailActivity.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
                break;
        }
    }
}
