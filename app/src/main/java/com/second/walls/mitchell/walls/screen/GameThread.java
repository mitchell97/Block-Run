package com.second.walls.mitchell.walls.screen;

import android.util.Log;

/**
 * Created by Mitchell on 2017-06-04.
 */
public class GameThread extends Thread {
    public int MAX_FPS;
    public static final int BASE = 60;
    private final long OPTIMAL_TIME ;//= 1000000000 / MAX_FPS;

    private int fps;
    private double GameSpeedPerMilliSec = 0.000025;

    private GamePanel gamePanel;
    private GameCanvas gameCanvas;

    private volatile boolean running;

    public void setRunning(boolean running){
        this.running = running;
    }

    public GameThread(GameCanvas canvas, GamePanel gamePanel, int FPS){
        super();
        Log.d("Init" ,Integer.toString(FPS));
        this.gamePanel = gamePanel;
        this.gameCanvas = canvas;
        MAX_FPS = FPS;
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

            // update the game logic
            gamePanel.update(delta);

            // draw everyting
            gameCanvas.postInvalidate();

            try{
                Thread.sleep( (lastLoopTime-System.nanoTime() + OPTIMAL_TIME)/1000000 );
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
