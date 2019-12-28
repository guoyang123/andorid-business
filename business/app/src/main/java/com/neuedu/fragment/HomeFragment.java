package com.neuedu.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.neuedu.ProductDetailActivity;
import com.neuedu.R;
import com.neuedu.adapter.MyViewPagerAdapter;
import com.neuedu.utils.OkHttpCallback;
import com.neuedu.utils.OkHttpCallbackFile;
import com.neuedu.utils.OkHttpUtils;
import com.neuedu.vo.ProductListVO;
import com.neuedu.vo.ServerResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements View.OnClickListener {

    ViewPager viewPager;
    List<ImageView> viewList=new ArrayList<>();
    PagerAdapter pagerAdapter;
    private  static final int CARSOUEL_NOTIFY=1;
    private  static final int LOAD_PIC=2;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {

            switch (msg.what){
                case CARSOUEL_NOTIFY:
                    List<ProductListVO> productListVOLsit=(List<ProductListVO>)msg.obj;
                    //创建view,渲染到viewpager上
                    render(productListVOLsit);

                    break;
                case LOAD_PIC:
                    ImageView imageView=viewList.get(msg.arg1);
                    byte[] contents=(byte[])msg.obj;
                    Bitmap bitmap=BitmapFactory.decodeByteArray(contents,0,contents.length);
                    imageView.setImageBitmap(bitmap);
                    break;
            }

        }
    };

    private  void  render(List<ProductListVO> productListVOLsit){

        for(int i=0;i<productListVOLsit.size();i++){
            ProductListVO productListVO=productListVOLsit.get(i);
            String uri=productListVO.getMainImage();

            final ImageView imageView=new ImageView(getActivity());
            imageView.setId(productListVO.getId());
            imageView.setOnClickListener(this);
            viewList.add(imageView);
            OkHttpUtils.get("http://img.cdn.imbession.top/"+uri,new OkHttpCallbackFile(i){
                @Override
                public void onFinish(String status, byte[] msg,int position) {

                      Message message=mHandler.obtainMessage();
                      message.what=LOAD_PIC;
                      message.arg1=position;
                      message.obj=msg;

                      mHandler.sendMessage(message);
                }
            });
            //url okhttp-->byte[] ->BitmapFactory -->Bitmap








        }

        pagerAdapter=new MyViewPagerAdapter(viewList);
        viewPager.setAdapter(pagerAdapter);



    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.home_fragment,container,false);

         viewPager=view.findViewById(R.id.casouelviewpager);

//         List<View> viewList=new ArrayList<>();
//        ImageView imageView=new ImageView(getActivity());
//        imageView.setImageResource(R.mipmap.cart);
//        viewList.add(imageView);
//
//        ImageView imageView2=new ImageView(getActivity());
//        imageView2.setImageResource(R.mipmap.order);
//        viewList.add(imageView2);
//
//         MyViewPagerAdapter viewPagerAdapter=new MyViewPagerAdapter(viewList);
//         viewPager.setAdapter(viewPagerAdapter);

         //获取接口数据


        OkHttpUtils.get("http://172.24.36.1:8080/portal/product/carsouel.do",new OkHttpCallback(){
            @Override
            public void onFinish(String status, String msg) {

                //解析数据
                Gson gson=new Gson();
                ServerResponse<List<ProductListVO>> serverResponse=gson.fromJson(msg, new TypeToken<ServerResponse<List<ProductListVO>>>(){}.getType());
                int status1=serverResponse.getStatus();
                if(status1==0){
                    //获取数据
                    List<ProductListVO> productListVOS=serverResponse.getData();

                    Message message=new Message();
                    message.what=CARSOUEL_NOTIFY;
                    message.obj=productListVOS;
                    mHandler.sendMessage(message);
                }


            }
        });

        return  view;
    }


    @Override
    public void onClick(View v) {


        Intent intent=new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra("productId",v.getId());
        startActivity(intent);

    }
}
