package com.staaworks.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

import com.staaworks.news.Category;
import com.staaworks.news.Feed;
import com.staaworks.news.Feeds;


public class FeedDBA {

    private static final String DB_NAME = "feeds.db";
    private static final String TABLE = "FeedTable";
    private static final Integer VERSION = 1;
    private SQLiteDatabase db;
    private DBHelper helper;
    private Queries queries = new Queries();
    private Integer position = 0;



    // The name of each column in the feed database.


    public static final String COL_LINKS = "feed_links";
    public static final int LINKS_INDEX = 0;

    public static final String FEED_TITLES = "feed_titles";
    public static final int TITLE_INDEX = 1;

    public static final String COL_DESC = "feed_descriptions";
    public static final int DESC_INDEX = 2;

    public static final String COL_DATE = "feed_pubDates";
    public static final int DATE_INDEX = 3;

    public static final String COL_RATING = "feed_ratings";
    public static final int RATING_INDEX = 4;

    public static final String COL_IMG = "feed_imageURLs";
    public static final int IMG_INDEX = 5;

    public static final String COL_IMG_TITLE = "feed_imageTitles";
    public static final int IMG_TITLE_INDEX = 6;

    public static final String VIEWED = "viewed";
    public static final int VIEWED_INDEX = 7;


    public static final String CATEGORY = "cat";
    public static final int CATEGORY_INDEX = 8;

    public static final String PRIORITY = "priority";


    private static final String DATABASE_CREATE = "create table " +
            TABLE + " (" +
            COL_LINKS + " text primary key not null, " +
            FEED_TITLES + " text not null, " +
            COL_DESC + " text not null, " +
            COL_DATE + " text not null, " +
            COL_RATING + " integer not null, " +
            COL_IMG + " text not null, " +
            COL_IMG_TITLE + " text not null, " +
            VIEWED + " text default false, " +
            CATEGORY + " text default general, " +
            PRIORITY + " integer default 0" +
            ");";





    public FeedDBA(Context context) {
        helper = new DBHelper(context, DB_NAME, null, VERSION);
    }


    public  FeedDBA open() {
        db = helper.getWritableDatabase();
        return this;
    }



    public void close() {
        //queries.resolveCategories();
        db.close();
    }





    public Feeds getAll() {
        return queries.getAllFeeds();
    }




    public Feeds getNew() {
        return queries.getNewFeeds();
    }





    public Feeds getImportant() {
        return queries.getImportantFeeds();
    }





    public Feeds getNextSet(Category category) {
        Feeds feeds = queries.getFeeds(position, position + 9, category);
        position += 10;
        return feeds;
    }





    public Feeds getByTitle() {
        return queries.sortFeedsBy(FEED_TITLES);
    }





    public Boolean isFeedViewed(Feed feed) {
        return queries.isViewed(feed);
    }


    public void setViewed(Feed feed) {
        queries.setViewed(feed);
    }



    public void addFeed(Feed feed) {
        queries.addFeed(feed);
    }




    public void removeFeed(Feed feed) {
        queries.removeFeed(feed);
    }




    public void updateFeed(Feed oldFeed, Feed newFeed) {
        queries.updateFeed(oldFeed, newFeed);
    }





    public class Queries {


        private static final String retrieveAll = "SELECT * FROM " + TABLE + " ORDER BY " + PRIORITY + " DESC;";



        protected void resolveCategories() {
            for (Category category: Category.loadAll()) {
                for (Feed feed : getByCategory(category)) {
                    if (!feed.isValid()) {
                        removeFeed(feed);
                    }
                }
            }
        }


        protected Feeds getFeeds(Integer start, Integer stop, Category category) {
            if (getByCategory(category).size() != 0) {

                Feeds   feeds = new Feeds(),
                        f = getByCategory(category);

                int size = f.size();
                
                if (size < 10) return  f;
                else if (size > stop) {

                    for (int i = start; i <= stop; i++) {
                        feeds.add(f.get(i));
                    }
                }
                else {
                    for (int i = start; i < size; i++) {
                        feeds.add(f.get(i));
                    }
                }
                return feeds;
            }
            else return new Feeds();
        }




        protected Boolean isViewed(Feed feed) {
            try {
                String statement = "SELECT * FROM " + TABLE + " WHERE " + COL_LINKS + " = '" + feed.getLink() + "';";
                Cursor cursor = db.rawQuery(statement, null);

                cursor.moveToNext();
                String viewed = cursor.getString(VIEWED_INDEX);
                cursor.close();

                return Boolean.getBoolean(viewed);
            }
            catch (Exception e) {
                return false;
            }
        }




        protected void setViewed(Feed feed) {
            updateFeed(feed, feed);
        }






        protected Feeds getByCategory(Category category) {
            if (category.equals(Category.all)) {
                return getAllFeeds();
            }
            else {
                String statement = "SELECT * FROM " + TABLE + " WHERE " + CATEGORY + " = '" + category.name() + "' ORDER BY " + PRIORITY + " DESC;";
                return getFeeds(statement);
            }
        }




        protected Feeds getNewFeeds() {
            Feeds feeds = new Feeds();

            for (Feed feed: getAllFeeds()) {
                if (feed.isNew()) feeds.add(feed);
            }

            return feeds;
        }

        protected Feeds getImportantFeeds() {
            Feeds feeds = new Feeds();

            for (Feed feed: getAllFeeds()) {
                if (feed.getRating() == 5) feeds.add(feed);
            }

            return feeds;
        }

        protected Feeds sortFeedsBy(String columnName) {
            String retrieve = "SELECT * FROM " + TABLE + " ORDER BY " + columnName + " DESC;";
            return getFeeds(retrieve);
        }

        private Feeds getFeeds(String statement) {
            Cursor cursor = db.rawQuery(statement, null);
            Feeds feeds = new Feeds();
            String feedTitle, feedLink, feedDescription, feedImageUrl, feedImageTitle, feedPubDate, feedCategory, isViewed;
            int feedRating;

            while (cursor.moveToNext()) {
                feedTitle = cursor.getString(TITLE_INDEX);
                feedLink = cursor.getString(LINKS_INDEX);
                feedDescription = cursor.getString(DESC_INDEX);
                feedImageUrl = cursor.getString(IMG_INDEX);
                feedImageTitle = cursor.getString(IMG_TITLE_INDEX);
                feedPubDate = cursor.getString(DATE_INDEX);
                feedRating = cursor.getInt(RATING_INDEX);
                feedCategory = cursor.getString(CATEGORY_INDEX);
                isViewed = cursor.getString(VIEWED_INDEX);

                boolean viewed = Boolean.getBoolean(isViewed);
                Feed feed = new Feed(feedTitle,feedLink,feedDescription,feedImageUrl,feedImageTitle,feedPubDate,feedRating + "", feedCategory);

                if (viewed)
                    feeds.add(feed.setViewed());
                else feeds.add(feed);
            }
            cursor.close();

            return feeds;
        }




        protected Feed getPersistedFeed(Feed feed) {
            String statement = "SELECT * FROM " + TABLE + " WHERE " + COL_LINKS + " = '" + feed.getLink() + "';";
            return getFeeds(statement).get(0);
        }


        protected Feeds getAllFeeds() {
            return getFeeds(retrieveAll);
        }


        protected void addFeed(Feed feed) {

            System.out.println("AddFeedCalled: " + feed);
                ContentValues contentValues = new ContentValues();

            contentValues.put(FEED_TITLES, feed.getTitle());
            contentValues.put(COL_LINKS, feed.getLink());
            contentValues.put(COL_DESC, feed.getDescription());
            contentValues.put(COL_DATE, feed.getPubDate());
            contentValues.put(COL_IMG, feed.getImageURL());
            contentValues.put(COL_IMG_TITLE, feed.getImageTitle());
            contentValues.put(COL_RATING, feed.getRating());
            contentValues.put(CATEGORY, feed.getCategory());
            contentValues.put(PRIORITY, feed.getPriority());

                db.insert(TABLE, null, contentValues);

        }
        
        
        protected void updateFeed(Feed oldFeed, Feed newFeed) {
            String condition = COL_LINKS + "='" + oldFeed.getLink() + "'";

            ContentValues contentValues = new ContentValues();

            contentValues.put(FEED_TITLES, newFeed.getTitle());
            contentValues.put(COL_LINKS, newFeed.getLink());
            contentValues.put(COL_DESC, newFeed.getDescription());
            contentValues.put(COL_DATE, newFeed.getPubDate());
            contentValues.put(COL_IMG, newFeed.getImageURL());
            contentValues.put(COL_IMG_TITLE, newFeed.getImageTitle());
            contentValues.put(COL_RATING, newFeed.getRating());
            contentValues.put(CATEGORY, newFeed.getCategory());
            contentValues.put(PRIORITY, newFeed.getPriority());
            contentValues.put(VIEWED, newFeed.isViewed());
            
            db.update(TABLE, contentValues, condition, null);
        }


        protected void removeFeed(Feed feed) {
            String condition = COL_LINKS + "='" + feed.getLink() + "'";
            db.delete(TABLE, condition, null);
        }


    }

    private static class DBHelper extends SQLiteOpenHelper {



        /**
         * Create a helper object to create, open, and/or manage a database.
         * This method always returns very quickly.  The database is not actually
         * created or opened until one of {@link #getWritableDatabase} or
         * {@link #getReadableDatabase} is called.
         *
         * @param context to use to open or create the database
         * @param name    of the database file, or null for an in-memory database
         * @param factory to use for creating cursor objects, or null for the default
         * @param version number of the database (starting at 1); if the database is older,
         *                {@link #onUpgrade} will be used to upgrade the database; if the database is
         *                newer, {@link #onDowngrade} will be used to downgrade the database
         */
        public DBHelper(Context context, String name, CursorFactory factory, int version) {
            super(context, name, factory, version);
        }



        /**
         * Called when the database is created for the first time. This is where the
         * creation of tables and the initial population of the tables should happen.
         *
         * @param db The database.
         */
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }


        /**
         * Called when the database needs to be upgraded. The implementation
         * should use this method to drop tables, add tables, or do anything else it
         * needs to upgrade to the new schema version.
         * <p>
         * <p>
         * The SQLite ALTER TABLE documentation can be found
         * <a href="http://sqlite.org/lang_altertable.html">here</a>. If you add new columns
         * you can use ALTER TABLE to insert them into a live TABLE. If you rename or remove columns
         * you can use ALTER TABLE to rename the old TABLE, then create the new TABLE and then
         * populate the new TABLE with the contents of the old TABLE.
         * </p><p>
         * This method executes within a transaction.  If an exception is thrown, all changes
         * will automatically be rolled back.
         * </p>
         *
         * @param db         The database.
         * @param oldVersion The old database version.
         * @param newVersion The new database version.
         */
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            Log.w("FeedDBAdapter_Task", "Upgrading from version " + oldVersion + " to version " + newVersion + "\nDestroying All data");
            db.execSQL("DROP TABLE IF EXISTS " + TABLE);
            onCreate(db);
        }
    }

}
