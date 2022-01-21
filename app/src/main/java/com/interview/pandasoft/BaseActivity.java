package com.interview.pandasoft;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Locale;

public class BaseActivity  extends AppCompatActivity {

    private ProgressDialog mProgressDialogLoader;
    CountDownTimer timer = null;
    private int TIME_EXPIRE = 10*1000;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void showProgressDialog(Context context) {
        if (mProgressDialogLoader == null) {
            mProgressDialogLoader = new ProgressDialog(context);
            mProgressDialogLoader.setMessage("Loading");
            mProgressDialogLoader.setIndeterminate(true);
            mProgressDialogLoader.setCancelable(false);
            mProgressDialogLoader.show();
        }
    }

    public void hideProgressDialog() {
        if (!BaseActivity.this.isFinishing() && mProgressDialogLoader != null) {
            mProgressDialogLoader.dismiss();
            mProgressDialogLoader = null;
        }
    }

    public void showAlertDialog(Context context, String msg){
        new AlertDialog.Builder(context)
                .setMessage(msg)
                .setNegativeButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create()
                .show();
    }

    public String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd/MM/yyyy", cal).toString();
        return date;
    }

    public void startTimer(){
        if (timer == null) {
            timer = new CountDownTimer(TIME_EXPIRE,1000) {
                @Override
                public void onTick(long l) {
                    Log.d("TAG", "onTick: "+l);
                }

                @Override
                public void onFinish() {
                    ActivityManager am = (ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
                    ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
                    boolean a = !cn.getShortClassName().contains("LoginActivity");
                    if(a) {
                        Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            }.start();
        }else {
            timer.start();
        }
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        if(timer != null){
            timer.cancel();
            timer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
    }
}
