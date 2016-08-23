package com.staaworks.News;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * Class implements a list listener.
 * @author Ahmad Alfawwaz
 */

public class ListListener extends Activity implements OnItemClickListener {

    Feeds feeds;
    Activity activity;


    public ListListener(Feeds feeds, Activity anActivity) {
        this.feeds = feeds;
        activity  = anActivity;
    }



    public void onItemClick(AdapterView parent, View view, int pos, long id) {
        Intent in = new Intent(activity, InAppBrowserPage.class);
        in.putExtra("URL", feeds.get(pos).getLink());
        activity.startActivity(in);
    }
}