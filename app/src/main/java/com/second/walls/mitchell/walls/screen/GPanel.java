package com.second.walls.mitchell.walls.screen;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.MainThread;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.second.walls.mitchell.walls.R;
import com.second.walls.mitchell.walls.scenes.GameplayScene;
import com.second.walls.mitchell.walls.scenes.SceneManager;

/**
 * Created by Mitchell on 2017-07-27.
 */
public class GPanel extends SurfaceView implements SurfaceHolder.Callback{

    private GThread thread;
    private SceneManager sceneManager;

    public GPanel(Context context){
        super(context);
        getHolder().addCallback(this);

        sceneManager = new SceneManager();
        sceneManager.setActive(new GameplayScene(findViewById(R.id.leftSide), findViewById(R.id.rightSide)));

        thread = new GThread(getHolder(), this);
        setFocusable(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new GThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        while(true){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){e.printStackTrace();}
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return sceneManager.handleTouches(event);
    }

    public void update(double delta){
        sceneManager.handleUpdates(delta);
    }

    public void draw(Canvas canvas){
        super.draw(canvas);
        sceneManager.handleDraws(canvas);
    }

    public GThread getThread() {
        return thread;
    }
}
