package com.neuedu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.neuedu.utils.OkHttpCallback;
import com.neuedu.utils.OkHttpUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraActivity extends AppCompatActivity {

    Button camera_button;
    ImageView img_iv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        camera_button=(Button)findViewById(R.id.camera);
        img_iv=(ImageView)findViewById(R.id.img_iv);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              startCamera();
            }
        });
    }



    public Uri imageUri;
    public  File file;
    public  static final int REQUEST_CODE_CAMERA=1;
    //启动相机
    public  void  startCamera(){



        Intent intent=new Intent("android.media.action.IMAGE_CAPTURE");

        intent.putExtra(MediaStore.EXTRA_OUTPUT, getImageUri(getFile()));

        startActivityForResult(intent,REQUEST_CODE_CAMERA);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode==REQUEST_CODE_CAMERA){
            if(resultCode==RESULT_OK){
               crop(imageUri); //图片裁剪
            }

        }else  if(requestCode==PHOTO_REQUEST_CUT){
            if(data!=null){
               Bitmap bitmap= data.getParcelableExtra("data");

               img_iv.setImageBitmap(bitmap);


               //bitmap转成File
                file=bitmap2File(bitmap);
                OkHttpUtils.upload("http://172.24.36.1:8080/upload",file.getAbsolutePath(),new OkHttpCallback(){
                    @Override
                    public void onFinish(String status, String msg) {
                        super.onFinish(status, msg);
                        Log.e("com.neuedu",msg);
                    }
                });



            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    public static final int  PHOTO_REQUEST_CUT=10;
    /*
     * 剪切图片
     */
    public void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    public  File  getFile(){

        file=new File(getExternalCacheDir(),"temp_photo.png");
        if(file.exists()){
            file.delete();
        }
         try {
             file.createNewFile();

         } catch (IOException e) {
             e.printStackTrace();
         }
         return file;

     }


    public  Uri getImageUri(File file){

       if(Build.VERSION.SDK_INT>=24){
           //FileProvider获取
          imageUri= FileProvider.getUriForFile(this,"com.neuedu.fileprovider",file);

       } else{
           imageUri=Uri.fromFile(file);
       }

       return imageUri;
    }



    /**Bitmap转File*/

    public File bitmap2File(Bitmap bm)  {//将Bitmap类型的图片转化成file类型，便于上传到服务器

        BufferedOutputStream bos = null;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(file));
            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file;

    }


}
