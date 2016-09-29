package com.staaworks.events;

import android.content.SharedPreferences;

import com.staaworks.util.ContextTools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ahmad Alfawwaz on 9/29/2016
 */
public class AttendeeCategory {


    private SharedPreferences preferences = ContextTools.eventAttendeeSharedPreferences;

    public AttendeeCategory all = new AttendeeCategory("all");

    List<AttendeeCategory> loadAll = new ArrayList<>();


    public AttendeeCategory(String value) {



    }


    public List<AttendeeCategory> loadAll() {

        for (Object object: preferences.getAll().keySet()) {

            if (object instanceof String) {

                String raw = (String) object;
                AttendeeCategory category = revString(raw);

                if (!loadAll.contains(category)) {
                    loadAll.add(category);
                }

            }
        }
        return loadAll;
    }

    private AttendeeCategory revString(String raw) {

        if (raw != null && !raw.isEmpty()) {
            return new AttendeeCategory(raw);
        }
        else {
            return new AttendeeCategory("all");
        }

    }

}
