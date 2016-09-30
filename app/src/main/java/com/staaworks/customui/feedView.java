package com.staaworks.customui;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Ahmad Alfawwaz on 8/11/2016
 */
public class feedView extends View {


    private TextView titleBar;
    private TextView descriptionPane;
    private ImageView feedImagePane;

    private String feedTitle;
    private String feedDescription;
    private Bitmap feedImageBitmap;



    public feedView(Context context) {
        super(context);
    }


    public String getFeedTitle() {
        return feedTitle;
    }

    public String getFeedDescription() {
        return feedDescription;
    }

    public void setText(CharSequence text) {
        feedTitle = breakAtLineBreaks(text.toString())[0];
        feedDescription = breakAtLineBreaks(text.toString())[1];
        titleBar.setText(feedTitle);
        descriptionPane.setText(feedDescription);
    }




    private static String[] breakAtLineBreaks(String statements) {
        String[] result = new String[2];

        char[] chars = statements.toCharArray();
        StringBuilder b = new StringBuilder();
        int k = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '\n') {
                k = i;
                break;
            }
        }


        for (int j = 0; j < k; j++) {
            result[0] = b.append(chars[j]).toString();
            result[0] = result[0].replaceAll("\n", "");
        }


        b = new StringBuilder();
        for (int j = k; j < chars.length; j++) {
            result[1] = b.append(chars[j]).toString();
            result[1] = result[1].replaceAll("\n", "");
        }

        return result;
    }

}
