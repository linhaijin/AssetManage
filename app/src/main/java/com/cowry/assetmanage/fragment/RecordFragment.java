package com.cowry.assetmanage.fragment;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.adapter.RecordAdapter;
import com.cowry.assetmanage.base.BaseFragment;
import com.cowry.assetmanage.bean.RecordBean;
import com.cowry.assetmanage.widgets.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/6/23.
 */
public class RecordFragment extends BaseFragment {
    private ListView lvRecord;
    private List<RecordBean> recordBeans;
    private RecordAdapter adapter;
    @Override
    protected void setContentView(LayoutInflater inflater, ViewGroup container) {
        rootView = inflater.inflate(R.layout.fragment_record,container,false);
    }

    @Override
    public void findView() {
        lvRecord = (ListView) rootView.findViewById(R.id.lvRecord);
    }

    @Override
    public void setListener() {

    }

    @Override
    public void underCreate() {
        recordBeans = new ArrayList<>();
        for (int i=0;i<5;i++){
            RecordBean bean = new RecordBean();
            bean.setImg(R.mipmap.computer);
            bean.setName("sony笔记本电脑");
            bean.setUser("王晓晓");
            bean.setDate(DateUtils.ToYMD(System.currentTimeMillis() - i * 24 * 60 * 60 * 1000));
            recordBeans.add(bean);
        }
        adapter = new RecordAdapter(getContext());

        lvRecord.setAdapter(adapter);

        adapter.refresh(recordBeans);

    }
}
