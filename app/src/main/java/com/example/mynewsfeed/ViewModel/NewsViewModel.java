package com.example.mynewsfeed.ViewModel;

import android.app.Application;

import com.example.mynewsfeed.Repo.NewsRepository;
import com.example.mynewsfeed.RoomDb.News;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;
    private LiveData<List<News>> mAllNews;

    public NewsViewModel(Application application){
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getmAllNews();
    }

    public LiveData<List<News>> getmAllNews(){
        return mAllNews;
    }

    public void insert(News news){
        mRepository.insert(news);
    }
}
