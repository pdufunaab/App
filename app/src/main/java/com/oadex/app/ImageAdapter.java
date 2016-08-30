package com.oadex.app;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

/**
 * Created by Oseni Adekunle on 01/08/2016.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView ;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100,100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(20,20,20,20);
            imageView.setBackgroundResource(R.drawable.grid_item);



        }
        else
        {
            imageView = (ImageView) convertView;
        }
        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    private Integer[] mThumbIds = {
            R.drawable.user,R.drawable.location_pin,
            R.drawable.newspaper,R.drawable.reading,
            R.drawable.users_group,R.drawable.calendar,
            R.drawable.book,R.drawable.chat,
            R.drawable.home_icon
    };

    private Integer[] mThumbText = {
            R.string.profile,R.string.map,
            R.string.news,R.string.learning,
            R.string.groups,R.string.timetable,
            R.string.contacts,R.string.feedbacks,
            R.string.about
    };
}
