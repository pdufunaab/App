package app.funaab;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.oadex.app.R;

import java.util.List;

/**
 * Created by Malik on 8/16/2016.
 */
public class TimeTableAdapter extends CursorAdapter
{
    LayoutInflater layoutInflater;
    private static final int VIEW_TYPE_GROUP_START = 0;
    private static final int VIEW_TYPE_GROUP_CONT = 1;
    private static final int VIEW_TYPE_COUNT = 2;

    public TimeTableAdapter(Context context, Cursor c)
    {
        super(context, c, 0);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position)
    {
        if(position == 0)
        {
            return VIEW_TYPE_GROUP_START;
        }

        Cursor cursor = getCursor();
        cursor.moveToPosition(position);

        boolean newGroup = newGroup(cursor,position);

        if(newGroup)
        {
            return VIEW_TYPE_GROUP_START;
        }
        else
        {
            return VIEW_TYPE_GROUP_CONT;
        }
    }


    @Override
    public int getViewTypeCount()
    {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent)
    {
        int position = cursor.getPosition();
        int viewType;

        if(position == 0)
        {
            viewType = VIEW_TYPE_GROUP_START;
        }
        else
        {
            boolean newGroup = newGroup(cursor,position);
            if(newGroup)
            {
                viewType = VIEW_TYPE_GROUP_START;
            }
            else
            {
                viewType = VIEW_TYPE_GROUP_CONT;
            }
        }

        View view;

        if(viewType == VIEW_TYPE_GROUP_START)
        {
            view = layoutInflater.inflate(R.layout.table_header_list,parent,false);
        }
        else
        {
            view = layoutInflater.inflate(R.layout.table_list_item,parent,false);
        }

        return view;
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor)
    {


        TextView courseView = (TextView)view.findViewById(R.id.courseView);
        TextView venueView = (TextView)view.findViewById(R.id.venueView);
        TextView timeView = (TextView)view.findViewById(R.id.timeView);

        courseView.setText(cursor.getString(cursor.getColumnIndexOrThrow("CourseCode")));
        venueView.setText(cursor.getString(cursor.getColumnIndexOrThrow("Venue")));
        timeView.setText(cursor.getString(cursor.getColumnIndexOrThrow("Time")));

        TextView dayView;
        dayView = (TextView)view.findViewById(R.id.dayHeaderView);
        if(dayView != null)
        {
            dayView.setText(cursor.getString(cursor.getColumnIndexOrThrow("Day")));
        }

    }

    public boolean newGroup(Cursor cursor, int position)
    {
        String dayNew = cursor.getString(cursor.getColumnIndexOrThrow("Day"));
        cursor.moveToPosition(position - 1);

        String dayPrevious = cursor.getString(cursor.getColumnIndexOrThrow("Day"));
        cursor.moveToPosition(position);

        if(!dayNew.equals(dayPrevious))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    /*public TimeTableAdapter(Context context, List<TimeTable> objects) {
        super(context, -1, objects);
    }*/

    private static class ViewHolder
    {
        TextView dayView;
        TextView courseView;
        TextView venueView;
        TextView timeView;

    }




    /*@Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TimeTable timeTable = getItem(position);
        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.table_list_item,parent,false);
            holder.dayView = (TextView)convertView.findViewById(R.id.day_textView);
            holder.courseView = (TextView)convertView.findViewById(R.id.course_textView);
            holder.venueView = (TextView)convertView.findViewById(R.id.venue_textView);
            holder.timeView = (TextView)convertView.findViewById(R.id.time_textView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        Context context = getContext();
        holder.courseView.setText(timeTable.course);
        holder.timeView.setText(timeTable.date);
        holder.venueView.setText(timeTable.venue);

        return  convertView;
    }*/
}
