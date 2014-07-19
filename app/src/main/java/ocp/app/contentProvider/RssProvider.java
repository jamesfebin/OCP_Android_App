package ocp.app.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import ocp.app.database.RSSDatabaseHandler;
import ocp.app.models.RSSItem;

/**
 * Created by user on 19/07/14.
 */
public class RssProvider extends ContentProvider {
    public static final String AUTHORITY = "user.orthodoxycognatepage.provider";
    public static final String SCHEME = "content://";

    // URIs
    public static final String ITEMS = SCHEME + AUTHORITY + "/item";
    public static final Uri URI_Items= Uri.parse(ITEMS);
    public static final String Items_BASE = ITEMS + "/";


    RSSDatabaseHandler dbHelper;
    public RssProvider() {
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor result = null;

        if (uri.toString().startsWith(Items_BASE)) {

            dbHelper = new RSSDatabaseHandler(getContext());

            final String id = uri.getLastPathSegment();
            result = dbHelper
                    .getReadableDatabase()
                    .query(RSSItem.TableName, RSSItem.FIELDS,
                            RSSItem.COL_ID + " IS ?",
                            new String[] { id }, null, null,
                            null, null);

            result.setNotificationUri(getContext().getContentResolver(), URI_Items);


        }
        else if (URI_Items.equals(uri)) {

            long unixTimeNow = System.currentTimeMillis() / 1000L;
        dbHelper = new RSSDatabaseHandler(getContext());

            result = dbHelper
                    .getReadableDatabase()
                    .query(RSSItem.TableName, RSSItem.FIELDS, null, null, null,null,
                            RSSItem.COL_ID , null);

            Log.d("Row count", result.getCount() + "");
            result.setNotificationUri(getContext().getContentResolver(), URI_Items);

        }
        else {
            throw new UnsupportedOperationException("Not yet implemented");
        }

        return result;
    }


    @Override
    public String getType(Uri uri) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
