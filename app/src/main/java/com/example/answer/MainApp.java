package com.example.answer;

import android.app.Application;

import com.karumi.dexter.Dexter;


public class MainApp extends Application {


    public static String mLongitude = "0";// 经度
    public static String mLatitude = "0"; // 纬度
    public static String mAddress = "";// 地址

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        //权限初始化
        Dexter.initialize(this);

    }

}
