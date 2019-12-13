package com.example.mynewsfeed.Parser;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.example.mynewsfeed.R;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NetworkActivity extends Activity {
    public static final String WIFI = "Wi-Fi";
    public static final String ANY = "Any";
    private static final String URL = "https://www.yahoo.com/news/rss/world";

    // Whether there is a Wi-Fi connection.
    private static boolean wifiConnected = false;
    // Whether there is a mobile connection.
    private static boolean mobileConnected = false;
    // Whether the display should be refreshed.
    public static boolean refreshDisplay = true;
    public static String sPref = null;

    public void loadPage(){
        if(sPref.equals(ANY) &&(wifiConnected || mobileConnected)){
            new DownloadXmlTask().execute(URL);
        }else if((sPref.equals(WIFI)) && (wifiConnected)){
            new DownloadXmlTask().execute(URL);
        }else{
            //err
            //pref kısmından dolayı
            new DownloadXmlTask().execute(URL);
        }
    }

    private class DownloadXmlTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls){
            try{
                return loadXmlFromNetwork(urls[0]);
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

    private String loadXmlFromNetwork(String urlString) throws XmlPullParserException, IOException{
        InputStream stream = null;

        NewsParser newsParser = new NewsParser();
        List<NewsParser.News> newsS = null;
        String title = null;
        String description = null;
        String link = null;
        String pubDate = null;
        String source = null;

        /*
        Calendar rightNow = Calendar.getInstance();
        DateFormat formatter = new SimpleDateFormat("MMM dd h:mmaa");

        // Checks whether the user set the preference to include summary text
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean pref = sharedPrefs.getBoolean("summaryPref", false);
         */
        try{
            stream = dowlandUrl(urlString);
            newsS = newsParser.parse(stream);
        }finally {
            if(stream != null){
                stream.close();
            }
        }
        //döndürülen sonuç burayı ve doınbackground da return typeler değişmeli
        Log.d("donen xml degerleri  ", newsS.toString());
        return newsS.toString();
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
}
