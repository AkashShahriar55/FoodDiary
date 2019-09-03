package com.example.akash.fooddiary;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView tv;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View decorView = getWindow().getDecorView();
        // Hide both the navigation bar and the status bar.
        // SYSTEM_UI_FLAG_FULLSCREEN is only available on Android 4.1 and higher, but as
        // a general rule, you should design your app to hide the status bar whenever you
        // hide the navigation bar.
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                | View.SYSTEM_UI_FLAG_IMMERSIVE);
        setContentView(R.layout.activity_splash_screen);

        tv = (TextView) findViewById(R.id.txt_home);
        Typeface custom_font= Typeface.createFromAsset(getAssets(),"fonts/ShadowsIntoLight.ttf");
        tv.setTypeface(custom_font);
        StartAnimations();
    }

    private void StartAnimations() {

        Animation fadeIn = new AlphaAnimation(0.0f, 1.0f);
        fadeIn.setInterpolator(new AccelerateInterpolator()); // add this
        fadeIn.setStartOffset(1200);
        fadeIn.setDuration(1000);

        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        tv.startAnimation(fadeIn);


        Thread splashTread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    // Splash screen pause time
                    while (waited < 3500) {
                        sleep(100);
                        waited += 100;
                    }
                    Intent intent = new Intent(SplashScreen.this,
                            Main2Activity.class);
                    startActivity(intent);
                    SplashScreen.this.finish();
                } catch (InterruptedException e) {
                    // do nothing
                } finally {
                    SplashScreen.this.finish();
                }

            }
        };
        splashTread.start();

    }
}
