package com.cowry.assetmanage.activity;

import android.os.Bundle;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.AllocationListAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.bean.CheckBean;
import com.cowry.assetmanage.widgets.ScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/7/23.
 */
public class ScrapDetailActivity extends BaseActivity {
    private ACache mCache;
    private ScrollListView scrollListView;
    private AllocationListAdapter adapter;
    private List<Bean> beans;
    private List<Bean> filterBeans;
    private final  int SCANNIN_GREQUEST_CODE = 1;
    private List<CheckBean> checkBeans;
    @Override
    public int setLayout() {
        return R.layout.activity_scrap_detail;
    }

    @Override
    public void findView() {
        scrollListView = (ScrollListView) findViewById(R.id.scrollListView);
        adapter = new AllocationListAdapter(this);
        scrollListView.setAdapter(adapter);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void underCreate() {
        setAppTitle("报废详情");
        mCache = ACache.get(this);
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        Bundle data = getIntent().getBundleExtra("data");
        String str = data.getString("beans");
        checkBeans = JSON.parseArray(mCache.getAsString("scrapBeans"), CheckBean.class);
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
}
