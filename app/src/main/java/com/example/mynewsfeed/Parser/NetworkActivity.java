package com.example.mynewsfeed.Parser;

import android.app.Activity;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkActivity extends Activity {

    private static  String mURL = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";

    protected   List<NewsParser.News> fetchedNews;
    protected List<LastBuildDateParser.BuildFeatures> fetchedBuilds;

    private String networkActivityChoice = "build"; //default

    public AsyncResponse delegate = null;

    public void loadPage(){
        new DownloadXmlTask().execute(mURL);
    }


    private class DownloadXmlTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls){
            try{
                if(networkActivityChoice.equals("build")){
                    return loadXBuildFeaturesFromNetwork(urls[0]).toString();
                }else {
                    return loadXmlFromNetwork(urls[0]).toString();
                }
            }catch (IOException e){
                return "";
            }catch (XmlPullParserException e){
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result){
            //
            if(result != null)
            delegate.processFinish(result);
        }
    }

    private List<NewsParser.News> loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException{
        InputStream stream = null;
        NewsParser newsParser = new NewsParser();
        List<NewsParser.News> newsS ;
        try{
            stream = dowlandUrl(urlString);
            newsS = newsParser.parse(stream);
            fetchedNews = newsS;
        } finally {
            if(stream != null){
                stream.close();
            }
        }
        return newsS;
    }

    private List<LastBuildDateParser.BuildFeatures> loadXBuildFeaturesFromNetwork(String urlString) throws XmlPullParserException, IOException{
        InputStream stream = null;
        LastBuildDateParser lastBuildDateParser = new LastBuildDateParser();
        List<LastBuildDateParser.BuildFeatures> buildFeatures;
        try{
            stream = dowlandUrl(urlString);
            buildFeatures = lastBuildDateParser.parse(stream);
            fetchedBuilds =buildFeatures;
        } finally {
            if(stream != null){
                stream.close();
            }
        }
        return buildFeatures;
    }

    private InputStream dowlandUrl(String urlString) throws IOException{
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000 /* milliseconds */);
        conn.setConnectTimeout(15000 /* milliseconds */);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);

        conn.connect();

        return conn.getInputStream();
    }

    public  List<NewsParser.News> getFetchedNews(){
         return fetchedNews;
    }
    public List<LastBuildDateParser.BuildFeatures> getFetchedBuilds() { return fetchedBuilds; }

    public void chooseNetworkActivity(String choice){
        networkActivityChoice = choice;
        //for fetching build feautres choice is build

        switch (choice){
            case "World":
                mURL = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";
                break;
            case "Science":
                mURL = "https://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
                break;
            case "Sports":
                mURL = "https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml";
                break;
        }
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

}
