package com.neuedu;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ProductDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //productId
        int productId=getIntent().getIntExtra("productId",0);
        Toast.makeText(this,productId+"",Toast.LENGTH_LONG).show();


    }
}
