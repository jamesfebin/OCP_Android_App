package ocp.app.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ocp.app.contentProvider.RssProvider;
import ocp.app.models.RSSItem;

public class RSSDatabaseHandler extends SQLiteOpenHelper {

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "rssReader";

	// Contacts table name
	private static final String TABLE_RSS = "websites";

	// Contacts Table Columns names
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_LINK = "link";
	private static final String KEY_RSS_LINK = "rss_link";
	private static final String KEY_DESCRIPTION = "description";
Context mContext;
	public RSSDatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {

		db.execSQL(RSSItem.CreateTable);
	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL(RSSItem.TableName);
		// Create tables again
		onCreate(db);
	}

	/**
	 * Adding a new website in websites table Function will check if a site
	 * already existed in database. If existed will update the old one else
	 * creates a new row
	 * */
    public synchronized boolean putRssItem(RSSItem item) {
        boolean success = false;
        int result = 0;
        final SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(RSSItem.TableName, new String[] { "_title" },"_title" + "=?",
                new String[] { item._title }, null, null, null, null);


        if (cursor.getCount() > 0) {
            // Then update


            result += db.update(RSSItem.TableName, item.getContent(),
                    RSSItem.COL__title + " IS ?",
                    new String[] { item._title });
        }


        if (result > 0) {
            success = true;
        } else {
            // Update failed or wasn't possible, insert instead
            final long id = db.insert(RSSItem.TableName, null,
                    item.getContent());

            success = true;
        }

        if(success)
        {
            notifyProviderOnItemChange();

        }
        return success;
    }

    public void notifyProviderOnItemChange()
    {
        mContext.getContentResolver().notifyChange(
                RssProvider.URI_Items, null, false);

    }


}