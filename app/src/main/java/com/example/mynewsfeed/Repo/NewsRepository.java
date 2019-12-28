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
    private LiveData<List<String>> allNonValidCategory;
    private LiveData<List<News>> scienceNews;
    private LiveData<List<News>> sportNews;
    private LiveData<List<News>> worldNews;



    public NewsRepository(Application application){
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        mNewsDao = db.newsDao();
        mAllNews = mNewsDao.getAllNews();
        scienceNews = mNewsDao.getAllNewsScience();
        sportNews = mNewsDao.getAllNewsSport();
        worldNews = mNewsDao.getAllNewsWorld();
    }

    public LiveData<List<News>> getmAllNews(){
        return mAllNews;
    }

    public LiveData<List<News>> getScienceNews(){
        return scienceNews;
    }
    public LiveData<List<News>> getSportNews(){
        return sportNews;
    }
    public LiveData<List<News>> getWorldNews(){
        return worldNews;
    }

    public LiveData<List<String>> allNonValidCategory() { return allNonValidCategory;}

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

    public void deleteAll(){
        new deleteAllAsyncTask(mNewsDao).execute();
    }

    private static class deleteAllAsyncTask extends AsyncTask<News ,Void,Void>{
        private NewsDao mAsyncTaskDao;

        deleteAllAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final News... params){
            mAsyncTaskDao.deleteAll();
            return null;
        }

    }

    public void deleteWhereTitle(String title){
        new deleteWhereTitleAsyncTask(mNewsDao).execute(title);
    }

    private static class deleteWhereTitleAsyncTask extends AsyncTask<String ,Void,Void>{
        private NewsDao mAsyncTaskDao;

        deleteWhereTitleAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params){
            for (String param : params) mAsyncTaskDao.deleteWhereTitle(param);
            return null;
        }

    }

    public AsyncTask<String, Void, Boolean> getCategoryValidnes(String title){
       return new getCategoryValidnesAsyncTask(mNewsDao).execute(title);
    }

    private static class getCategoryValidnesAsyncTask extends AsyncTask<String ,Void,Boolean>{
        private NewsDao mAsyncTaskDao;

        getCategoryValidnesAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Boolean doInBackground(final String... params){
            return mAsyncTaskDao.getCategoryValidnes(params[0]);
        }

    }

    public void setCategoryFalse( String category){
        new setCategoryFalseAsyncTask(mNewsDao).execute( category);
    }

    private static class setCategoryFalseAsyncTask extends AsyncTask<String ,Void,Void>{
        private NewsDao mAsyncTaskDao;

        setCategoryFalseAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params){
            for (String param : params) mAsyncTaskDao.setCategoryFalse(param);
            return null;
        }

    }


    public void setCategoryTrue( String category){
        new setCategoryTrueAsyncTask(mNewsDao).execute( category);
    }

    private static class setCategoryTrueAsyncTask extends AsyncTask<String ,Void,Void>{
        private NewsDao mAsyncTaskDao;

        setCategoryTrueAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final String... params){
            for (String param : params) mAsyncTaskDao.setCategoryTrue(param);
            return null;
        }

    }


    public AsyncTask<String, Void, String> getPubDate(String title){
        return new getPubDateAsyncTask(mNewsDao).execute(title);
    }

    private static class getPubDateAsyncTask extends AsyncTask<String ,Void,String>{
        private NewsDao mAsyncTaskDao;

        getPubDateAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(final String... params){
            return mAsyncTaskDao.getPubDate(params[0]);
        }

    }

    public AsyncTask<String, Void, String> getType(String title){
        return new getTypeAsyncTask(mNewsDao).execute(title);
    }

    private static class getTypeAsyncTask extends AsyncTask<String ,Void,String>{
        private NewsDao mAsyncTaskDao;

        getTypeAsyncTask(NewsDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected String doInBackground(final String... params){
            return mAsyncTaskDao.getType(params[0]);
        }

    }

}
