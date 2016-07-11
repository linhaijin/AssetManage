package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.cowry.assetmanage.widgets.SingleSelectorView;
import com.cowry.assetmanage.widgets.ToastUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by acer on 2016/7/3.
 */
public class AddCheckActivity extends BaseActivity implements View.OnClickListener{
    private final int REQUEST_MANAGER = 0X10;
    private TimePickerView pvTime;
    private TextView tvNumber;
    private EditText etDate;
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
        return R.layout.activity_add_check;
    }

    @Override
    public void findView() {
        tvNumber = (TextView) findViewById(R.id.tvNumber);
        etDate = (EditText) findViewById(R.id.etDate);
        tvDone = (TextView) findViewById(R.id.tvDone);


        et_search = (Button) findViewById(R.id.et_search);
        scrollListView = (ScrollListView) findViewById(R.id.scrollListView);
        adapter = new AllocationListAdapter(this);
        scrollListView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        etDate.setOnClickListener(this);
        tvDone.setOnClickListener(this);
        et_search.setOnClickListener(this);

    }

    @Override
    public void underCreate() {
        setAppTitle("新增盘点任务");
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
            checkBeans = JSON.parseArray(mCache.getAsString("checkBeans"), CheckBean.class);
            if (checkBeans==null){
                Log.d("xlc","第一次生成盘点任务");
                tvNumber.setText("0020160001");
                checkBeans = new ArrayList<>();
            }else {
                Log.d("xlc","盘点任务加1");
                tvNumber.setText(String.valueOf(checkBeans.get(checkBeans.size()-1).getNumber()+1));
            }
        }



    }

    private void initSelectTime() {
        //时间选择器
        pvTime = new TimePickerView(this, TimePickerView.Type.YEAR_MONTH_DAY);
        pvTime.setTextPx(16);//设置字体大小
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 60, calendar.get(Calendar.YEAR));
        pvTime.setTime(new Date());
        pvTime.setCyclic(false);
        pvTime.setCancelable(true);

        //时间选择后回调
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                long timm = date.getTime();
                etDate.setText(DateUtils.ToYMD(timm));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.etDate:
                if (pvTime==null){
                    initSelectTime();
                }
                if (!pvTime.isShowing()){
                    pvTime.show();
                }
                break;
            case R.id.tvDone:
                if (TextUtils.isEmpty(etDate.getText())){
                    ToastUtils.getInstance().showToast("请选择盘点时间");
                    return;
                }

                if (TextUtils.isEmpty(str)){
                    ToastUtils.getInstance().showToast("请选择你要盘点的资产");
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
            bean.setDate(etDate.getText().toString());
            bean.setStatus("未开始");
            bean.setNumber(Integer.valueOf(tvNumber.getText().toString()));
            bean.setBeans(str);
//            bean.setStartId(filterBeans.get(0).getbId());
//            bean.setEndId(filterBeans.get(filterBeans.size()-1).getbId());
            checkBeans.add(bean);
            String s = JSON.toJSONString(checkBeans, true);
            mCache.put("checkBeans", s, ACache.TIME_WEEK);
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
