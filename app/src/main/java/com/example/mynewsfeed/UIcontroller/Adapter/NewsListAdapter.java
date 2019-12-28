package com.example.mynewsfeed.UIcontroller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mynewsfeed.R;
import com.example.mynewsfeed.RoomDb.News;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {

    private final LayoutInflater mInflater;
    private List<News> mNews; // Cached copy of news

    public NewsListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.one_row, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if (mNews != null ) {
            News current = mNews.get(position);
            holder.newsHeaderTv.setText(current.getTitle());
            holder.newsDecsTV.setText(current.getDescription());
        }
    }

    public void setNews(List<News> news){
        mNews = news;
        notifyDataSetChanged();
    }

    public News getNewsAtPosition(int position){
        return mNews.get(position);
    }
    @Override
    public int getItemCount() {
        if (mNews != null)
            return mNews.size();
        else return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private final TextView newsHeaderTv;
        private final TextView newsDecsTV;

        private NewsViewHolder(View itemView) {
            super(itemView);
            newsHeaderTv = itemView.findViewById(R.id.news_header_tv);
            newsDecsTV = itemView.findViewById(R.id.news_desc_tv);
        }
    }
}
