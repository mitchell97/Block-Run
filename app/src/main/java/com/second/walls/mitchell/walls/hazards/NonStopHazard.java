package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.second.walls.mitchell.walls.gameobjects.RectPlayer;
import com.second.walls.mitchell.walls.managers.WallManager;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-07-20.
 */
public class NonStopHazard extends Hazard {

    private int direction;

    public NonStopHazard(){
        iconPaint.setColor(Color.RED);
        direction = 0;
    }

    @Override
    public void onPlayerUpdate(RectPlayer player, double delta){
        if (player.isMovingLeft()){
            direction = -1;
        }
        if (player.isMovingRight()){
            direction = 1;
        }

        if (direction < 0){
            player.moveLeft(delta);
        }

        if (direction > 0){
            player.moveRight(delta);
        }
    }

    @Override
    public void onWallUpdate(WallManager wallManager, double delta) {
        wallManager.getWalls().getFirst().setWallGap((int)(Constants.PLAYER_WIDTH * 3.5));
        wallManager.regularUpdate(delta);
    }

    @Override
    public void initPlayer(RectPlayer player) {
        direction = 0;
    }

    @Override
    public void onTermination() {
        wallManager.getWalls().getFirst().resetWallGap();
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(Constants.PLAYER_WIDTH * 1.5f);
        canvas.drawText("Non-Stop",0, Constants.SCREEN_HEIGHT * 0.4f, paint);
    }
}
