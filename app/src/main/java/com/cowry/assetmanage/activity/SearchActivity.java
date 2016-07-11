package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.SearchAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.bean.SearchBean;
import com.cowry.assetmanage.widgets.DateUtils;
import com.cowry.assetmanage.widgets.MyUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class SearchActivity extends BaseActivity {
    private List<Bean> beans;
    private SearchAdapter adapter;
    private ListView lvSearch;
    private EditText etSearch;
    private ImageView ivClearText;
    private ACache mCache;
    @Override
    public int setLayout() {
        return R.layout.activity_search;
    }

    @Override
    public void findView() {
        lvSearch = (ListView) findViewById(R.id.lvSearch);

        etSearch = (EditText) findViewById(R.id.et_search);
        ivClearText = (ImageView) findViewById(R.id.ivClearText);
    }

    @Override
    public void setListener() {
        /**清除输入字符**/
        ivClearText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                etSearch.setText("");
            }
        });
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

            }

            @Override
            public void afterTextChanged(Editable e) {

                String content = etSearch.getText().toString();
                if ("".equals(content)) {
                    ivClearText.setVisibility(View.INVISIBLE);
                } else {
                    ivClearText.setVisibility(View.VISIBLE);
                }
                if (content.length() > 0) {
                    List<Bean> fileterList = search(content);
                    adapter.refresh(fileterList);
                    //mAdapter.updateData(mContacts);
                } else {
                    adapter.refresh(beans);
                }

            }

        });

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("number",beans.get(position).getbId());
                bundle.putString("name",beans.get(position).getbName());
                bundle.putString("brand",beans.get(position).getbBrand());
                bundle.putString("status",beans.get(position).getbStatus());
                Intent intent = new Intent(SearchActivity.this,DetailActivity.class);
                intent.putExtra("data",bundle);
                startActivity(intent);
            }
        });
    }

    private List<Bean> search(String content) {
        List<Bean> filterBeans = new ArrayList<>();
        for (Bean bean : beans){
            if (bean.getbName().contains(content)){
                filterBeans.add(bean);
            }
        }
        return filterBeans;
    }

    @Override
    public void underCreate() {
        setAppTitle("资产查询");
        mCache = ACache.get(this);
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        adapter = new SearchAdapter(this);
        lvSearch.setAdapter(adapter);
        adapter.refresh(beans);

    }

    @Override
    public void onBack(View view) {
        MyUtil.hintKbTwo(this);
        super.onBack(view);
    }
}
