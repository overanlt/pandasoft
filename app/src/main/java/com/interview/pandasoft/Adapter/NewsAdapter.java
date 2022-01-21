package com.interview.pandasoft.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.interview.pandasoft.Model.DataApiResponse;
import com.interview.pandasoft.R;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder> {

    Context context;
    LayoutInflater inflater;
    List<DataApiResponse> dataInfos;
    private static final int TYPE_EMPTY = 0;
    private static final int TYPE_LIST = 1;
    boolean isEmpty = true;
    onClickNews clickNews;

    public NewsAdapter(Context context, List<DataApiResponse> dataInfos , onClickNews clickNews) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.dataInfos = dataInfos;
        this.clickNews = clickNews;
    }

    public interface onClickNews{
        void onClickNewsResult(DataApiResponse response);
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == TYPE_EMPTY){
            View view = inflater.inflate(R.layout.adapter_empty,parent,false);
            ItemHolder holder  = new ItemHolder(view,viewType);
            return  holder;
        }else if(viewType == TYPE_LIST) {
            View view = inflater.inflate(R.layout.adapter_news, parent, false);
            ItemHolder holder = new ItemHolder(view,viewType);
            return holder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        if(!isEmpty){
            holder.tvNews.setText(dataInfos.get(position).getTitle());
            Picasso.get().load(dataInfos.get(position).getImage()).error(context.getDrawable(R.drawable.failed)).into(holder.imgNews);
        }
    }

    @Override
    public int getItemCount() {
        if((dataInfos == null) || (dataInfos.size() == 0)){
            isEmpty = true;
            return 1;
        }else{
            isEmpty = false;
            return dataInfos.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isEmpty){
            return TYPE_EMPTY;
        }else{
            return TYPE_LIST;
        }
    }

    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        int view_type;

        //--- layout data
        ImageView imgNews;
        TextView tvNews;
        LinearLayout layout;

        //--- layout no data
        TextView tvEmptyDesc;

        public ItemHolder(@NonNull View itemView,int viewType) {
            super(itemView);
            if(viewType == TYPE_EMPTY){
                view_type = 0;
            }else {
                view_type = 1;
                imgNews = itemView.findViewById(R.id.imgNews);
                tvNews = itemView.findViewById(R.id.tvNewsName);
                layout = itemView.findViewById(R.id.layout);

                layout.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            if(id == R.id.layout){
                clickNews.onClickNewsResult(dataInfos.get(getLayoutPosition()));
            }
        }
    }
}
