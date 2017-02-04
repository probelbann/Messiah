package com.pocketmessiah.messiah.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;

import com.pocketmessiah.messiah.R;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class Messiah extends AppCompatActivity {

    TextView qouteTextView;
    String qoute = "";
    Animation anim;

    static String qouteForNotification = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messiah);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-8391218411454353~1229412623");
        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

            try {
                if(qouteForNotification.equals("")) {
                    onClickFindQoute(qouteTextView);
                    qouteForNotification = getNotificationQoute();
                }
                else setTextView();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }

    public void setTextView(){
        qouteTextView = (TextView) findViewById(R.id.qouteTextView);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/andantino_script.ttf");
        qouteTextView.setTypeface(typeFace);
        qouteTextView.setText(qouteForNotification);
        qouteTextView.startAnimation(anim);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.app_menu, menu);//Menu Resource, Menu
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.reminder:
                Intent intent = new Intent(Messiah.this, RemindActivity.class);
                startActivity(intent);
                break;
            case R.id.share:
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, qoute + "\n" + "https://play.google.com/store/apps/details?id=com.pocketmessiah.messiah");
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public void onClickFindQoute(View view) throws IOException {
        Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(40);

        qouteTextView = (TextView) findViewById(R.id.qouteTextView);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/andantino_script.ttf");
        qouteTextView.setTypeface(typeFace);

        int randQoute = (int) (Math.random()*203+1);
        int counter = 0;

        InputStream instream = getResources().openRawResource(R.raw.messiah);

        if(Locale.getDefault().getLanguage().equals("en")){
            instream = getResources().openRawResource(R.raw.messiah_en);
        }

        InputStreamReader inputreader = new InputStreamReader(instream);
        BufferedReader fin = new BufferedReader(inputreader);
        String line;
        while ((line = fin.readLine()) != null) {
            if (line.equals("_")) {
                counter++;
            }
            if (counter == randQoute) {
                qoute = "";
                while (true) {
                    line = fin.readLine();
                    if (line.equals("_")) {
                        counter++;
                        break;
                    }
                    qoute = qoute.concat(line).concat("\n");
                }
                anim = AnimationUtils.loadAnimation(this,R.anim.appear_text);
                qouteTextView.setText(qoute);
                qouteTextView.startAnimation(anim);
            }
        }
    }

    public String getNotificationQoute() throws IOException {
        int randQoute = (int) (Math.random() * 203 + 1);
        int counter = 0;

        InputStream instream = getResources().openRawResource(R.raw.messiah);

        if(Locale.getDefault().getLanguage().equals("en")){
            instream = getResources().openRawResource(R.raw.messiah_en);
        }

        InputStreamReader inputreader = new InputStreamReader(instream);
        BufferedReader fin = new BufferedReader(inputreader);
        String line;
        while ((line = fin.readLine()) != null) {
            if (line.equals("_")) {
                counter++;
            }
            if (counter == randQoute) {
                qoute = "";
                while (true) {
                    line = fin.readLine();
                    if (line.equals("_")) {
                        counter++;
                        break;
                    }
                    qoute = qoute.concat(line).concat("\n");
                }
            }
        }
        return qoute;
    }


}