package com.second.walls.mitchell.walls.screen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

import com.second.walls.mitchell.walls.R;
import com.second.walls.mitchell.walls.screen.Constants;
import com.second.walls.mitchell.walls.screen.GamePanel;

public class MainActivity extends Activity {

    private int screenWidth;
    private int screenHeight;
    private int fps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Constants.SCREEN_WIDTH = dm.widthPixels;
        Constants.SCREEN_HEIGHT = dm.heightPixels;

        fps = 60;

        setContentView(R.layout.activity_main);
    }

    public void playBtnPressed(View v){
        Intent play = new Intent(this, GamePanel.class);
        play.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        play.putExtra("fps",fps);
        startActivity(play);

//        Intent play = new Intent(this, Run.class);
//        play.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
//        play.putExtra("fps",fps);
//        startActivity(play);
    }

    public void settingsBtnPressed(View v){
        Intent play = new Intent(this, Settings.class);
        play.putExtra("currentFPS", fps);
        startActivityForResult(play, 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Log.d("Called", "Result: " + data.getIntExtra("fps", 60));
                fps = data.getIntExtra("fps", 60);
                Log.d("FPS", Integer.toString(fps));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.d("Num Threads", Integer.toString(Thread.getAllStackTraces().keySet().size()));
//
//        int num = 0;
//        int ter = 0;
//        int wait = 0;
//        for (Thread thread: Thread.getAllStackTraces().keySet()){
//            if (thread.getState().equals(Thread.State.RUNNABLE)){
//                num++;
//            }
//            if (thread.getState().equals(Thread.State.TERMINATED)){
//                ter++;
//            }
//            if (thread.getState().equals(Thread.State.WAITING)){
//                wait++;
//            }
//        }
//
//        Log.d("Num Runnable Threads", Integer.toString(num));
//        Log.d("Num Terminated Threads", Integer.toString(ter));
//        Log.d("Num Waiting Threads", Integer.toString(wait));

    }

    public int screenWidth(){
        return screenWidth;
    }

    public int screenHeight(){
        return screenHeight;
    }

}
