package com.ahmedemad.facenchase;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;


public class AnimatedView extends ImageView{

	private Context mContext;

    BitmapDrawable fun, whoa, evil1, evil2;

	int X1 = -1, Y1 = -1;
    int X2 = -1, Y2 = -1;
    int X3 = -1, Y3 = -1;

    int Width, Height, ShapeWidth, ShapeHeight, speed=5, textsize=65;

    Boolean flag = true;

	private int X1Velocity = -1;
	private int Y1Velocity =-1;
    private int X2Velocity = -1;
    private int Y2Velocity = -1;

    Boolean Touched = true;

	private Handler h;
	private final int FRAME_RATE = 5;

    int passedMSeconds, mseconds=0, seconds=0;
    Timer timer;
    TimerTask timerTask;
	
	public AnimatedView(Context context, AttributeSet attrs)  {  
		super(context, attrs);  
		mContext = context;  
		h = new Handler();

        fun = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.fun);
        whoa = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.whoa);
        evil1 = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.evil1);
        evil2 = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.evil2);
        //back = (BitmapDrawable) mContext.getResources().getDrawable(R.drawable.back2);

        ShapeWidth = fun.getBitmap().getWidth();
        ShapeHeight = fun.getBitmap().getHeight();

        passedMSeconds = 0;
        reScheduleTimer();
    } 
	
	private Runnable r = new Runnable() {
		@Override
		public void run() {
			invalidate(); 
		}
	};
	
	protected void onDraw(Canvas canvas) {

        //Log.e("Screen!!!!!!!!!!!!!!", "ShapeWidth: " + ShapeWidth + " SreenWidth: " + Width + " ScreenHeight: " + Height);

        Width = this.getWidth();
        Height = this.getHeight();

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawPaint(paint);

        paint.setColor(getResources().getColor(R.color.white));
        paint.setTextSize(textsize);

        canvas.drawText(seconds+"."+mseconds, Width/20, Height/20, paint);

        if (X1Velocity<0 && X2Velocity<0 && Y1Velocity<0 && Y1Velocity<0 && flag)
        {
            if (Width<550 || Height<950)
            {
                X1Velocity = 6;
                Y1Velocity = 3;
                X2Velocity = 4;
                Y2Velocity = 2;
                speed = 6;
                textsize=45;
            }
            else if (Width<850 || Height<1400)
                textsize = 55;
            else
            {
                X1Velocity = 10;
                Y1Velocity = 5;
                X2Velocity = 8;
                Y2Velocity = 4;
            }
            flag = false;
        }

        if (X1<0 && Y1<0) {
            X1 = 0;
            Y1 = 0;
        }
        if (X2<0 && Y2<0) {
            X2 = 0;
            Y2 = (Height/3)*2;
        }
        if (X3<0 && Y3<0) {
            X3 = Width / 2;
            Y3 = Height / 2;
        }

            X1 += X1Velocity;
            Y1 += Y1Velocity;
            if ((X1 > Width - ShapeWidth) || (X1 < 0)) {
                X1Velocity = X1Velocity * -1;
            }
            if ((Y1 > Height - ShapeHeight) || (Y1 < 0)) {
                Y1Velocity = Y1Velocity * -1;
            }

            X2 += X2Velocity;
            Y2 += Y2Velocity;
            if ((X2 > Width - ShapeWidth) || (X2 < 0)) {
                X2Velocity = X2Velocity * -1;
            }
            if ((Y2 > Height - ShapeHeight) || (Y2 < 0)) {
                Y2Velocity = Y2Velocity * -1;
            }

        int x1=X1+ShapeWidth/6, x2=X2+ShapeWidth/6, x3=X3-ShapeWidth/2+ShapeWidth/6;
        int y1=Y1+ShapeHeight/6, y2=Y2+ShapeHeight/6, y3=Y3-ShapeHeight;
        int shapewidth = (ShapeWidth/6) * 4, shapeheight = (ShapeHeight/6) * 4;


        if ((((y1+shapeheight >= y2) && (y1+shapeheight < y2+shapeheight))
                || ((y2+shapeheight >= y1) && (y2+shapeheight < y1+shapeheight)))
                && (((x1+shapewidth >= x2) && (x1+shapeheight < x2+shapewidth))
                || ((x2+shapewidth >= x1) && (x2+shapewidth < x1+shapewidth)))) {
            X1Velocity = X1Velocity * -1;
            X2Velocity = X2Velocity * -1;
            Y1Velocity = Y1Velocity * -1;
            Y2Velocity = Y2Velocity * -1;
        }


        if ((((y1+shapeheight >= y3) && (y1+shapeheight < y3+shapeheight))
                || ((y3+shapeheight >= y1) && (y3+shapeheight < y1+shapeheight)))
                && (((x1+shapewidth >= x3) && (x1+shapeheight < x3+shapewidth))
                || ((x3+shapewidth >= x1) && (x3+shapewidth < x1+shapewidth)))) {

            Game_Activity.LastSeconds = seconds;
            Game_Activity.LastMSeconds = mseconds;

            ((Activity)getContext()).finish();
        }

        if ((((y2+shapeheight >= y3) && (y2+shapeheight < y3+shapeheight))
                || ((y3+shapeheight >= y2) && (y3+shapeheight < y2+shapeheight)))
                && (((x2+shapewidth >= x3) && (x2+shapewidth < x3+shapewidth))
                || ((x3+shapewidth >= x2) && (x3+shapewidth < x2+shapewidth)))) {

            Game_Activity.LastSeconds = seconds;
            Game_Activity.LastMSeconds = mseconds;

            ((Activity)getContext()).finish();
        }

	    canvas.drawBitmap(evil1.getBitmap(), X1, Y1, null);

        canvas.drawBitmap(evil2.getBitmap(), X2, Y2, null);

        if (Touched)
            canvas.drawBitmap(fun.getBitmap(), X3-ShapeWidth/2, Y3-(ShapeHeight+ShapeHeight/6), null);
        else
            canvas.drawBitmap(whoa.getBitmap(), X3-ShapeWidth/2, Y3-(ShapeHeight+ShapeHeight/6), null);

        Touched = false;

        h.postDelayed(r, FRAME_RATE);
	}

    public boolean onTouchEvent(MotionEvent event) {

        Touched = true;

        handleActionDown((int)event.getX(), (int)event.getY());

        if(Touched) {
            if (!((event.getX()-ShapeWidth/2 > Width - ShapeWidth) || (event.getX()-ShapeWidth/2 < 0))) {
                X3 = (int) event.getX(0);
            }
            if (!((event.getY()-(ShapeHeight+ShapeHeight/6) > Height - ShapeHeight) || (event.getY()-(ShapeHeight+ShapeHeight/6) < 0))) {
                Y3 = (int) event.getY(0);
            }

        }

        return true;
    }

    public void handleActionDown(int eventX, int eventY) {

        if (eventX >= (X3 - (ShapeWidth/2)) && (eventX <= (X3 + ShapeWidth))) {
            if (eventY >= (Y3 - (ShapeHeight)) && (eventY <= (Y3 + ShapeHeight/2))) {
                Touched = true;
            } else {
                Touched = false;
            }
        } else {
            Touched = false;
        }
    }


    public void reScheduleTimer(){
        timer = new Timer();
        timerTask = new myTimerTask();
        timer.schedule(timerTask, 0, 100);
    }

    private class myTimerTask extends TimerTask{
        @Override
        public void run() {
            // TODO Auto-generated method stub
            passedMSeconds++;
            updateLabel.sendEmptyMessage(0);
        }
    }

    private Handler updateLabel = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);

            mseconds = passedMSeconds % 10;
            seconds = passedMSeconds / 10;

            if (seconds<1)
            {
                try {
                    if (!(Game_Activity.Music.isPlaying()))
                        Game_Activity.Music.start();
                }catch (NullPointerException e){
                    Game_Activity.Music = MediaPlayer.create(mContext, R.raw.music);
                    Game_Activity.Music.start();
                }
            }

            if ((seconds%speed == 0) && (mseconds == 0))
            {
                if (Y1Velocity<0)
                    Y1Velocity--;
                if (Y1Velocity>0)
                    Y1Velocity++;

                if (X1Velocity<0)
                    X1Velocity-=2;
                if (X1Velocity>0)
                    X1Velocity+=2;


                if (Y2Velocity<0)
                    Y2Velocity--;
                if (Y2Velocity>0)
                    Y2Velocity++;

                if (X2Velocity<0)
                    X2Velocity-=2;
                if (X2Velocity>0)
                    X2Velocity+=2;
            }
        }
    };

}