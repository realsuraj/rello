package com.tiwari.suraj.rello;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefConfig {
    private static final String MY_PREFERENCE_NAME = "com.example.yoga.shared_preference";
    public static final String PREF_USERNAME = "pref_username";
    public  static String username;

    public static void saveUsername(Context context, String username){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PREF_USERNAME,username);
        editor.apply();
    };
    public static String LoadUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_PREFERENCE_NAME,Context.MODE_PRIVATE);
        return  sharedPreferences.getString(PREF_USERNAME,null);
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String usernamei) {
        username = usernamei;
    }
}
