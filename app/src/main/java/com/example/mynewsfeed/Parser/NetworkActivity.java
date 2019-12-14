package com.example.mynewsfeed.Parser;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mynewsfeed.ViewModel.NewsViewModel;

import org.xmlpull.v1.XmlPullParserException;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkActivity extends Activity {
    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String URL = "https://rss.nytimes.com/services/xml/rss/nyt/World.xml";

    protected   List<NewsParser.News> fetchedNews;
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
     //       new DownloadXmlTask().execute(URL);
     //   }else if((sPref.equals(WIFI)) && (wifiConnected)){
     //       new DownloadXmlTask().execute(URL);
     //   }else{
     //       //err
     //       //pref kısmından dolayı
     //       new DownloadXmlTask().execute(URL);
     //   }
        new DownloadXmlTask().execute(URL);
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls){
            try{
                //burayı editle
                return loadXmlFromNetwork(urls[0]).toString();
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
            //UI da gösterme yada benim için rooma kaydetme
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
}
