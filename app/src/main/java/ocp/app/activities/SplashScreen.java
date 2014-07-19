package ocp.app.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.ocp.app.R;

/**
 * Created by user on 19/07/14.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);



        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {



                    Intent mainIntent = new Intent(SplashScreen.this,MainActivity.class);

                    startActivity(mainIntent);
                    finish();


            }
        }, 3000);



    }


}

