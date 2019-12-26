package com.example.mynewsfeed.Parser;

import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LastBuildDateParser{
    private static final String ns = null;

    public List parse(InputStream in) throws XmlPullParserException, IOException {

        //initilize parser and features
        try{
            XmlPullParser parser = Xml.newPullParser();
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES,false);
            parser.setInput(in, null);
            parser.nextTag();
            return readFeed(parser);
        }
        finally {
            in.close();
        }
    }

    private List readFeed(XmlPullParser parser) throws XmlPullParserException,IOException{
        List buildFeatures = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        parser.next();
        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if (name.equals("channel")) {
                buildFeatures.add(readFeatures(parser));
            }else {
                skip(parser);
            }
        }

        return buildFeatures;
    }

    //model
    public static class BuildFeatures{
        public final String lastBuildDate;
        public final String title;

        public BuildFeatures(String lastBuildDate,String title){
            this.lastBuildDate = lastBuildDate;
            this.title = title;
        }
    }

    private BuildFeatures readFeatures(XmlPullParser parser)throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "channel");
        String buildDate = null;
        String title = null;

        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("lastBuildDate")){
                buildDate = readLastBuildDate(parser);
            }else if(name.equals("title")){
                title = readTitle(parser);
            }else{
                skip(parser);
            }
        }
        return  new LastBuildDateParser.BuildFeatures(buildDate,title);
    }

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns,"title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns,"title");
        return title;
    }


    private String readLastBuildDate (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "lastBuildDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "lastBuildDate");
        return pubDate;
    }


    private String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

    private void skip(XmlPullParser parser)throws XmlPullParserException, IOException{
        if(parser.getEventType() != XmlPullParser.START_TAG){
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0){
            switch (parser.next()){
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }

    }
}
