package com.example.mynewsfeed.Repo;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mynewsfeed.RoomDb.News;
import com.example.mynewsfeed.RoomDb.NewsDao;
import com.example.mynewsfeed.RoomDb.NewsRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class NewsRepository {
    private NewsDao mNewsDao;
    private LiveData<List<News>> mAllNews;

    public NewsRepository(Application application){
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.getAllNews();
    }

    public LiveData<List<News>> getmAllNews(){
        return mAllNews;
    }

    public void insert(News...news){
        new insertAsyncTask(mNewsDao).execute(news);
    }

    private static class insertAsyncTask extends AsyncTask<News ,Void,Void>{
        private NewsDao mAsyncTaskDao;

        insertAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final News... params){
            for (News param : params) mAsyncTaskDao.insert(param);
            return null;
        }

    }

}
