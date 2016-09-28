package com.staaworks.news;

import android.content.SharedPreferences;
import android.util.Log;

import com.oadex.app.NewsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * Created by Ahmad Alfawwaz on 9/23/2016
 */
public class Category {



    private static SharedPreferences preferences = NewsActivity.getCategoriesSharedPreferences();



    private String name;
    private String tabTitle;
    private String attr;
    private static final List<Category> categories = new ArrayList<>();


    public static final Category all= new Category("all", "All News");
    public static final Category general = new Category("general", "General (Public News)");
    public static final Category important = new Category("important", "Important News");
    public static final Category sport = new Category("sport", "Sport News");
    public static final Category matriculation = new Category("matriculation", "Matriculation Updates");
    public static final Category convocation = new Category("convocation", "Convocation Updates");
    public static final Category entertainment = new Category("entertainment", "Entertainment News");
    public static final Category politics = new Category("politics", "News On Politics");
    public static final Category business = new Category("business", "Business News");





    public Category(String name, String attr) {

        this.name = name;
        this.attr = attr;
        this.tabTitle = name.toUpperCase(Locale.US);

        create();
    }


    private void create() {

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



    public static Category getCategory(String attr) {
        Category value = general;
        for (Category cat : loadAll()) {
            if (cat.getAttr().equals(attr)) {
                value = cat;
                break;
            }
        }
        return value;
    }


    private static boolean exists(Category category) {
        return categories.contains(category);
    }


    private static void persist(Category categories) {
        preferences.edit().putString(categories.name, mkString(categories)).apply();
    }


    public static Category getCategoryFromName(String name) {

        String catString = preferences.getString(name, "error");

        if (!catString.equals("error")) {
            System.out.println("CATEGORY: An Existing Category Is About To Be Used: " + name);
            return revString(catString);
        }
        else {
            if (name != null && !name.equals(""))
                return new Category(name, name.toUpperCase(Locale.US));
            else return general;
        }
    }


    public static List<Category> loadAll() {

        Object[] k = preferences.getAll().keySet().toArray();
        List<Category> categories = new ArrayList<>(k.length);

        for (Object o: k) {
            Category c = getCategoryFromName((String) o);
            Log.i("CATEGORY", "loadAll: LOADED_NAME" + ((String) o));

            if (!categories.contains(c)) categories.add(c);
            if (!Category.categories.contains(c)) Category.categories.add(c);
        }

        return categories;
    }


    private static String mkString(Category category) {
        return category.name + "|" + category.attr;
    }


    private static Category revString(String catString) {

        if (!catString.isEmpty() && catString.contains("|")) {
            String name = splitCatString(catString, '|')[0];
            String attr = splitCatString(catString, '|')[1];

            if (name.contains("|"))
                name = name.replace("|","");

            if (attr.contains("|"))
                attr = attr.replace("|", "");

            if (name.equals("")  || attr.equals("")) {
                Log.i("CATEGORY", "revString: RETURNING_GENERAL: NAME OR ATTR EMPTY");
                Log.i("CATEGORY", "revString: NAME: " + name);
                Log.i("CATEGORY", "revString: ATTR: " + attr);
                return general;
            }

            Log.i("CATEGORY", "revString: CONDITIONS PASSED: RETURNING NEW CATEGORY");
            return new Category(name, attr);
        }

        return general;
    }


    @Override
    public boolean equals(Object o) {
        return o instanceof Category && mkString((Category) o).equals(mkString(this));
    }


    public static String[] splitCatString(String catString, char delimiter) {

        if (catString.contains(delimiter + "")) {

            int delimiterIndex = catString.indexOf(delimiter);
            String part1 = "",part2 = "";

            for (int i = 0; i < delimiterIndex; i++) {
                part1 += catString.charAt(i);
            }

            for (int i = delimiterIndex + 1; i < catString.length(); i++) {
                part2 += catString.charAt(i);
            }

            return new String[] {part1, part2};
        }
        else {
            return new String[] {catString};
        }
    }
}