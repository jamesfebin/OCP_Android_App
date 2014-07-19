package ocp.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.ImageOptions;
import com.ocp.app.R;

/**
 * Created by user on 19/07/14.
 */
public class DetailActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        TextView titleView,descriptionView;
        ImageView imageView;
        Button shareButton;



        super.onCreate(savedInstanceState);

        setContentView(R.layout.detail_view);


       final String title = getIntent().getExtras().getString("title");
        final String image = getIntent().getExtras().getString("image_url");
        final String description = getIntent().getExtras().getString("description");
        final String link = getIntent().getExtras().getString("link");


        titleView = (TextView)findViewById(R.id.detailTitle);
        descriptionView = (TextView) findViewById(R.id.detailDescription);
        imageView = (ImageView) findViewById(R.id.detailImage);


        shareButton = (Button) findViewById(R.id.shareLink);

        shareButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                Intent share = new Intent(android.content.Intent.ACTION_SEND);
                share.setType("text/plain");
                share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

                // Add data to the intent, the receiving app will decide
                // what to do with it.
                share.putExtra(Intent.EXTRA_SUBJECT, title);
                share.putExtra(Intent.EXTRA_TEXT, link);

                startActivity(Intent.createChooser(share, "Share link!"));

            }
        });

        titleView.setText(title);
        descriptionView.setText(Html.fromHtml(description));


        descriptionView.setError(null);
        if(image.matches("")==false)
        {
        AQuery aq = new AQuery(getApplicationContext());
        ImageOptions options = new ImageOptions();
        aq.id(imageView).image(image, options);


            }
        else
        {
            imageView.setVisibility(View.GONE);
        }


    }



}
