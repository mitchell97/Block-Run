package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.second.walls.mitchell.walls.gameobjects.RectPlayer;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-06-23.
 */
public class IceHazard extends Hazard{

    private static int levelTime = 5;

    private final int PLAYER_ACCELERATION = 1;
    private final double FRICTION_DECELERATION = 0.1;
    private int playerDirection;
    private double playerSpeed;

    public IceHazard(){
        super();
        iconPaint.setColor(Color.CYAN);
        playerDirection = 0;
        playerSpeed = 0;
    }

    @Override
    public void onPlayerUpdate(RectPlayer player, double delta) {
        if (player.isMovingLeft()){
          //  Log.d("Called", "left");
            leftPressed(player, delta);
        }

        if (player.isMovingRight()) {
          //  Log.d("Called", "right");
            rightPressed(player, delta);
        }
        if (!player.isMovingRight() && !player.isMovingLeft()){
           // Log.d("Called", "nothing pressed");
            if (playerDirection != 0){
                playerSpeed = playerSpeed - FRICTION_DECELERATION * delta * Constants.GAME_SPEED;
                if (playerSpeed <= 0) {
                    playerSpeed = 0;
                    playerDirection = 0;
                }

                if (playerDirection < 0) {
                    getNewXLeft(player, delta);
                }

                if (playerDirection > 0){
                    getNewXRight(player, delta);
                }
            }
        }
    }

    private void leftPressed(RectPlayer player, double delta){
        if (playerDirection == 0 && player.getX() > 0){
            playerDirection = -1;
        }
        if (playerDirection < 0){
            if (playerSpeed + PLAYER_ACCELERATION * delta * Constants.GAME_SPEED > player.speed()){
                playerSpeed = player.speed();
            }else{
                playerSpeed += PLAYER_ACCELERATION * delta * Constants.GAME_SPEED;
            }

            getNewXLeft(player, delta);
        }
        if (playerDirection > 0){
            if (playerSpeed - PLAYER_ACCELERATION * delta * Constants.GAME_SPEED < 0){
                playerSpeed = Math.abs(playerSpeed - PLAYER_ACCELERATION * delta * Constants.GAME_SPEED);
                playerDirection = -1;
                getNewXLeft(player,delta);
            }else{
                playerSpeed = playerSpeed - PLAYER_ACCELERATION * delta * Constants.GAME_SPEED;
                getNewXRight(player, delta);
            }
        }

    }

    private void rightPressed(RectPlayer player, double delta){
        if (playerDirection == 0 && player.getX() < Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH){
            playerDirection = 1;
        }
        if (playerDirection > 0){
            if (playerSpeed + PLAYER_ACCELERATION * delta * Constants.GAME_SPEED> player.speed()){
                playerSpeed = player.speed();
            }else {
                playerSpeed = playerSpeed + PLAYER_ACCELERATION * delta * Constants.GAME_SPEED;
            }
            getNewXRight(player, delta);
        }
        if (playerDirection < 0){
            if (playerSpeed - PLAYER_ACCELERATION * delta * Constants.GAME_SPEED < 0){
                playerSpeed = Math.abs(playerSpeed - PLAYER_ACCELERATION * delta * Constants.GAME_SPEED);
                playerDirection = 1;
                getNewXRight(player,delta);
            }else{
                playerSpeed = playerSpeed - PLAYER_ACCELERATION * delta * Constants.GAME_SPEED;
                getNewXLeft(player, delta);
            }
        }
    }

    private void getNewXLeft(RectPlayer player, double delta){
        int newX = player.getX() - (int) (playerSpeed * delta * Constants.GAME_SPEED);

        if (newX <= 0){
            playerSpeed = 0;
            playerDirection = 0;
            player.setX(0);
        }else{
            player.setX(newX);
        }
    }

    private void getNewXRight(RectPlayer player, double delta){
        int newX = player.getX() + (int) (playerSpeed * delta * Constants.GAME_SPEED);

        if (newX >= Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH){
            playerSpeed = 0;
            playerDirection = 0;
            player.setX(Constants.SCREEN_WIDTH - Constants.PLAYER_WIDTH);
        }
        else {
            player.setX(newX);
        }
    }


    @Override
    public void update(double delta) {
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.CYAN);
        paint.setTextSize(Constants.PLAYER_WIDTH * 1.5f);
        canvas.drawText("ICE HAZARD",0, Constants.SCREEN_HEIGHT * 0.4f, paint);
    }

    @Override
    public void initPlayer(RectPlayer player) {
        playerDirection = 0;
        playerSpeed = 0;

        if (player.isMovingLeft()){
            playerSpeed = player.speed();
            playerDirection = -1;
        }

        if (player.isMovingRight()){
            playerSpeed = player.speed();
            playerDirection = 1;
        }
    }
}
