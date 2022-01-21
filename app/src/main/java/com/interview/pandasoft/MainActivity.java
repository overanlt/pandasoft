package com.interview.pandasoft;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.interview.pandasoft.Adapter.NewsAdapter;
import com.interview.pandasoft.Api.Api;
import com.interview.pandasoft.Model.ApiResponeModel;
import com.interview.pandasoft.Model.DataApiResponse;

public class MainActivity extends BaseActivity {

    RecyclerView rvNews;
    NewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvNews = findViewById(R.id.rvNews);
        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        showProgressDialog(MainActivity.this);
        new Api().GetNewsApi(getApplicationContext(), new Api.onApiResponse() {
            @Override
            public void _onApiResponse(ApiResponeModel response) {
                hideProgressDialog();
                if(response.getStatus() == 200){
                    adapter = new NewsAdapter(getApplicationContext(), response.getData(), new NewsAdapter.onClickNews() {
                        @Override
                        public void onClickNewsResult(DataApiResponse response) {
                            Intent intent = new Intent(MainActivity.this,NewsDetailActivity.class);
                            intent.putExtra("news",new Gson().toJson(response));
                            startActivity(intent);
                        }
                    });
                    rvNews.setAdapter(adapter);
                }else {
                    showAlertDialog(MainActivity.this,response.getMessage());
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }

    @Override
    protected void onPause() {
        super.onPause();
        timer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startTimer();
    }
}