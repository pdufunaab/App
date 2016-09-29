package com.staaworks.search;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oadex.app.R;

/**
 * Created by Ahmad Alfawwaz on 9/19/2016
 */
public class PersonAdapter extends ArrayAdapter<Person> {

    private Person person;
    private String JsonString;




    public PersonAdapter(Context context, People people, String JsonString) {
        super(context, R.layout.search_list_item, R.id.nameTextView, people);
        this.JsonString = JsonString;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View current = super.getView(position, convertView, parent);

        person = getItem(position);
        String name = person.getName();
        String department = person.getDepartment();
        String phone = person.getPhoneNumber();
        String email = person.getEmail();

        System.out.println("\n" + person + "\n");



        if (name != null && !name.equals("")) {
            TextView nameTextView = (TextView) current.getTag(R.id.nameTextView);
            if (nameTextView == null) {
                nameTextView = (TextView) current.findViewById(R.id.nameTextView);
                current.setTag(R.id.nameTextView, nameTextView);
            }
            nameTextView.setText(name);
        }
        else {
            remove(person);
            notifyDataSetChanged();
            return current;
        }



        if (department != null && !department.equals("")) {
            TextView dptTextView = (TextView) current.getTag(R.id.dptTextView);
            if (dptTextView == null) {
                dptTextView = (TextView) current.findViewById(R.id.dptTextView);
                current.setTag(R.id.dptTextView, dptTextView);
            }
            dptTextView.setText(department);
        }
        else {
            remove(person);
            notifyDataSetChanged();
            return current;
        }



        TextView phoneTextView = (TextView) current.getTag(R.id.phoneTextView);
        if (phoneTextView == null) {
            phoneTextView = (TextView) current.findViewById(R.id.phoneTextView);
            current.setTag(R.id.phoneTextView, phoneTextView);
        }

        if (phone != null && !phone.equals("")) {
            phoneTextView.setText(phone);
        }
        else {
            phoneTextView.setVisibility(View.GONE);
        }



        TextView emailTextView = (TextView) current.getTag(R.id.emailTextView);
        if (emailTextView == null) {
            emailTextView = (TextView) current.findViewById(R.id.emailTextView);
            current.setTag(R.id.emailTextView, emailTextView);
        }

        if (email != null && !email.equals("")) {
            emailTextView.setText(email);
        }
        else {
            emailTextView.setVisibility(View.GONE);
        }

        return current;
    }


    public String getJsonString() {
        if (JsonString == null) return "Unknown Format";

        String json = JsonString;
        JsonString = "Unknown Format";
        return json;
    }
}
