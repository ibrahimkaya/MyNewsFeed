package com.example.mynewsfeed.UIcontroller;

import android.content.Intent;
import android.os.Bundle;

import com.example.mynewsfeed.Parser.NetworkActivity;
import com.example.mynewsfeed.Parser.NewsParser;
import com.example.mynewsfeed.R;
import com.example.mynewsfeed.RoomDb.News;
import com.example.mynewsfeed.UIcontroller.Adapter.NewsListAdapter;
import com.example.mynewsfeed.ViewModel.NewsViewModel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    NetworkActivity networkActivity =new NetworkActivity();
    private NewsViewModel mNewsViewModel;


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

        mNewsViewModel = ViewModelProviders.of(this).get(NewsViewModel.class);

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

        //handle selected item action
        startMenuACtion(item.getItemId());

        return super.onOptionsItemSelected(item);
    }

    public void button(View view) {
        Toast.makeText(this,"clearing all data! ", Toast.LENGTH_LONG).show();
        mNewsViewModel.deleteAll();
    }

    public void fetchAndRoom(){
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

    public void startMenuACtion(int id){
        Intent intent;


        switch (id){
            case R.id.ignored_source_menuItem:
                 intent = new Intent(this,ListOfIgnoredSourceActivity.class);
                 startActivity(intent);
            case R.id.action_settings:
                 //intent = new Intent(this,Settings.class);
            case R.id.refresh_menu:
                fetchAndRoom();
        }

    }

    public void readNews(News mNews){
        Intent intent = new Intent(this, DescriptionActivity.class);
        intent.putExtra("title",mNews.getTitle());
        intent.putExtra("description",mNews.getDescription());
        intent.putExtra("link",mNews.getLink());
        startActivity(intent);

    }
}
