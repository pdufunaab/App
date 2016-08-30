package app.funaab;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oadex.app.R;

import java.util.List;

/**
 * Created by Malik on 8/30/2016.
 */
public class ResultAdapter extends ArrayAdapter<Result>
{
    public ResultAdapter(Context context,List objects)
    {
        super(context, -1, objects);
    }

    private static class ViewHolder
    {
        TextView courseCodeView;
        TextView scoreView;
        TextView unitView;
        TextView gradeView;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Result result = getItem(position);
        ViewHolder holder;

        if(convertView == null)
        {
            holder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.result_list,parent,false);
            holder.courseCodeView = (TextView)convertView.findViewById(R.id.result_courseCodeView);
            holder.scoreView = (TextView)convertView.findViewById(R.id.result_scoreView);
            holder.unitView = (TextView)convertView.findViewById(R.id.result_unitView);
            holder.gradeView = (TextView)convertView.findViewById(R.id.result_gradeView);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.courseCodeView.setText(result.courseCode);
        holder.scoreView.setText(result.score);
        holder.unitView.setText(result.unit);
        holder.gradeView.setText(result.grade);

        return  convertView;
    }
}
