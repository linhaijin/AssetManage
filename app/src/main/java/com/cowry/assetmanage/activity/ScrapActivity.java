package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.CheckAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.CheckBean;
import com.cowry.assetmanage.widgets.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class ScrapActivity extends BaseActivity {
    private final int REQUEST_ADD_CHECK = 0X10;
    private final int REQUEST_CHECK_DETAIL = 0x11;
    private ListView lvCheck;
    private List<CheckBean> checkBeans;
    private ACache mCache;
    private CheckAdapter adapter;
    private Button btnAdd;
    @Override
    public int setLayout() {
        return R.layout.activity_scrap;
    }

    @Override
    public void findView() {
        lvCheck = (ListView) findViewById(R.id.lvCheck);
        btnAdd = (Button) findViewById(R.id.btnAdd);
    }

    @Override
    public void setListener() {
        lvCheck.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ScrapActivity.this, ScrapDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("number",checkBeans.get(position).getNumber());
                bundle.putString("category", checkBeans.get(position).getCategory());
                bundle.putString("date", checkBeans.get(position).getDate());
                bundle.putString("status", checkBeans.get(position).getStatus());
                bundle.putString("beans",checkBeans.get(position).getBeans());
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScrapActivity.this, AddScrapActivity.class);
                startActivityForResult(intent, REQUEST_ADD_CHECK);
            }
        });
    }

    @Override
    public void underCreate() {
        setAppTitle("资产报废");
        mCache = ACache.get(this);
        checkBeans = JSON.parseArray(mCache.getAsString("scrapBeans"), CheckBean.class);
        if (checkBeans==null){
            checkBeans = new ArrayList<>();
            for (int i=0;i<2;i++){
                CheckBean bean = new CheckBean();
                if (i==0){
                    bean.setStatus("报废审批中");
                    bean.setBeans("00201607004,00201607005");
                    bean.setNumber(2016070001);
                }else {
                    bean.setStatus("已报废");
                    bean.setBeans("00201607002,00201607003");
                    bean.setNumber(2016070002);
                }

                bean.setDate(DateUtils.ToYMD(System.currentTimeMillis()));
                bean.setCategory("技术支撑部");
                checkBeans.add(bean);
            }
            String beans = JSON.toJSONString(checkBeans, true);
            mCache.put("scrapBeans", beans,ACache.TIME_WEEK);
        }
        adapter = new CheckAdapter(this);
        lvCheck.setAdapter(adapter);
        adapter.refresh(checkBeans);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode!=RESULT_OK){
            return;
        }
        switch (requestCode){
            case REQUEST_ADD_CHECK:
                checkBeans = JSON.parseArray(mCache.getAsString("scrapBeans"), CheckBean.class);
                adapter.refresh(checkBeans);
                break;
            case REQUEST_CHECK_DETAIL:
                checkBeans = JSON.parseArray(mCache.getAsString("scrapBeans"), CheckBean.class);
                adapter.refresh(checkBeans);
                break;
        }
    }
}
