package com.staaworks.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UnknownFormatConversionException;

/**
 * Created by Ahmad Alfawwaz on 8/30/2016
 */
public class Time {

    public static Calendar getCalendar(String rawDateObject) {
        int[] res = new int[3];

        int day = 1,
                month = 9,
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

            if (rawDateObject.contains(months[i])) {
                res[1] = i;
                monthGot = true;
            }

        }


        if (rawDateObject.matches("(\\d+)-(\\d+)-(\\d+)")) {

            res[0] = split(rawDateObject, '-')[0];
            res[2] = split(rawDateObject, '-')[2];

            if (!monthGot) {
                res[1] = split(rawDateObject, '-')[1] - 1;
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
            return pDate;

        }


        else if (rawDateObject.matches("(\\d+)/(\\d+)/(\\d+)")) {
            res[0] = split(rawDateObject, '/')[0];
            res[2] = split(rawDateObject, '/')[2];

            if (!monthGot) {
                res[1] = split(rawDateObject, '/')[1] - 1;
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
            return pDate;

        }



        // if date is not in any of the above given formats, parse it with the special datetime formatter
        else {

            return parseDate(rawDateObject);

        }

    }

    public static int[] split(String statement, char delimiter) {
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
                result[0] = Integer.parseInt(resultInProgress[0]);
                System.out.println(resultInProgress[0]);
            }


            b = new StringBuilder();
            for (int j = k.get(0) + 1; j < k.get(1); j++) {
                resultInProgress[1] = b.append(chars[j]).toString();
                result[1] = Integer.parseInt(resultInProgress[1]);
                System.out.println(resultInProgress[1]);
            }

            b = new StringBuilder();
            for (int j = k.get(1) + 1; j < statement.length(); j++) {
                resultInProgress[2] = b.append(chars[j]).toString();
                result[2] = Integer.parseInt(resultInProgress[2]);
                System.out.println(resultInProgress[2]);
            }

            return result;
        }

    }

    public static Calendar parseDate (String pubDate){
        //String returnDate;
        Calendar c= Calendar.getInstance();
        if (pubDate.contains(",") && pubDate.contains(":")) {
            try {

                String format = "EEE, dd MMM yyyy kk:mm:ss Z";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
                Date formattedDate = sdf.parse(pubDate);

                c.setTime(formattedDate);
                return c;

            } catch (ParseException e) {
                return null;
            }

        }
        else {
            c.setTimeInMillis(Long.parseLong(pubDate));
            return c;
        }
    }

    public static double getTimeDifferenceInDays(Calendar calendar, Calendar anotherCalendar) {
        final double DAY_VALUE = 1000 * 3600 * 24;

        return (calendar.getTimeInMillis()/DAY_VALUE) - (anotherCalendar.getTimeInMillis()/DAY_VALUE);
    }

}
