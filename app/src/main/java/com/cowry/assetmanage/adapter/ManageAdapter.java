package com.cowry.assetmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.widgets.DateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by acer on 2016/6/27.
 */
public class ManageAdapter extends BaseAdapter {
    private List<Bean> recordBeans;
    private Context context;
    private static HashMap<Integer,Boolean> isSelected;

    public ManageAdapter(Context context) {
        this.context = context;
        recordBeans = new ArrayList<>();
        isSelected = new HashMap<Integer, Boolean>();
        initData();
    }
    // 初始化isSelected的数据
    private void initData(){
        for(int i=0; i<recordBeans.size();i++) {
            getIsSelected().put(i,false);
        }
    }
    public static HashMap<Integer,Boolean> getIsSelected() {
        return isSelected;
    }

    public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {
        ManageAdapter.isSelected = isSelected;
    }

    public void refresh(List<Bean> recordBeans){
        this.recordBeans = recordBeans;
        initData();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return recordBeans.size();
    }

    @Override
    public Object getItem(int position) {
        return recordBeans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.item_record,null);
            viewHolder = new ViewHolder();
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkbox);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.img);
            viewHolder.date = (TextView) convertView.findViewById(R.id.date);
            viewHolder.name = (TextView) convertView.findViewById(R.id.name);
            viewHolder.user = (TextView) convertView.findViewById(R.id.user);
            viewHolder.status = (TextView) convertView.findViewById(R.id.status);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bean bean = recordBeans.get(position);
        viewHolder.user.setText(bean.getbUser());
        viewHolder.name.setText(bean.getbName());
        viewHolder.date.setText(DateUtils.ToYMD(bean.getbInTime()));
        viewHolder.imageView.setImageResource(bean.getbImg());
        viewHolder.status.setText(bean.getbStatus());
        viewHolder.checkBox.setChecked(getIsSelected().get(position));
        viewHolder.checkBox.setVisibility(View.VISIBLE);
        return convertView;
    }

    public static class ViewHolder {
        public CheckBox checkBox;
        private ImageView imageView;
        private TextView name;
        private TextView user;
        private TextView date;
        private TextView status;
    }

    public int getSelectCount(){
        int count=0;
        for (int i=0;i<isSelected.size();i++){
            if (isSelected.get(i)){
                count++;
            }
        }
        return count;
    }
}
