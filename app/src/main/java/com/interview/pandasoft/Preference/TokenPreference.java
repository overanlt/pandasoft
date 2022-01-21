package com.interview.pandasoft.Preference;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

public class TokenPreference {

    public static  void SaveAccessToken(String access_token, Context context){
        SharedPreferences.Editor editor = context.getSharedPreferences("access_token", MODE_PRIVATE).edit();
        editor.putString("access_token",access_token);
        editor.apply();
    }
    public static String RetrievedAccessToken(Context context){
        SharedPreferences prefs = context.getSharedPreferences("access_token", MODE_PRIVATE);
        return prefs.getString("access_token",null);
    }

}
