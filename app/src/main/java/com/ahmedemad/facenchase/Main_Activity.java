package com.ahmedemad.facenchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class Main_Activity extends Activity {

    Button Start;

    TextView Best, Last;
    private View mDecorView;

    SharedPreferences sharedpreferences;

    int lastseconds, lastmseconds, bestseconds, bestmseconds;

    public static final String MyPrefrrencesKEY = "Score" ;
    public static final String LastSecondsKEY = "LastSeconds";
    public static final String LastMSecondsKEY = "LastMSeconds";
    public static final String BestSecondsKEY = "BestSeconds";
    public static final String BestMSecondsKEY = "BestMSeconds";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecorView = getWindow().getDecorView();
        setContentView(R.layout.activity_main);

        Start = (Button) findViewById(R.id.B_Start);
        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Game_Activity.class);
                startActivity(intent);
            }
        });

        Best = (TextView) findViewById(R.id.Best);
        Last = (TextView) findViewById(R.id.Last);

        sharedpreferences = getSharedPreferences(MyPrefrrencesKEY, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putInt(Main_Activity.LastSecondsKEY, 0);
        editor.putInt(Main_Activity.LastMSecondsKEY, 0);
        editor.commit();

        if (sharedpreferences.contains(BestSecondsKEY))
        {
            bestseconds = sharedpreferences.getInt(BestSecondsKEY, 0);
        }
        if (sharedpreferences.contains(BestMSecondsKEY))
        {
            bestmseconds = sharedpreferences.getInt(BestMSecondsKEY, 0);
        }

        if (bestseconds>=10)
            Best.setText(" " + bestseconds + "." + bestmseconds + " ");
        else
            Best.setText(" 0"+bestseconds + "." + bestmseconds + " ");

        Last.setText(" 00.0 ");
    }

    @Override
    public void onResume()
    {
        super.onResume();


        if (sharedpreferences.contains(LastSecondsKEY))
        {
            lastseconds = sharedpreferences.getInt(LastSecondsKEY, 0);
        }
        if (sharedpreferences.contains(LastMSecondsKEY))
        {
            lastmseconds = sharedpreferences.getInt(LastMSecondsKEY, 0);
        }


        if (sharedpreferences.contains(BestSecondsKEY))
        {
            bestseconds = sharedpreferences.getInt(BestSecondsKEY, 0);
        }
        if (sharedpreferences.contains(BestMSecondsKEY))
        {
            bestmseconds = sharedpreferences.getInt(BestMSecondsKEY, 0);
        }

        if ((lastseconds*10 + lastmseconds) > (bestseconds*10 + bestmseconds))
        {
            SharedPreferences.Editor editor = sharedpreferences.edit();
            editor.putInt(Main_Activity.BestSecondsKEY, lastseconds);
            editor.putInt(Main_Activity.BestMSecondsKEY, lastmseconds);
            editor.commit();
            if (lastseconds>=10)
                Best.setText(" " + lastseconds + "." + lastmseconds + " ");
            else
                Best.setText(" 0"+lastseconds + "." + lastmseconds + " ");
        }

        if (lastseconds>=10)
            Last.setText(" " + lastseconds + "." + lastmseconds + " ");
        else
            Last.setText(" 0"+lastseconds + "." + lastmseconds + " ");

        try {
            Game_Activity.Music.stop();
            Game_Activity.Music.reset();
            Game_Activity.Music.release();
            Game_Activity.Music = null;
        }catch (NullPointerException e){}
    }

    @Override
    public void onPause()
    {
        super.onPause();
        try {
            Game_Activity.Music.stop();
            Game_Activity.Music.reset();
            Game_Activity.Music.release();
            Game_Activity.Music = null;
        }catch (NullPointerException e){}
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Main_Activity.LastSecondsKEY, 0);
        editor.putInt(Main_Activity.LastMSecondsKEY, 0);
        editor.commit();
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}