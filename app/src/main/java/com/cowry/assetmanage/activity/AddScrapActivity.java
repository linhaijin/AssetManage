package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bigkoo.pickerview.TimePickerView;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.AllocationListAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.bean.CheckBean;
import com.cowry.assetmanage.widgets.DateUtils;
import com.cowry.assetmanage.widgets.ScrollListView;
import com.cowry.assetmanage.widgets.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by acer on 2016/7/3.
 */
public class AddScrapActivity extends BaseActivity implements View.OnClickListener{
    private final int REQUEST_MANAGER = 0X10;
    private TextView tvDone;
    private ACache mCache;
    private List<Bean> beans;
    private List<CheckBean> checkBeans;
    private List<String> startList = new ArrayList<>();
    private List<String> endList = new ArrayList<>();
    private MyTask myTask;
    private Button et_search;
    private ScrollListView scrollListView;
    private AllocationListAdapter adapter;
    private List<Bean> filterBeans;
    private String str;
    @Override
    public int setLayout() {
        return R.layout.activity_add_scrap;
    }

    @Override
    public void findView() {
        tvDone = (TextView) findViewById(R.id.tvDone);


        et_search = (Button) findViewById(R.id.et_search);
        scrollListView = (ScrollListView) findViewById(R.id.scrollListView);
        adapter = new AllocationListAdapter(this);
        scrollListView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        tvDone.setOnClickListener(this);
        et_search.setOnClickListener(this);

    }

    @Override
    public void underCreate() {
        setAppTitle("新增报废");
        mCache = ACache.get(this);
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        if (beans==null){
            ToastUtils.getInstance().showToast("未找到资产");
            finish();
        }else {
            for (int i=0;i<beans.size();i++){
                startList.add(beans.get(i).getbId());
            }
            for (int i=0;i<beans.size();i++){
                endList.add(beans.get(i).getbId());
            }
            checkBeans = JSON.parseArray(mCache.getAsString("scrapBeans"), CheckBean.class);

        }



    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tvDone:


                if (TextUtils.isEmpty(str)){
                    ToastUtils.getInstance().showToast("请选择你要报废的资产");
                    return;
                }

                myTask = new MyTask();
                myTask.execute();
                break;

            case R.id.et_search:
                Intent intent = new Intent(this,ManageActivity.class);
                intent.putExtra("type",1);
                startActivityForResult(intent, REQUEST_MANAGER);
                break;
        }

    }

    private class MyTask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showWaiting();
        }


        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            myTask=null;
            cancelWaiting();
            CheckBean bean = new CheckBean();
            bean.setCategory("全部资产");
            bean.setStatus("报废审批中");
            bean.setBeans(str);
            bean.setNumber(checkBeans.get(checkBeans.size()-1).getNumber()+1);
            checkBeans.add(bean);
            String s = JSON.toJSONString(checkBeans, true);
            mCache.put("scrapBeans", s, ACache.TIME_WEEK);
            setResult(RESULT_OK);
            finish();

        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            /**
             * 取消任务的处理  任务重置
             */
            myTask=null;
        }
    }

    @Override
    public void onBackPressed() {
        if (myTask!=null){
            /**取消任务*/
            myTask.cancel(true);
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case REQUEST_MANAGER:
                    filterBeans = new ArrayList<>();
                    str = data.getStringExtra("data");
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
                    break;
            }
        }
    }
}
