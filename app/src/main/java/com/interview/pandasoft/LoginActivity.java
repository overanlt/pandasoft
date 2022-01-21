package com.interview.pandasoft;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.interview.pandasoft.Api.Api;
import com.interview.pandasoft.Model.ApiResponeModel;
import com.interview.pandasoft.Model.LoginModel;
import com.interview.pandasoft.Preference.TokenPreference;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    EditText  etUser,etPassword;
    Button btnLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.btnLogin){
            showProgressDialog(LoginActivity.this);
            new Api().LoginApi(getApplicationContext(), new LoginModel(etUser.getText().toString(), etPassword.getText().toString()), new Api.onApiResponse() {
                @Override
                public void _onApiResponse(ApiResponeModel response) {
                    hideProgressDialog();
                    if(response.getStatus() == 200){
                        TokenPreference.SaveAccessToken(response.getAccess_token(),getApplicationContext());
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    }else {
                        showAlertDialog(LoginActivity.this,response.getMessage());
                    }
                }
            });
        }
    }


}