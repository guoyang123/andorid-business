package com.neuedu;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neuedu.utils.OkHttpCallback;
import com.neuedu.utils.OkHttpUtils;
import com.neuedu.utils.SharedPreferencesUtil;
import com.neuedu.vo.ServerResponse;
import com.neuedu.vo.UserVO;

import java.io.IOException;
import java.util.logging.Logger;

import okhttp3.Call;
import okhttp3.Response;

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
                 OkHttpUtils.get("http://10.25.132.8:8080/portal/user/login.do?username="+username+"&password="+password,
                     new OkHttpCallback(){
                         @Override
                         public void onFinish(String status, String msg) {
                             super.onFinish(status, msg);
                             //解析数据
                             Gson gson=new Gson();
                             ServerResponse<UserVO> serverResponse=gson.fromJson(msg, new TypeToken<ServerResponse<UserVO>>(){}.getType());
                             int status1=serverResponse.getStatus();
                             if(status1==0){//登录成功
                                 //保存用户信息
//                             SharedPreferences sharedPreferences= LoginActivity.this.getSharedPreferences("userinfo",MODE_PRIVATE);
//                             SharedPreferences.Editor editor= sharedPreferences.edit();
//                             editor.putBoolean("isLogin",true);
//                             editor.putString("user",msg);
//                             editor.commit();

                                SharedPreferencesUtil util= SharedPreferencesUtil.getInstance(LoginActivity.this);
                                util.delete("isLogin");
                                util.delete("user");

                                util.putBoolean("isLogin",true);
                                util.putString("user",gson.toJson(serverResponse.getData()));
                                Boolean isLogin= util.readBoolean("isLogin");

                                //Activity跳转
                                 Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                 LoginActivity.this.startActivity(intent);

                             }else{
                                 Looper.prepare();
                                 Toast.makeText(LoginActivity.this,serverResponse.getMsg(),Toast.LENGTH_LONG).show();
                                 Looper.loop();
                             }




                         }
                     }    );
                 //解析接口返回的数据




         }

    }
}
