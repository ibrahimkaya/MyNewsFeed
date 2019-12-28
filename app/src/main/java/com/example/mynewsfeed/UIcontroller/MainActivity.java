package com.example.mynewsfeed.UIcontroller;


import android.content.Intent;
import android.os.Bundle;

import com.example.mynewsfeed.Parser.LastBuildDateParser;
import com.example.mynewsfeed.Parser.NetworkActivity;
import com.example.mynewsfeed.Parser.NewsParser;
import com.example.mynewsfeed.R;
import com.example.mynewsfeed.RoomDb.Build;
import com.example.mynewsfeed.RoomDb.News;
import com.example.mynewsfeed.UIcontroller.Adapter.NewsListAdapter;
import com.example.mynewsfeed.ViewModel.BuildViewModel;
import com.example.mynewsfeed.ViewModel.NewsViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.List;

public class MainActivity extends AppCompatActivity implements NetworkActivity.AsyncResponse, OnItemSelectedListener {

    NetworkActivity networkActivity =new NetworkActivity();
    private NewsViewModel mNewsViewModel;

    private BuildViewModel mBuildViewModel;

    public static String typeCoice = "world"; // default news source type
    Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recyclerLayout);
        final NewsListAdapter adapter = new NewsListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //
        networkActivity.delegate = this;

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        mBuildViewModel = ViewModelProviders.of(this).get(BuildViewModel.class);

        mNewsViewModel.getmAllNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                adapter.setNews(news);
            }
        });

        ItemTouchHelper helper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT |ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                        int position = viewHolder.getAdapterPosition();
                        News mNews = adapter.getNewsAtPosition(position);

                        switch (direction){
                            case ItemTouchHelper.LEFT:
                                readNews(mNews);
                                break;
                            case ItemTouchHelper.RIGHT:
                                break;
                        }
                        adapter.notifyItemChanged(position);
                    }
                }
        );
        helper.attachToRecyclerView(recyclerView);

        typeSpinner =  findViewById(R.id.news_type_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.newsType, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        typeSpinner.setAdapter(spinnerAdapter);
        typeSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //handle selected item action
        startMenuACtion(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    public void button(View view) {
        Toast.makeText(this,"clearing all data! ", Toast.LENGTH_LONG).show();
        mNewsViewModel.deleteAll();
    }

    public void fetchNewsAndRoom(){
        //chooser set by activity selected news type
        networkActivity.chooseNetworkActivity(typeCoice);
        //start loading  xml
        networkActivity.loadPage();
    }

    //fetch builds, for further implementations
    public void fetchBuildsAndRoom(){
        networkActivity.chooseNetworkActivity("build");
        networkActivity.loadPage();
    }


    public void startMenuACtion(int id){
        Intent intent;

        switch (id){
            case R.id.ignored_source_menuItem:
                 intent = new Intent(this,ListOfIgnoredSourceActivity.class);
                 startActivity(intent);
                 break;
            case R.id.action_settings:
                 //intent = new Intent(this,Settings.class);
                 break;
            case R.id.refresh_menu:
                fetchNewsAndRoom();
                break;
        }
    }

    public void readNews(News mNews){
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("title",mNews.getTitle());
        intent.putExtra("description",mNews.getDescription());
        intent.putExtra("link",mNews.getLink());
        startActivity(intent);
    }

    @Override
    public void processFinish(String output){
        // onPostExecute(result)

        NewsParser.News fetchedNews;
        News mTempNews;
            //generate entity object for item list size
        if(networkActivity.getFetchedNews() != null)
            for(int i = 0; i< networkActivity.getFetchedNews().size(); i++) {
                //get ith element
                fetchedNews = networkActivity.getFetchedNews().get(i);
                mTempNews  = new News(fetchedNews.title,
                        fetchedNews.description,
                        fetchedNews.link,
                        fetchedNews.pubDate,
                        fetchedNews.creator,
                        fetchedNews.category,
                        typeCoice);
                //insert my entity
                mNewsViewModel.insert(mTempNews);
            }

        LastBuildDateParser.BuildFeatures fetchedBuilds;
        Build mBuildTemp;

        if(networkActivity.getFetchedBuilds() != null){

            for(int i = 0; i< networkActivity.getFetchedBuilds().size(); i++){
                fetchedBuilds = networkActivity.getFetchedBuilds().get(i);
                mBuildTemp = new Build(fetchedBuilds.title,
                        fetchedBuilds.lastBuildDate);
                mBuildViewModel.insert(mBuildTemp);
            }
        }
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        typeCoice = typeSpinner.getSelectedItem().toString();
        Log.d("ozellikler",typeSpinner.getSelectedItem().toString() +" bu secilen type " );
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
