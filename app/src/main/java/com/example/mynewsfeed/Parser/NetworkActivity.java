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
    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static  String mURL = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";

    protected   List<NewsParser.News> fetchedNews;
    protected List<LastBuildDateParser.BuildFeatures> fetchedBuilds;

    private String networkActivityChoice = "build"; //default

    public AsyncResponse delegate = null;
/*
    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;
    public static String sPref = null;
*/
    public void loadPage(){
     //   if(sPref.equals(ANY) &&(wifiConnected || mobileConnected)){
     //       new DownloadXmlTask().execute(mURL);
     //   }else if((sPref.equals(WIFI)) && (wifiConnected)){
     //       new DownloadXmlTask().execute(mURL);
     //   }else{
     //       //err
     //       //pref kısmından dolayı
     //       new DownloadXmlTask().execute(mURL);
     //   }
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
               // return getResources().getString(R.string.connection_error);
                return "";
            }catch (XmlPullParserException e){
                //return getResources().getString(R.string.xml_error);
                return "";
            }
        }

        @Override
        protected void onPostExecute(String result){
            //
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

  //    public void getResults opPostExecute u çağıran bir fonksiyon yaz onun üstünden eriş mainde

    public void chooseNetworkActivity(String choice){
        networkActivityChoice = choice;
        //for fetching build feautres choice is build

        switch (choice){
            case "world":
                mURL = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";
                break;
            case "science":
                mURL = "https://rss.nytimes.com/services/xml/rss/nyt/Science.xml";
                break;
            case "sports":
                mURL = "https://rss.nytimes.com/services/xml/rss/nyt/Sports.xml";
                break;
        }
    }

    public interface AsyncResponse {
        void processFinish(String output);
    }

}
