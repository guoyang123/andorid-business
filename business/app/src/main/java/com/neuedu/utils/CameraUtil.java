package com.neuedu.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.neuedu.CameraActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CameraUtil {

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    public static Uri imageUri;
    public static File tempFile;
    public static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    public static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    public static final int PHOTO_REQUEST_CUT = 3;// 结果

    private  static CameraUtil cameraUtil=null;
    private CameraUtil(){}


    public static CameraUtil getInstance(){
        if(cameraUtil==null){
            synchronized (CameraUtil.class){
                if(cameraUtil==null){
                    cameraUtil=new CameraUtil();
                }
            }
        }
        return cameraUtil;
    }
    public  void  camera(Activity context){

        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
             tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
            // 从文件中创建uri
            if(Build.VERSION.SDK_INT>=24){
                imageUri= FileProvider.getUriForFile(context,
                        context.getPackageName()+".fileprovider",
                        tempFile);
            }else {
                imageUri=Uri.fromFile(tempFile);
            }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA

            context.startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
        }else{
            Toast.makeText(context, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
        }


    }

    /*
     * 判断sdcard是否被挂载
     */
    public boolean hasSdcard() {
        //判断ＳＤ卡手否是安装好的　　　media_mounted
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /*
     * 剪切图片
     */
    public void crop(Activity activity,Uri uri) {


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
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        activity.startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    //保存图片到SharedPreferences
    public void saveBitmapToSharedPreferences(Context context,Bitmap bitmap) {
//        // Bitmap bitmap=BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
//        //第一步:将Bitmap压缩至字节数组输出流ByteArrayOutputStream
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
//        //第二步:利用Base64将字节数组输出流中的数据转换成字符串String
//        byte[] byteArray = byteArrayOutputStream.toByteArray();
//        String imageString = new String(Base64.encodeToString(byteArray, Base64.DEFAULT));
//        //第三步:将String保持至SharedPreferences
//        SharedPreferences sharedPreferences = context.getSharedPreferences("testSP", Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("image", imageString);
//        editor.commit();

        //上传头像
      //  setImgByStr(imageString,"");
    }


    /**
     * 上传头像       此处使用用的OKHttp post请求上传的图片

     */
    public  void setImgByStr() {




    }

}
