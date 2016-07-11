package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.cowry.assetmanage.R;
import com.cowry.assetmanage.activity.MipcaActivityCapture;
import com.cowry.assetmanage.adapter.MainFragAdapter;
import com.cowry.assetmanage.app.ACache;
import com.cowry.assetmanage.base.BaseActivity;
import com.cowry.assetmanage.bean.Bean;
import com.cowry.assetmanage.fragment.IndexFragment;
import com.cowry.assetmanage.fragment.RecordFragment;
import com.cowry.assetmanage.widgets.ToastUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private final  int SCANNIN_GREQUEST_CODE = 1;
    private ViewPager viewPager;
    private LinearLayout lyIndex;
    private LinearLayout lyRecord;
    private ImageView indexIcon;
    private ImageView recordIcon;
    private TextView indexText;
    private TextView recordText;
    private MainFragAdapter adapter;
    private List<Fragment> fragments;
    private List<ImageView> imgCollection = new ArrayList<ImageView>();
    private List<TextView> tvCollection = new ArrayList<TextView>();
    private List<LinearLayout> linearCollection = new ArrayList<LinearLayout>();

    private List<Integer> fouceImages = new ArrayList<Integer>();
    private List<Integer> nofouceImages = new ArrayList<Integer>();
    private LinearLayout lyScan;
    private ACache mCache;
    private List<Bean> beans;
    @Override
    public int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void findView() {
        viewPager = (ViewPager) findViewById(R.id.vp_main);
        lyIndex = (LinearLayout) findViewById(R.id.lyIndex);
        lyRecord = (LinearLayout) findViewById(R.id.lyRecord);
        indexIcon = (ImageView) findViewById(R.id.indexIcon);
        recordIcon = (ImageView) findViewById(R.id.recordIcon);
        indexText = (TextView) findViewById(R.id.indexText);
        recordText = (TextView) findViewById(R.id.recordText);
        linearCollection.add(lyIndex);
        linearCollection.add(lyRecord);
        imgCollection.add(indexIcon);
        imgCollection.add(recordIcon);
        tvCollection.add(indexText);
        tvCollection.add(recordText);
        fouceImages.add(R.mipmap.tab_bar_home_focus_icon);
        fouceImages.add(R.mipmap.tab_bar_info_focus_icon);
        nofouceImages.add(R.mipmap.tab_bar_home_default_icon);
        nofouceImages.add(R.mipmap.tab_bar_info_default_icon);

        lyScan = (LinearLayout) findViewById(R.id.lyScan);
    }

    @Override
    public void setListener() {
        fragments = new ArrayList<>();
        fragments.add(new IndexFragment());
        fragments.add(new RecordFragment());
        adapter = new MainFragAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int
                    positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                setMenuFocus(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        for (int i=0;i<linearCollection.size();i++){
            linearCollection.get(i).setOnClickListener(this);
        }

        lyScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startScanQrCode();
            }
        });


    }

    private void setMenuFocus(int position) {
        for (int i=0;i<imgCollection.size();i++){
            if (i==position){
                imgCollection.get(i).setImageResource(fouceImages.get(i));
                tvCollection.get(i).setTextColor(getResources().getColor(R.color.blue));
            }else {
                imgCollection.get(i).setImageResource(nofouceImages.get(i));
                tvCollection.get(i).setTextColor(getResources().getColor(R.color.white));
            }

        }
    }

    @Override
    public void underCreate() {
        viewPager.setCurrentItem(0);
        setMenuFocus(0);
        mCache = ACache.get(this);
        mCache.clear();
        beans = JSON.parseArray(mCache.getAsString("beans"), Bean.class);
        if (beans==null){
            beans = new ArrayList<>();
            for (int i=0;i<10;i++){
                Bean bean = new Bean();
                bean.setbId("0020160700" + i);
                if (i%2==0){
                    bean.setbName("acer笔记本");
                    bean.setbBrand("acer");
                }else {
                    bean.setbName("dell商务机");
                    bean.setbBrand("dell");
                }
                bean.setbImg(R.mipmap.computer);
                bean.setbInTime(System.currentTimeMillis() - i * 24 * 60 * 60 *
                        1000);
                bean.setbUser("王晓晓");
                bean.setbAmount(String.valueOf(i));
                if (i%3==0){
                    bean.setbStatus("送修中");
                }else  if (i%4==0){
                    bean.setbStatus("报废");
                }else if (i%5==0){
                    bean.setbStatus("借出");
                }else {
                    bean.setbStatus("使用中");
                }
                beans.add(bean);
            }
            String s = JSON.toJSONString(beans, true);
            mCache.put("beans", s,ACache.TIME_WEEK);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lyIndex:
                viewPager.setCurrentItem(0);
                setMenuFocus(0);
                break;
            case R.id.lyRecord:
                viewPager.setCurrentItem(1);
                setMenuFocus(1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if(resultCode == RESULT_OK){
//                    Bundle bundle = data.getExtras();
//                    String result = bundle.getString("result");
//                   ToastUtils.getInstance().showToast(result);
                    Bundle bundle = new Bundle();
                    bundle.putString("number",beans.get(0).getbId());
                    bundle.putString("name",beans.get(0).getbName());
                    bundle.putString("brand",beans.get(0).getbBrand());
                    bundle.putString("status",beans.get(0).getbStatus());
                    Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                    intent.putExtra("data", bundle);
                    startActivity(intent);
                }
                break;
        }
    }

    /**
     * 打开扫描二维码页面
     */
    public void startScanQrCode(){
        Intent intent = new Intent();
        intent.setClass(this, MipcaActivityCapture.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }
}
