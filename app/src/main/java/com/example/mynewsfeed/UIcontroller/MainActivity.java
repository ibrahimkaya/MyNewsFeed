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
    NetworkActivity n =new NetworkActivity();
    private NewsViewModel mNewsViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        NewsParser.News ns = new NewsParser.News("","","","","","");
        n.loadPage();
        if(n.getFetchedNews() != null) {
            for(int i = 0; i<n.getFetchedNews().size();i++) {
                Log.d("donen", n.getFetchedNews().get(i).category);
                ns = n.getFetchedNews().get(i);
                News mNs = new News(ns.title, ns.description, ns.link, ns.pubDate, ns.creator, ns.category);
                mNewsViewModel.insert(mNs);
            }
        }
    }




}
