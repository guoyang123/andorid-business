package com.neuedu.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.neuedu.R;
import com.neuedu.utils.SharedPreferencesUtil;
import com.neuedu.vo.UserVO;

public class UserCenterFragment extends Fragment {

    TextView info;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=  inflater.inflate(R.layout.center_fragment,container,false);
         info=view.findViewById(R.id.info);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        //判断用户是否登录
       Boolean isLogin= SharedPreferencesUtil.getInstance(getActivity()).readBoolean("isLogin");
       if(isLogin){
           //已经登录
           //获取用户信息
           UserVO userVO=(UserVO) SharedPreferencesUtil.getInstance(getActivity()).readObject("user", UserVO.class);
           info.setText(userVO.getUsername());
       }

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
