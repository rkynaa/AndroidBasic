package com.example.neardeal.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {//Key Pemanggilan Fungsi
    public static final String NAMA_APLIKASI="qtaasteelapp";
    public static final String USER_TOKEN="USER_TOKEN";
    public static final String USER_EMAIL="USER_EMAIL";
    public static final String USER_NAME="USER_NAME";
    public static final String IS_LOGGED_IN="IS_LOGGED_IN";


    //Define
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;


    public SharedPrefManager(Context context){

        sharedPreferences=context.getSharedPreferences(NAMA_APLIKASI,Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }


    public void setUserToken(String key,String nilai){
        editor.putString(key,nilai);
        editor.commit();
    }

    public String getUserToken(){
        return sharedPreferences.getString(USER_TOKEN,"");
    }

    public void setUserEmail(String key,String nilai){
        editor.putString(key,nilai);
        editor.commit();
    }

    public String getUserEmail(){
        return sharedPreferences.getString(USER_EMAIL,"");
    }

    public void setUserName(String key,String nilai){
        editor.putString(key,nilai);
        editor.commit();
    }

    public String getUserName(){
        return sharedPreferences.getString(USER_NAME,"");
    }

    public void setUserPosition(String key,String nilai){
        editor.putString(key,nilai);
        editor.commit();
    }


    public void setIsLoggedIn(String key,boolean value){
        editor.putBoolean(key,value);
        editor.commit();
    }

    public Boolean getIsLoggedIn() {
        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

}
