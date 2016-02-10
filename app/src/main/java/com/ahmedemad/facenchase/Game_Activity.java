package com.ahmedemad.facenchase;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class Game_Activity extends Activity {

    private View mDecorView;

    public static int LastSeconds, LastMSeconds;

    public static MediaPlayer Music;

    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDecorView = getWindow().getDecorView();
        setContentView(R.layout.activity_game);

        Music = MediaPlayer.create(this, R.raw.music);
        Music.start();

        sharedpreferences = getSharedPreferences(Main_Activity.MyPrefrrencesKEY, Context.MODE_PRIVATE);
    }

    @Override
    public void onDestroy() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Main_Activity.LastSecondsKEY, LastSeconds);
        editor.putInt(Main_Activity.LastMSecondsKEY, LastMSeconds);
        editor.commit();

        try {
            Music.stop();
            Music.reset();
            Music.release();
            Music = null;
        }catch (NullPointerException e){}

        super.onDestroy();
    }

    @Override
    public void onStop() {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Main_Activity.LastSecondsKEY, LastSeconds);
        editor.putInt(Main_Activity.LastMSecondsKEY, LastMSeconds);
        editor.commit();

        try {
            Music.stop();
            Music.reset();
            Music.release();
            Music = null;
        }catch (NullPointerException e){}

        super.onStop();
    }

    @Override
    public void onResume()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Main_Activity.LastSecondsKEY, LastSeconds);
        editor.putInt(Main_Activity.LastMSecondsKEY, LastMSeconds);
        editor.commit();

        try {
            if (!(Music.isPlaying()))
            {
                if (!(Game_Activity.Music.isPlaying()))
                    Game_Activity.Music.start();
            }
        }catch (NullPointerException e) {
                Game_Activity.Music = MediaPlayer.create(this, R.raw.music);
                Game_Activity.Music.start();
        }


        super.onResume();
    }

    @Override
    public void onPause()
    {
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(Main_Activity.LastSecondsKEY, LastSeconds);
        editor.putInt(Main_Activity.LastMSecondsKEY, LastMSeconds);
        editor.commit();
        super.onPause();

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