package com.example.mynewsfeed.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import com.example.mynewsfeed.Repo.NewsRepository;
import com.example.mynewsfeed.RoomDb.News;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class NewsViewModel extends AndroidViewModel {

    private NewsRepository mRepository;
    private LiveData<List<News>> mAllNews;
    private LiveData<List<String>> allNonValidCategory;

    public NewsViewModel(Application application){
        super(application);
        mRepository = new NewsRepository(application);
        mAllNews = mRepository.getmAllNews();
    }

    public LiveData<List<News>> getmAllNews(){
        return mAllNews;
    }
    public LiveData<List<String>> getAllNonValidCategory() { return allNonValidCategory;}

    public void insert(News news){
        mRepository.insert(news);
    }

    public void deleteAll(){
        mRepository.deleteAll();
    }

    public void deleteWhereTitle(String title){
        mRepository.deleteWhereTitle(title);
    }

    public AsyncTask<String, Void, Boolean> getCategoryValidnes(String title){
        return mRepository.getCategoryValidnes(title);
    }

    public void setCategoryFalse( String category){
        mRepository.setCategoryFalse(category);
    }

    public void setCategoryTrue( String category){
        mRepository.setCategoryTrue(category);
    }

    public AsyncTask<String, Void, String> getPubDate(String title){
        return mRepository.getPubDate(title);
    }

    public AsyncTask<String, Void, String> getType(String title){
        return mRepository.getType(title);
    }

}
