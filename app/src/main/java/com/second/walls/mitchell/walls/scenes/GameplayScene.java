package com.second.walls.mitchell.walls.scenes;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.second.walls.mitchell.walls.screen.Constants;
import com.second.walls.mitchell.walls.managers.HazardManager;
import com.second.walls.mitchell.walls.gameobjects.PlayerTrail;
import com.second.walls.mitchell.walls.gameobjects.RectPlayer;
import com.second.walls.mitchell.walls.managers.WallManager;

import java.util.zip.CheckedOutputStream;

/**
 * Created by Mitchell on 2017-06-26.
 */
public class GameplayScene implements Scene {

    private RectPlayer player;

    private boolean gameOver;
    private int score;

    private WallManager wallManager;
    private PlayerTrail playerTrail;
    private HazardManager hazardManager;

    private static final long DOUBLE_CLICK_TIME_DELTA = 300;//milliseconds
    private long lastClickTime = 0;

    public GameplayScene(View leftSide, View rightSide) {

        leftSide.getBackground().setAlpha(0);
        leftSide.setOnTouchListener(new View.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                if (gameOver && event.getAction() == MotionEvent.ACTION_DOWN) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        reset();
                        lastClickTime = 0;
                    }
                    lastClickTime = clickTime;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    player.setMovingLeft(true);
                    player.setMovingRight(false);

                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (player.isMovingLeft()) {
                        player.setMovingLeft(false);
                        player.setMovingRight(false);
                    }

                }
                return true;
            }
        });

        rightSide.getBackground().setAlpha(0);
        rightSide.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (gameOver && event.getAction() == MotionEvent.ACTION_DOWN) {
                    long clickTime = System.currentTimeMillis();
                    if (clickTime - lastClickTime < DOUBLE_CLICK_TIME_DELTA) {
                        reset();
                        lastClickTime = 0;
                    }
                    lastClickTime = clickTime;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    player.setMovingRight(true);
                    player.setMovingLeft(false);
                }

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (player.isMovingRight()) {
                        player.setMovingLeft(false);
                        player.setMovingRight(false);
                    }
                }
                return true;
            }
        });

        reset();
    }

    private void reset(){
        player = new RectPlayer(Color.RED);
        wallManager = new WallManager();
        hazardManager = new HazardManager();
        hazardManager.addListener(player);
        hazardManager.addListener(wallManager);
        playerTrail = new PlayerTrail(Color.RED, player);
        score = 0;
        Constants.GAME_SPEED = Constants.START_GAME_SPEED;
        gameOver = false;
    }

    @Override
    public void update(double delta){
        if (!gameOver) {
            player.update(delta);
            wallManager.handleUpdates(delta);
            if (wallManager.playerCollide(player)){
                gameOver = true;
                return;
            }
            hazardManager.handleUpdates(delta, score);
            playerTrail.update(delta);

            if (wallManager.wallPassed(player))
                score++;

        } else{
        }
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.WHITE);
        wallManager.handleDraws(canvas);
        hazardManager.handleDraws(canvas);
        playerTrail.draw(canvas);
        player.draw(canvas);
        drawScore(canvas);
    }

    @Override
    public boolean receiveTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && gameOver)
            reset();
        return true;
    }

    private void drawScore(Canvas canvas){
        Paint p = new Paint();
        p.setTextSize(Constants.PLAYER_WIDTH/2);
        canvas.drawText("Score: " + score, Constants.SCORE_PADDING, Constants.PLAYER_WIDTH/2 + Constants.SCORE_PADDING, p);
    }


    @Override
    public void terminate() {

    }
}
