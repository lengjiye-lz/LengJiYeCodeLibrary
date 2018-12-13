package com.lengjiye.code.activity;


import android.util.Log;
import android.view.View;

import com.lengjiye.code.R;
import com.lengjiye.code.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 自动测试activity
 */
public class AutoTestActivity extends BaseActivity {

    @Override
    public int getResourceId() {
        return R.layout.activity_auto_test;
    }

    @Override
    protected void initViews() {
        super.initViews();

    }

    @Override
    protected void setListener() {
        super.setListener();
        setOnClickListener(findViewById(R.id.button));
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.button:
                getInfo();
            break;
        }
    }

    private void getInfo() {
        BufferedReader reader = null;
        String content = "";
        try {
            Process process = Runtime.getRuntime().exec("adb shell");
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuffer output = new StringBuffer();
            int read;
            char[] buffer = new char[1024];
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            reader.close();
            content = output.toString();
            Log.d("manman", "content = " + content);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
