package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.Bundle;
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
import com.cowry.assetmanage.widgets.UIAlertView;

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
//    private Button btnAllocation;
//    private Button btnBorrow;
//    private Button btnRepair;
    private LinearLayout lyButtons;
    private TextView tvDone;
    Button btn1;
    Button btn2;
    Button btn3;
    Button btn4;
    @Override
    public int setLayout() {
        return R.layout.activity_manager;
    }

    @Override
    public void findView() {
        lvSearch = (ListView) findViewById(R.id.lvSearch);

        etSearch = (EditText) findViewById(R.id.et_search);
        ivClearText = (ImageView) findViewById(R.id.ivClearText);
//        btnAllocation = (Button) findViewById(R.id.btnAllocation);
//        btnBorrow = (Button) findViewById(R.id.btnBorrow);
//        btnRepair = (Button) findViewById(R.id.btnRepair);
        lyButtons = (LinearLayout) findViewById(R.id.lyButtons);
        tvDone = (TextView) findViewById(R.id.tvDone);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
    }

    @Override
    public void setListener() {
        ivClearText.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);
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
//        btnAllocation.setOnClickListener(this);
//        btnBorrow.setOnClickListener(this);
//        btnRepair.setOnClickListener(this);
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



        switch (v.getId()){
            case R.id.ivClearText:
                etSearch.setText("");
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
                Intent intent1 = new Intent();
                intent1.putExtra("data",str.toString());
                setResult(RESULT_OK,intent1);
                finish();
                break;
            case R.id.btn1:
                Bundle bundle = new Bundle();
                bundle.putString("number",beans.get(0).getbId());
                bundle.putString("name",beans.get(0).getbName());
                bundle.putString("brand",beans.get(0).getbBrand());
                bundle.putString("status",beans.get(0).getbStatus());
                Intent intent = new Intent(this,DetailActivity.class);
                intent.putExtra("data", bundle);
                startActivity(intent);
                break;
            case R.id.btn2:
                startActivity(new Intent(this,AssetOutActivity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this,SelectPrinterActivity.class));
                break;
            case R.id.btn4:
                final List<Bean> filterBeans = new ArrayList<>();
                if (adapter.getSelectCount()<=0){
                    ToastUtils.getInstance().showToast("未选中任何项");
                    return;
                }
                for (int i=0;i<beans.size();i++){
                    if (adapter.getIsSelected().get(i)){
                        filterBeans.add(beans.get(i));
                    }
                }
                UIAlertView dialog = new UIAlertView(this,getString(R.string.kindly_reminder),"确认删除所选资产，删除后将无法恢复",
                        getString(R.string.cancel),getString(R.string.confirm));
                dialog.show();
                dialog.setClicklistener(new UIAlertView.ClickListenerInterface() {
                    @Override
                    public void doRight() {
                        beans.removeAll(filterBeans);
                        adapter.refresh(beans);
                        ToastUtils.getInstance().showToast("删除成功");
                    }
                });
                break;

        }
    }
}
