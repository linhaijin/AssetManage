package com.cowry.assetmanage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cowry.assetmanage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by acer on 2016/5/30.
 */
public class SingleSelectorAdp extends BaseAdapter {
    private Context context;
    private List<String> strings;
    private String selectName;    /**当前选择的是哪个*/

    public SingleSelectorAdp(Context context) {
        this.context = context;
        this.strings = new ArrayList<>();
        this.selectName = null;
    }

    public void  refresh(List<String> strings,String selectName){
        this.strings = strings;
        this.selectName = selectName;
        notifyDataSetChanged();

    }

    @Override
    public int getCount() {
        return strings.size();
    }

    @Override
    public Object getItem(int position) {
        return strings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_single_selector,null);
            viewHolder.classifyName = (TextView) convertView.findViewById(R.id.classifyName);
            viewHolder.classifyCheck = (ImageView) convertView.findViewById(R.id.classifyCheck);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.classifyName.setText(strings.get(position));
        if (selectName==null){
            return convertView;
        }
        if (selectName.equals(strings.get(position))){
            viewHolder.classifyCheck.setVisibility(View.VISIBLE);
        }else {
            viewHolder.classifyCheck.setVisibility(View.GONE);
        }
        return convertView;
    }

    static class ViewHolder{
        TextView classifyName;
        ImageView classifyCheck;
    }
}
