package com.staaworks.news;

import android.content.Context;
import android.content.SharedPreferences;

import com.oadex.app.NewsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Ahmad Alfawwaz on 9/23/2016
 */
public class Categories  {



    private static SharedPreferences preferences = NewsActivity.getCategoriesSharedPreferences();
    public static final List<Categories> loadAll = new ArrayList<>();



    private String name;
    private String tabTitle;
    private String attr;


    public static final Categories all= new Categories("all", "All News");
    public static final Categories general = new Categories("general", "General (Public News)");
    public static final Categories important = new Categories("important", "Important News");
    public static final Categories sport = new Categories("sport", "Sport News");
    public static final Categories matriculation = new Categories("matriculation", "Matriculation Updates");
    public static final Categories convocation = new Categories("convocation", "Convocation Updates");
    public static final Categories entertainment = new Categories("entertainment", "Entertainment News");
    public static final Categories politics = new Categories("politics", "News On Politics");
    public static final Categories business = new Categories("business", "Business News");




    public Categories(String name, String attr) {

        this.name = name;
        this.attr = attr;
        this.tabTitle = name.toUpperCase(Locale.US);

        create();
    }


    private void create() {

        loadAll.add(this);
        persist(this);

    }



    public String getTabTitle() {
        return tabTitle;
    }


    public String name() {
        return name;
    }

    public String getAttr() {
        return attr;
    }



    public static Categories getCategory(String attr) {
        Categories value = general;
        for (Categories cat : loadAll) {
            if (cat.getAttr().equals(attr)) {
                value = cat;
                break;
            }
        }
        return value;
    }


    private static boolean exists(Categories categories) {
        return loadAll().contains(categories);
    }


    private static void persist(Categories categories) {
        preferences.edit().putString(categories.name, mkString(categories)).apply();
    }


    public static Categories getCategoryFromName(String name) {

        String catString = preferences.getString(name, general.name + "|" + general.attr);
        return revString(catString);

    }


    protected static List<Categories> loadAll() {

        Object[] k = preferences.getAll().keySet().toArray();
        List<Categories> categories = new ArrayList<>(k.length);

        for (Object o: k) {
            Categories c = getCategoryFromName((String) o);
            categories.add(c);
            loadAll.add(c);
        }

        return categories;
    }


    private static String mkString(Categories categories) {
        return categories.name + "|" + categories.attr;
    }


    private static Categories revString(String catString) {

        if (!catString.isEmpty() && catString.contains("|"))
            return new Categories(catString.split("|")[0], catString.split("|")[1]);

        else return general;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof Categories && mkString((Categories) o).equals(mkString(this));
    }
}