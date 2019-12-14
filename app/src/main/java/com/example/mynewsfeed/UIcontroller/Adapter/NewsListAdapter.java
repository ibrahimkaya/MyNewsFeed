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
    private List<News> mNews; // Cached copy of words

    public NewsListAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public NewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.one_row, parent, false);
        return new NewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NewsViewHolder holder, int position) {
        if (mNews != null) {
            News current = mNews.get(position);
            holder.newsItemView.setText(current.getTitle());
        } else {
            // Covers the case of data not being ready yet.
            holder.newsItemView.setText("No Word");
        }
    }

    public void setNews(List<News> news){
        mNews = news;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mNews != null)
            return mNews.size();
        else return 0;
    }

    class NewsViewHolder extends RecyclerView.ViewHolder {
        private final TextView newsItemView;

        private NewsViewHolder(View itemView) {
            super(itemView);
            newsItemView = itemView.findViewById(R.id.news_header_tv);
        }
    }
}
