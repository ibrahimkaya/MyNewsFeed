package com.example.mynewsfeed.ViewModel;

import android.app.Application;

import com.example.mynewsfeed.Repo.BuildRepository;

import com.example.mynewsfeed.RoomDb.Build;


import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class BuildViewModel extends AndroidViewModel {
    private BuildRepository mRepository;
    private LiveData<List<Build>> mAllBuild;

    public BuildViewModel(Application application){
        super(application);
        mRepository = new BuildRepository(application);
        mAllBuild = mRepository.getAllBuildsFeature();
    }

    public LiveData<List<Build>> getAllBuildsFeature(){return mAllBuild;}

    public void insert(Build builds){
        mRepository.insert(builds);
    }

}
