package com.staaworks.news;

import android.content.Context;
import android.support.annotation.NonNull;

import com.staaworks.storage.FeedDBA;
import com.staaworks.util.Time;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Ahmad Alfawwaz on 8/9/2016
 */
public class Feed implements Serializable, Comparable<Feed> {

    private String title, link, description, imageURL,imageTitle, pubDate, category, viewed = "false";
    private Boolean important, recent, isViewed;
    private int rating, priority;
    private FeedDBA storage;
    private Calendar pDate;



    public Feed(String title, String link, String description, String imageURL, String imageTitle, String pubDate, String rating, String category, Context context) {


        if (!title.equals("")) {
            this.title = title;
        }
        else {
            this.title = "Sample Title";
        }



        if (!link.equals("")) {
            this.link = link;
        }
        else {
            this.link = "http://google.com";
        }



        if (!description.equals("")) {
            description = resolveDescription(description);
            this.description = description;
        }
        else {
            this.description = "Sample Text For Description Content";
        }

        if (!imageURL.equals("")) {
            this.imageURL = imageURL;
        }
        else {
            this.imageURL = "http://i.imgur.com/IXELLM8.jpg";
        }


        if (!imageTitle.equals("")) {
            this.imageTitle = imageTitle;
        }
        else {
            this.imageTitle = "Default Title";
        }



        try{
            this.rating = Integer.parseInt(rating.trim());
        }
        catch (NumberFormatException e) {
            this.rating = 3;
        }
        finally {
            important = this.rating == 5;
        }


        if (!pubDate.equals("")) {
            this.pubDate = pubDate;
        }
        else {
            this.pubDate = "9/1/2016";
        }
        pDate = Time.getCalendar(this.pubDate);


        if (!category.equals("")) {
            this.category = category;
        }
        else {
            this.category = "general";
        }

        storage = new FeedDBA(context);

    }



    public String getTitle() {
        return title;
    }

    public String getLink() {
        return link;
    }

    public String getDescription() {
        return description;
    }

    public int getRating() {
        return rating;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public String getPubDate() {
        return pubDate;
    }


    public String getImageURL() {
        return imageURL;
    }


    public String getCategory() {
        return category;
    }


    public Feed setViewed(String viewed) {
        if (!this.viewed.equals(viewed)) {
            this.viewed = viewed;
            storage.open();
            System.out.println("FEED: " + viewed);
            storage.setViewed(this, Boolean.getBoolean(viewed));
            isViewed = Boolean.getBoolean(viewed);
            storage.close();
        }
        return this;
    }


    public boolean isViewed() {
        isViewed = Boolean.getBoolean(viewed);
        return isViewed;
    }


    public int getPriority() {
        priority = 0;
        if (isNew()) priority++;
        if (isImportant()) priority++;
        if (isViewed()) priority++;
        return priority;
    }

    @Override
    public String toString() {
        return title;
    }




    private String resolveDescription(String description) {

        if (!description.contains("<") &&!description.contains(">")) {
            return description;
        }
        else {

            description = description.replaceAll("(<p>),(</p>)","\n");

            resolveLinks(description);

            description = description.replaceAll("<\\S+>", "");


            description = description.replaceAll("(<),(>)","");

            return description;
        }

    }





    private void resolveLinks(String stream) {
        ArrayList<String> links = new ArrayList<>();

        Pattern p =
                Pattern.compile("<a\\s+href\\s*=\\s*\"?(.*?)[\"|>]",
                        Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(stream);
        while (m.find()) {
            String link = m.group(1).trim();
            if (link.length() < 1) {
                continue;
            }

            if (link.charAt(0) == '#') {
                continue;
            }

            if (link.contains("mailto:")) {
                continue;
            }

            if (link.toLowerCase().contains("javascript")) {
                continue;
            }

            links.add(link);
        }
        for (String link: links) {
            link = stream.replaceAll(link, " (link:" + link + ") ");
        }

    }




    public Boolean isNew() {
        return Time.getTimeDifferenceInDays(Calendar.getInstance(),pDate) < 3
                && Time.getTimeDifferenceInDays(Calendar.getInstance(),pDate) > 0;
    }



    public Boolean isValid() {
        return
                rating <= 5
                        && rating >= 0
                        && Time.getTimeDifferenceInDays(Calendar.getInstance(), pDate) <= (4 * rating);
    }


    public Boolean isImportant() {
        return important;
    }



    @Override
    public int compareTo(@NonNull Feed feed) {
        if (feed.getLink().equals(this.getLink())) {
            return 0;
        }
        else return 1;
    }


    @Override
    public boolean equals(Object object) {
        return object.getClass() == this.getClass() && ((Feed) object).getLink().equals(this.getLink());
    }


}


