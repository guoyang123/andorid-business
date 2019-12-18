package com.neuedu;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 登录UI
 * */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText username_editText ;
    EditText password_editText;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //获取
         username_editText=(EditText)findViewById(R.id.username);
        password_editText=(EditText)findViewById(R.id.password);
        Button login_button=(Button)findViewById(R.id.login);
        //注册点击事件
        login_button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

         switch (v.getId()){
             case  R.id.login:
                 //获取用户名
                    String username=username_editText.getText().toString();
                 //获取密码
                 String password=password_editText.getText().toString();
                 Toast.makeText(this,username,Toast.LENGTH_LONG).show();
                 //请求接口  ->  okhttp

                 //解析接口返回的数据
         }

    }
}
