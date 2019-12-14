package com.example.mynewsfeed.UIcontroller;

import android.os.Bundle;

import com.example.mynewsfeed.Parser.NetworkActivity;
import com.example.mynewsfeed.Parser.NewsParser;
import com.example.mynewsfeed.R;
import com.example.mynewsfeed.RoomDb.News;
import com.example.mynewsfeed.UIcontroller.Adapter.NewsListAdapter;
import com.example.mynewsfeed.ViewModel.NewsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NetworkActivity networkActivity =new NetworkActivity();
    private NewsViewModel mNewsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerLayout);
        final NewsListAdapter adapter = new NewsListAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

        mNewsViewModel.getmAllNews().observe(this, new Observer<List<News>>() {
            @Override
            public void onChanged(List<News> news) {
                adapter.setNews(news);
            }
        });
        loadXml();
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void button(View view) {
        parserToRoom();
    }

    public void parserToRoom(){
        NewsParser.News fetchedNews;
        News mTempNews;
        //start parsing xml
        networkActivity.loadPage();
        //if parsing complate
        if(networkActivity.getFetchedNews() != null){
            //generate entity onbject for item list size
            for(int i = 0; i< networkActivity.getFetchedNews().size(); i++) {
                //get ith element
                fetchedNews = networkActivity.getFetchedNews().get(i);
                mTempNews  = new News(fetchedNews.title,
                                    fetchedNews.description,
                                    fetchedNews.link,
                                    fetchedNews.pubDate,
                                    fetchedNews.creator,
                                    fetchedNews.category);
                //insert my entity
                mNewsViewModel.insert(mTempNews);
            }
        }
    }

    public void loadXml(){
        networkActivity.loadPage();
    }

}
