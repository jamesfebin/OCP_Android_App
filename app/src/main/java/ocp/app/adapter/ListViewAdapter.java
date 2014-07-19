package ocp.app.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import com.ocp.app.R;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;

import ocp.app.activities.DetailActivity;


/**
 * Created by user on 19/07/14.
 */
public class ListViewAdapter extends SimpleCursorAdapter{


Cursor cursor;
    Context context;
    public ListViewAdapter(Context context, int layout, Cursor c, String[] from, int[] to, int flags) {
        super(context, layout, c, from, to, flags);
        this.context = context;
    }

    private static class ViewHolder {
        TextView title;
        ImageView image;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        cursor = getCursor();

        if(cursor.moveToPosition(position)) {
            final ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(context);
                convertView = inflater.inflate(R.layout.rss_item, parent, false);
                viewHolder.title = (TextView) convertView.findViewById(R.id.title);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.image);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.title.setText(cursor.getString(cursor.getColumnIndex("_title")));

            String image = cursor.getString(cursor.getColumnIndex("_image_url"));

            final String title = cursor.getString(cursor.getColumnIndex("_title"));
            final String description = cursor.getString(cursor.getColumnIndex("_description"));
            final String image_url = cursor.getString(cursor.getColumnIndex("_image_url"));
            final String link = cursor.getString(cursor.getColumnIndex("_link"));

            convertView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {


                    Intent intent  = new Intent(context, DetailActivity.class);
                    intent.putExtra("title",title);
                    intent.putExtra("description",description);
                    intent.putExtra("image_url",image_url);
                    intent.putExtra("link",link);

                    context.startActivity(intent);


                }
            });
            if (image.matches("") == false) {


                AQuery aq = new AQuery(context);
                ImageOptions options = new ImageOptions();


                aq.id(viewHolder.image).image(image, options);

            } else {

                viewHolder.image.setVisibility(View.GONE);
            }

            // Return the completed view to render on screen
        }
    return convertView;
    }
}
