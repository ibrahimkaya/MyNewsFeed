package com.example.mynewsfeed.Repo;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mynewsfeed.RoomDb.Build;
import com.example.mynewsfeed.RoomDb.BuildDao;
import com.example.mynewsfeed.RoomDb.News;
import com.example.mynewsfeed.RoomDb.NewsDao;
import com.example.mynewsfeed.RoomDb.NewsRoomDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class BuildRepository {
    private BuildDao mbuildDao;
    private LiveData<List<Build>> allBuilds;

    public BuildRepository (Application application){
        NewsRoomDatabase db = NewsRoomDatabase.getDatabase(application);
        mbuildDao = db.buildDao();
        allBuilds = mbuildDao.getAllBuildFeature();
    }

    public LiveData<List<Build>> getAllBuildsFeature(){return allBuilds;}

    public void insert(Build...builds){
        new BuildRepository.insertAsyncTask(mbuildDao).execute(builds);
    }
    private static class insertAsyncTask extends AsyncTask<Build,Void,Void> {
        private BuildDao mAsyncTaskDao;

        insertAsyncTask(BuildDao dao){
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Build... params){
            for (Build param : params) mAsyncTaskDao.insert(param);
            return null;
        }

    }

}
