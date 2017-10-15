package com.second.walls.mitchell.walls.screen;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

/**
 * Created by Mitchell on 2017-07-27.
 */
public class GThread extends Thread {
    public int MAX_FPS = 60;
    public static final int BASE = 60;
    private final long OPTIMAL_TIME ;//= 1000000000 / MAX_FPS;
    private int fps;

    private GPanel gamePanel;
    private SurfaceHolder surfaceHolder;

    private volatile boolean running;

    public static Canvas canvas;

    public GThread(SurfaceHolder surfaceHolder, GPanel gamePanel){
        super();
        this.surfaceHolder = surfaceHolder;
        this.gamePanel = gamePanel;
        OPTIMAL_TIME = 1000000000/MAX_FPS;
    }

    @Override
    public void run() {
        long lastLoopTime = System.nanoTime();
        long lastFpsTime = 0;
        while (running){

            long now = System.nanoTime();
            long updateLength = now - lastLoopTime;
            lastLoopTime = now;
            double delta = (updateLength / ((double)OPTIMAL_TIME)) * ((double)BASE/MAX_FPS);

            // Log.d("Delta", Double.toString(delta));
            // update the frame counter
            lastFpsTime += updateLength;
            fps++;

            //Constants.GAME_SPEED += GameSpeedPerMilliSec * (updateLength / 1000000);

            if (lastFpsTime >= 1000000000) {
                Log.d("FPS", Integer.toString(fps));
                lastFpsTime = 0;
                fps = 0;
            }

            canvas = null;

            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    gamePanel.update(delta);
                    gamePanel.draw(canvas);
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                if (canvas != null){
                    try{
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

//            try{
//                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
//            }catch (Exception e){
//                e.printStackTrace();
//            }
        }
    }

    public void setRunning(boolean running) {
        this.running = running;
    }
}
