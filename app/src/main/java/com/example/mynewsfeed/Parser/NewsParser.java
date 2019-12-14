package com.example.mynewsfeed.Parser;

import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class NewsParser {
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
        List news = new ArrayList();

        parser.require(XmlPullParser.START_TAG, ns, "rss");
        parser.next();
        parser.next();
        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if (name.equals("item")) {
                news.add(readNews(parser));
            } else {
                skip(parser);
            }

        }

        return news;
    }

    //model
    public static class News{
            public final String title;
            public final String description;
            public final String link;
            public final String pubDate;
            public final String creator;
            public final String category ;
            public final Boolean  isValidCategory ;



     public News(String title, String description, String link, String pubDate, String creator,String category){
         this.title = title;
         this.description = description;
         this.link = link;
         this.pubDate = pubDate;
         this.creator = creator;
         this.category = category;
         this.isValidCategory = true;
     }
}

    private  News readNews(XmlPullParser parser)throws XmlPullParserException, IOException {
        parser.require(XmlPullParser.START_TAG, ns, "item");
        String title = null;
        String description = null;
        String link = null;
        String pubDate = null;
        String creator = null;
        ArrayList<String> categoryList  = new ArrayList<>();
        String defaultCategory = "default" ;

        while(parser.next() != XmlPullParser.END_TAG){
            if(parser.getEventType() != XmlPullParser.START_TAG){
                continue;
            }
            String name = parser.getName();
            if(name.equals("title")){
                title = readTitle(parser);
            }else if(name.equals("description")){
                description = readDescription(parser);
            }else if(name.equals("link")){
                link = readLink(parser);
            }else if(name.equals("pubDate")){
                pubDate = readPubDate(parser);
            }else if(name.equals("category")){
                categoryList.add(readcategory(parser));
            }else if(name.equals("dc:creator")){
                creator = readCreator(parser);
            }
            else{
                skip(parser);
            }
        }
        //if  have any category, update default category
        if(categoryList.size() != 0){
            defaultCategory = categoryList.get(0);
        }
        return  new News(title, description,link,pubDate,creator,defaultCategory);
}

    private String readTitle(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns,"title");
        String title = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns,"title");
        return title;
    }

    private String readDescription(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns,"description");
        String description = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns,"description");
        return description;
    }
    private String readCreator(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns,"dc:creator");
        String creator = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns,"dc:creator");
        return creator;
    }

    private String readLink(XmlPullParser parser) throws IOException, XmlPullParserException{
        String link = "";
        parser.require(XmlPullParser.START_TAG,ns,"link");
        link = readText(parser);
        parser.require(XmlPullParser.END_TAG,ns,"link");
        return link;
    }

    private String readPubDate (XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "pubDate");
        String pubDate = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "pubDate");
        return pubDate;
    }

    private String readcategory(XmlPullParser parser) throws IOException, XmlPullParserException {
        parser.require(XmlPullParser.START_TAG, ns, "category");
        String category  = readText(parser);
        parser.require(XmlPullParser.END_TAG, ns, "category");
        return category ;
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
