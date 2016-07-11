package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.ManageAdapter;
import com.cowry.assetmanage.adapter.SearchAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.widgets.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class ManageActivity extends BaseActivity implements View.OnClickListener{
    private List<Bean> beans;
    private ManageAdapter adapter;
    private ListView lvSearch;
    private EditText etSearch;
    private ImageView ivClearText;
    private ACache mCache;
    private Button btnAllocation;
    private Button btnBorrow;
    private Button btnRepair;
    private LinearLayout lyButtons;
    private TextView tvDone;
    @Override
    public int setLayout() {
        return R.layout.activity_manager;
    }

    @Override
    public void findView() {
        lvSearch = (ListView) findViewById(R.id.lvSearch);

        etSearch = (EditText) findViewById(R.id.et_search);
        ivClearText = (ImageView) findViewById(R.id.ivClearText);
        btnAllocation = (Button) findViewById(R.id.btnAllocation);
        btnBorrow = (Button) findViewById(R.id.btnBorrow);
        btnRepair = (Button) findViewById(R.id.btnRepair);
        lyButtons = (LinearLayout) findViewById(R.id.lyButtons);
        tvDone = (TextView) findViewById(R.id.tvDone);
    }

    @Override
    public void setListener() {
        ivClearText.setOnClickListener(this);
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
        btnAllocation.setOnClickListener(this);
        btnBorrow.setOnClickListener(this);
        btnRepair.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ManageAdapter.ViewHolder holder = (ManageAdapter.ViewHolder) view.getTag();
                holder.checkBox.toggle();
                adapter.getIsSelected().put(position,holder.checkBox.isChecked());
            }
        });

    }

    @Override
    public void underCreate() {
        setAppTitle("资产管理");
        int type = getIntent().getIntExtra("type",0);
        if (type==1){
            lyButtons.setVisibility(View.GONE);
            tvDone.setVisibility(View.VISIBLE);
        }
        mCache = ACache.get(this);
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        adapter = new ManageAdapter(this);
        lvSearch.setAdapter(adapter);
        adapter.refresh(beans);
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
    public void onClick(View v) {

        Intent intent = new Intent();

        switch (v.getId()){
            case R.id.ivClearText:
                etSearch.setText("");
                break;
            case R.id.btnAllocation:
                intent.setClass(this,AllocationActivity.class);
                break;
            case R.id.btnBorrow:
                intent.setClass(this,BorrowActivity.class);
                break;
            case R.id.btnRepair:
                intent.setClass(this,RepairActivity.class);
                break;
            case R.id.tvDone:
                StringBuilder str = new StringBuilder();
                if (adapter.getSelectCount()<=0){
                    ToastUtils.getInstance().showToast("未选中任何项");
                    return;
                }
                for (int i=0;i<beans.size();i++){
                    if (adapter.getIsSelected().get(i)){
                        if (TextUtils.isEmpty(str)){
                            str.append(beans.get(i).getbId());
                        }else {
                            str.append(",");
                            str.append(beans.get(i).getbId());
                        }

                    }
                }
                intent.putExtra("data",str.toString());
                setResult(RESULT_OK, intent);
                finish();
                break;

        }
        StringBuilder str = new StringBuilder();
        if (v.getId()!= R.id.ivClearText&&v.getId()!= R.id.tvDone){
            if (adapter.getSelectCount()<=0){
                ToastUtils.getInstance().showToast("未选中任何项");
                return;
            }
            for (int i=0;i<beans.size();i++){
                if (adapter.getIsSelected().get(i)){
                    if (TextUtils.isEmpty(str)){
                        str.append(beans.get(i).getbId());
                    }else {
                        str.append(",");
                        str.append(beans.get(i).getbId());
                    }

                }
            }
            intent.putExtra("data",str.toString());
            startActivity(intent);
        }
    }
}
