package com.example.siworae.broadcastbestpractice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends BaseActivity {
    private EditText accountEdit;
    private EditText passwordEdit;
    private Button login;
    private CheckBox rememberPass;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public static void LoginActivityStart(Context context){
        Intent intent = new Intent(context,LoginActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //获取SharedPreferences实例
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        accountEdit = findViewById(R.id.account);
        passwordEdit = findViewById(R.id.password);
        rememberPass = findViewById(R.id.checkbox);
        login = findViewById(R.id.login);
        //获取复选框存储的值
        boolean isRemember = pref.getBoolean("rememberpassword",false);
        //判断复选框是否选中
        if (isRemember){
            //读取储存的账号密码数据
            String account = pref.getString("account","");
            String password = pref.getString("password","");
            //设置到输入框中
            accountEdit.setText(account);
            passwordEdit.setText(password);
            rememberPass.setChecked(true);
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框数据
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                if(account.equals("admin")&&password.equals("123456")){
                    editor = pref.edit();
                    if (rememberPass.isChecked()) {                             //判断复选框是否选中
                        //将复选框状态，账号密码数据储存
                        editor.putBoolean("rememberpassword", true);
                        editor.putString("account",account);
                        editor.putString("password",password);
                    }else {
                        //清除数据
                        editor.clear();
                    }
                    //提交
                    editor.apply();
                    MainActivity.MainActivityStart(LoginActivity.this);//启动活动MainActivity
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
