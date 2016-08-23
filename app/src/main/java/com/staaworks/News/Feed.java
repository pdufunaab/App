package com.staaworks.News;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UnknownFormatConversionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by Ahmad Alfawwaz on 8/9/2016.
 */
public class Feed {

    private String title, link, description, imageURL,imageTitle, pubDate;
    private Boolean valid = true;
    private int rating;



    public Feed(String title, String link, String description, String imageURL, String imageTitle, String pubDate, String rating) {


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
        } catch (NumberFormatException e) {
            this.rating = 3;
        }



        if (!pubDate.equals("")) {
            this.pubDate = pubDate;
        }
        else {
            this.pubDate = "23/8/2016";
        }

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

    @Override
    public String toString() {
        return title;
    }




    private String resolveDescription(String description) {

        if (!description.contains("<") &&!description.contains(">")) {
            return description;
        }
        else {
            for (int i = 0; i < description.length(); i++) {
                description = description.replaceAll("(<p>),(</p>)","\n");

                resolveLinks(description);


                description = description.replaceAll("<\\S+>", "");
                description = description.replaceAll("(<),(>)","");
            }
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
        Calendar pDate = calculatePubDate(pubDate);
        double diff = getTimeDifferenceInDays(Calendar.getInstance(),pDate);
        System.out.println(diff + " is the difference in days between now and creation");

        if (diff < 3 && diff > 0) {
            Log.i("New Indicator:", "Feed is New: Age" + diff);
            return true;
        }
        else return false;
    }



    public Boolean isValid() {
        Calendar pDate = calculatePubDate(pubDate);
        double diff = getTimeDifferenceInDays(Calendar.getInstance(),pDate);

        if (rating <= 5)
            valid = diff <= (4 * rating);
        else valid = false;

        return valid;
    }



    private static double getTimeDifferenceInDays(Calendar calendar, Calendar anotherCalendar) {
        final double DAY_VALUE = 1000 * 3600 * 24;

        return (calendar.getTimeInMillis()/DAY_VALUE) - (anotherCalendar.getTimeInMillis()/DAY_VALUE);
    }







    private Calendar calculatePubDate(String pubDate) {

        int[] res = new int[3];

        int day = 23,
            month = 7,
            year = 2016;

        Boolean monthGot = false;

        String[] months = new String[]
                {
                        "Jan", "Feb", "Mar",
                        "Apr", "May", "Jun",
                        "Jul", "Aug", "Sep",
                        "Oct", "Nov", "Dec"

                };


        for (int i = 0; i < 12; i++) {

            if (pubDate.contains(months[i])) {
                res[1] = i;
                monthGot = true;
            }

        }


        if (pubDate.matches("(\\d+)-(\\d+)-(\\d+)")) {

            res[0] = split(pubDate, '-')[0];
            res[2] = split(pubDate, '-')[2];

            if (!monthGot) {
                res[1] = split(pubDate, '-')[1] - 1;
            }


            if (res[0] > 2000) {
                year = res[0];
                month = res[1];
                day = res[2];
            }


            if (res[2] > 2000) {
                year = res[2];
                month = res[1];
                day = res[0];
            }

            Calendar pDate = Calendar.getInstance();
            pDate.set(year,month,day);
            double diff = getTimeDifferenceInDays(Calendar.getInstance(),pDate);

            System.out.println(diff + " is the difference in days between now and creation");

            return pDate;

        }


        else if (pubDate.matches("(\\d+)/(\\d+)/(\\d+)")) {
            res[0] = split(pubDate, '/')[0];
            res[2] = split(pubDate, '/')[2];

            if (!monthGot) {
                res[1] = split(pubDate, '/')[1] - 1;
            }

            if (res[0] > 2000) {
                year = res[0];
                month = res[1];
                day = res[2];
            }

            if (res[2] > 2000) {
                year = res[2];
                month = res[1];
                day = res[0];
            }

            Calendar pDate = Calendar.getInstance();
            pDate.set(year,month,day);
            double diff = getTimeDifferenceInDays(Calendar.getInstance(),pDate);

            System.out.println(diff + " is the difference in days between now and creation");

            return pDate;

        }



        // if date is not in any of the above given formats, parse it with the special datetime formatter
        else {

            return parseDate(pubDate);

        }

    }



    private int[] split(String statement, char delimiter) {
        if (!statement.contains(delimiter + ""))
            throw new UnknownFormatConversionException("The String provided contains no instance of delimiter");

        else {
                String[] resultInProgress = new String[3];
                int[] result = new int[3];

                char[] chars = statement.toCharArray();
                StringBuilder b = new StringBuilder();
                ArrayList<Integer> k = new ArrayList<>();
                for (int i = 0; i < chars.length; i++) {
                    if (chars[i] == delimiter)
                        k.add(i);
                }


                for (int j = 0; j < k.get(0); j++) {
                    resultInProgress[0] = b.append(chars[j]).toString();
                    //resultInProgress[0] = resultInProgress[0].replaceAll(delimiter + "", "");
                    result[0] = Integer.parseInt(resultInProgress[0]);
                    System.out.println(resultInProgress[0]);
                }


                b = new StringBuilder();
                for (int j = k.get(0) + 1; j < k.get(1); j++) {
                    resultInProgress[1] = b.append(chars[j]).toString();
                    //resultInProgress[1] = resultInProgress[1].replaceAll(delimiter + "", "");
                    result[1] = Integer.parseInt(resultInProgress[1]);
                    System.out.println(resultInProgress[1]);
                }

                b = new StringBuilder();
                for (int j = k.get(1) + 1; j < statement.length(); j++) {
                    resultInProgress[2] = b.append(chars[j]).toString();
                    //resultInProgress[2] = resultInProgress[2].replaceAll(delimiter + "", "");
                    result[2] = Integer.parseInt(resultInProgress[2]);
                    System.out.println(resultInProgress[2]);
                }

                return result;
        }

    }

    public Calendar parseDate (String pubDate){
        //String returnDate;

        try {
            String format = "EEE, dd MMM yyyy kk:mm:ss Z";
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
            Date formattedDate = sdf.parse(pubDate);

            Calendar c= Calendar.getInstance();
            c.setTime(formattedDate);
            return c;


            //returnDate=""+c.get(Calendar.DAY_OF_MONTH)+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR)+" "+c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE);
            //return returnDate;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;

        }
    }

}


