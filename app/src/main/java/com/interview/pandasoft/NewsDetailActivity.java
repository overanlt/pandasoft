package com.interview.pandasoft;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.interview.pandasoft.Api.Api;
import com.interview.pandasoft.Model.ApiResponeModel;
import com.interview.pandasoft.Model.DataApiResponse;
import com.interview.pandasoft.Model.LikeModel;
import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends BaseActivity implements View.OnClickListener {

    ImageView imgNews;
    TextView tvNewsName,tvNewsDetail,tvNewsDate;
    LinearLayout layoutLike;
    DataApiResponse data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        imgNews = findViewById(R.id.imgNews);
        tvNewsName = findViewById(R.id.tvNewsName);
        tvNewsDetail = findViewById(R.id.tvNewsDetail);
        tvNewsDate = findViewById(R.id.tvNewsDate);
        layoutLike = findViewById(R.id.layoutLike);

        data = new Gson().fromJson(getIntent().getStringExtra("news"),DataApiResponse.class);

        Picasso.get().load(data.getImage()).error(getDrawable(R.drawable.failed)).into(imgNews);
        tvNewsName.setText(data.getTitle());
        tvNewsDetail.setText(data.getDetail());
        tvNewsDate.setText(getDate(data.getCreate()));
        layoutLike.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.layoutLike){
            showProgressDialog(NewsDetailActivity.this);
            new Api().LikeApi(new LikeModel(Integer.parseInt(data.getId())), new Api.onApiResponse() {
                @Override
                public void _onApiResponse(ApiResponeModel response) {
                    hideProgressDialog();
                    showAlertDialog(NewsDetailActivity.this,response.getMessage());
                }
            });
        }
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