package com.cowry.assetmanage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.cowry.assetmanage.R;
import com.cowry.assetmanage.app.Resolution;
import com.cowry.assetmanage.base.BaseActivity;

/**
 * Created by acer on 2016/6/27.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private Button btnLogin;
    private EditText etName;
    private EditText etPwd;
    private LoginTask loginTask;
    @Override
    public int setLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void findView() {
        btnLogin = (Button) findViewById(R.id.btnLogin);
        etName = (EditText) findViewById(R.id.etName);
        etPwd = (EditText) findViewById(R.id.etPwd);
    }

    @Override
    public void setListener() {
        btnLogin.setOnClickListener(this);
    }

    @Override
    public void underCreate() {
        ViewGroup.LayoutParams params  = btnLogin.getLayoutParams();
        params.width = (int) (Resolution.screenW*0.6);
        btnLogin.setLayoutParams(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnLogin:
                loginTask = new LoginTask();
                loginTask.execute();
                break;
            case R.id.tvSetting:
                break;
        }
    }

    private class LoginTask extends AsyncTask<Void,Void,Boolean>{

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
            loginTask=null;
            cancelWaiting();
            if (!etName.getText().toString().equals("admin")){
                etName.requestFocus();
                etName.setError("默认账户为admin");
                return;
            }
            if (!etPwd.getText().toString().equals("12345")){
                etPwd.requestFocus();
                etPwd.setError("默认密码为12345");
                return;
            }
            startActivity(new Intent(LoginActivity.this,MainActivity.class));
            finish();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            /**
             * 取消任务的处理  任务重置
             */
            loginTask=null;
        }
    }

    @Override
    public void onBackPressed() {
        if (loginTask!=null){
            /**取消任务*/
            loginTask.cancel(true);
            return;
        }
        super.onBackPressed();
    }
}
