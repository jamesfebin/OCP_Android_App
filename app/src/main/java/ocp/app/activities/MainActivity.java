package ocp.app.activities;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;
import com.ocp.app.R;

import ocp.app.adapter.ListViewAdapter;
import ocp.app.contentProvider.RssProvider;
import ocp.app.database.RSSDatabaseHandler;
import ocp.app.models.RSSFeed;
import ocp.app.models.RSSItem;
import ocp.app.models.RSSParser;

public class MainActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor> {

    RSSParser rssParser = new RSSParser();
    RSSFeed rssFeed;
    RSSDatabaseHandler dbHelper;
    SimpleCursorAdapter itemAdapter;
    ListView listView;
    LoaderManager loadermanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new GetOcpFeeds().execute();

        populateListViewFromDb();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    public void populateListViewFromDb()
    {

        String[] fromFieldNames = new String[] {"_title"};

        int[] toViewIDs = new int[]
                {R.id.title};

        itemAdapter = new ListViewAdapter(this,R.layout.rss_item,null,fromFieldNames,toViewIDs,0);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemAdapter);

        loadermanager = getLoaderManager();
        loadermanager.initLoader(1,null,this);

    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                RssProvider.URI_Items , RSSItem.FIELDS, null, null,
                null);    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {

       itemAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
itemAdapter.swapCursor(null);
    }

    /**
     * Background Async Task to get RSS Feed Items data from URL
     * */
    class GetOcpFeeds extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */

        /**
         * getting all recent articles and showing them in listview
         * */
        @Override
        protected String doInBackground(String... args) {
            // rss link url
            List<RSSItem> rssItems = new ArrayList<RSSItem>();

            // list of rss items
            rssItems = rssParser.getRSSFeedItems("http://theorthodoxchurch.info/blog/news/feed/");
              String TAG_TITLE = "title";
            String TAG_LINK = "link";
             String TAG_DESRIPTION = "description";
            String TAG_PUB_DATE = "pubDate";
            String TAG_GUID = "guid"; // not used

            // looping through each item
            for(RSSItem item : rssItems){
                // creating new HashMap

                // adding each child node to HashMap key => value
               String description = item.getDescription();


                String ImageUrl="";
                if (description.contains("<img")){
                    String img  = description.substring(description.indexOf("<img"));
                    String cleanUp = img.substring(0, img.indexOf(">")+1);
                    img = img.substring(img.indexOf("src=") + 5);
                    int indexOf = img.indexOf("'");
                    if (indexOf==-1){
                        indexOf = img.indexOf("\"");
                    }
                    img = img.substring(0, indexOf);

                    Log.d("IMAGE ",img);
                    ImageUrl=img;
                }


                // taking only 200 chars from description
                if(description.length() > 100){
                    //description = description.substring(0, 97) + "..";
                }



            RSSItem dbItem = new RSSItem(item.getTitle(),item.getLink(),item.getDescription(),item.getPubdate(),item.getGuid(),ImageUrl);

                dbHelper = new RSSDatabaseHandler(getApplicationContext());
                dbHelper.putRssItem(dbItem);

                dbHelper.close();
            }


            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed items into listview
                     * */

/*                     ListAdapter adapter = new SimpleAdapter(
                            ListRSSItemsActivity.this,
                            rssItemList, R.layout.rss_item_list_row,
                            new String[] { TAG_LINK, TAG_TITLE, TAG_PUB_DATE, TAG_DESRIPTION },
                            new int[] { R.id.page_url, R.id.title, R.id.pub_date, R.id.link });

                    // updating listview
                    setListAdapter(adapter);
                    */
                }
            });
            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/

    }

}
