package com.cowry.assetmanage.activity;

import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.AllocationListAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.widgets.ScrollListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuliangchun on 2016/7/5.
 */
public class RepairActivity extends BaseActivity{
    private ACache mCache;
    private List<Bean> beans;
    private List<Bean> filterBeans;
    private ScrollListView scrollListView;
    private TextView tvDone;
    private AllocationListAdapter adapter;
    @Override
    public int setLayout() {
        return R.layout.activity_repair;
    }

    @Override
    public void findView() {
        scrollListView = (ScrollListView) findViewById(R.id.scrollListView);
        tvDone = (TextView) findViewById(R.id.tvDone);
    }

    @Override
    public void setListener() {
        adapter = new AllocationListAdapter(this);
        scrollListView.setAdapter(adapter);
    }

    @Override
    public void underCreate() {
        setAppTitle("资产送修");
        mCache = ACache.get(this);
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        filterBeans = new ArrayList<>();
        String data = getIntent().getStringExtra("data");
        String spStr[] = data.split(",");
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
