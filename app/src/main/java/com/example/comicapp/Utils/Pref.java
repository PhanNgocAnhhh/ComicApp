package com.example.comicapp.Utils;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class Pref {
    private static SharedPreferences preferences;
    private static final String PrefName= "PREF_NAME";
    public static final String TAIKHOAN = "taikhoan";
    public static final String MATKHAU= "";

    //Lưu dữ liệu vào SharedPreferences
    public static void setString(Context context, String name, String value){
        preferences = context.getSharedPreferences(PrefName,MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(name,value);
        editor.apply();

    }
    //Lấy dữ liệu từ SharedPreferences
    public static String getString(Context context, String name, String non){
        preferences = context.getSharedPreferences(PrefName,MODE_PRIVATE);
        return preferences.getString(name,non);

    }


}
