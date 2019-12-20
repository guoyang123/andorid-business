package com.neuedu;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.neuedu.fragment.CartFragment;
import com.neuedu.fragment.HomeFragment;
import com.neuedu.fragment.OrderFragment;
import com.neuedu.fragment.UserCenterFragment;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {


   LinearLayout home_LinearLayout;
    LinearLayout cart_LinearLayout;
    LinearLayout order_LinearLayout;
    LinearLayout center_LinearLayout;

    public static final String HOMEFRAGMENT_TAG="HOME";
    public static final String CARTFRAGMENT_TAG="CART";
    public static final String ORDERFRAGMENT_TAG="ORDER";
    public static final String CENTERFRAGMENT_TAG="CENTER";



    ImageView home_icon;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        home_LinearLayout=(LinearLayout)findViewById(R.id.home);
        cart_LinearLayout=(LinearLayout)findViewById(R.id.cart);
        order_LinearLayout=(LinearLayout)findViewById(R.id.order);
        center_LinearLayout=(LinearLayout)findViewById(R.id.center);
        home_icon=(ImageView)findViewById(R.id.home_icon);

        home_LinearLayout.setOnClickListener(this);
        cart_LinearLayout.setOnClickListener(this);
        order_LinearLayout.setOnClickListener(this);
        center_LinearLayout.setOnClickListener(this);
        Log.d("info","=========oncreate=====");


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("info","=========onstart=====");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("info","=========onrusume=====");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("info","=========onpause=====");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("info","=========ondestory=====");
    }


    @Override
    public void onClick(View v) {

         switch (v.getId()){
             case R.id.home:
                 attachFragment(HOMEFRAGMENT_TAG);
                 home_icon.setImageResource(R.mipmap.home_selected);

                 break;
             case R.id.cart:
                 attachFragment(CARTFRAGMENT_TAG);
                 break;
             case R.id.order:
                 attachFragment(ORDERFRAGMENT_TAG);
                 break;
             case R.id.center:
                 attachFragment(CENTERFRAGMENT_TAG);
                 break;
         }

    }


    private  void  attachFragment(String fragmentTag){

        //step1;获取Fragement管理器
        FragmentManager fragmentManager=getSupportFragmentManager();
        //开启事务
       FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

       Fragment fragment=fragmentManager.findFragmentByTag(fragmentTag);
       if(fragment==null){
           //管理器没有这个fragment
           if(fragmentTag.equals(HOMEFRAGMENT_TAG)){
               fragment=new HomeFragment();
               fragmentTransaction.add(fragment,HOMEFRAGMENT_TAG);
           }else if(fragmentTag.equals(CARTFRAGMENT_TAG)){
               fragment=new CartFragment();
               fragmentTransaction.add(fragment,CARTFRAGMENT_TAG);
           }else if(fragmentTag.equals(ORDERFRAGMENT_TAG)){
               fragment=new OrderFragment();
               fragmentTransaction.add(fragment,ORDERFRAGMENT_TAG);
           }else if(fragmentTag.equals(CENTERFRAGMENT_TAG)){
               fragment=new UserCenterFragment();
               fragmentTransaction.add(fragment,CENTERFRAGMENT_TAG);
           }
       }
       //添加Fragment

        fragmentTransaction.replace(R.id.content,fragment,fragmentTag);

        fragmentTransaction.commit();


    }

}
