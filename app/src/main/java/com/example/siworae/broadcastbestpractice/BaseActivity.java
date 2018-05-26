package com.example.siworae.broadcastbestpractice;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by siworae on 2018/5/24.
 *创建所有活动的父类
 */

public class BaseActivity extends AppCompatActivity{

    private ForceOfflineReceiver receiver;
    @Override
    //创建活动时调用的函数
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
    //销毁活动之前调用的函数
    protected void onDestroy(){
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
    //处于栈顶时调用
    protected void onResume(){
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.example.broadcastbestpractice.FORCE_OFFLINE");
        receiver = new ForceOfflineReceiver();
        registerReceiver(receiver,intentFilter);
    }
    //启动另一个活动时调用
    protected void onPause(){
        super.onPause();
        if (receiver!=null){
            unregisterReceiver(receiver);
            receiver=null;
        }
    }
    //广播接收器
    class ForceOfflineReceiver extends BroadcastReceiver{
        public void onReceive(final Context context, Intent intent){
        //构建对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("错误");
            builder.setMessage("你已被强制下线，请重新登陆。");
            //设置对话框不可取消
            builder.setCancelable(false);
            //对话框确定按钮事件
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //销毁所有活动
                    ActivityCollector.finishAll();
                    //重新启动LoginActivity
                    LoginActivity.LoginActivityStart(BaseActivity.this);

                }
            });
            builder.show();
        }
    }
}
