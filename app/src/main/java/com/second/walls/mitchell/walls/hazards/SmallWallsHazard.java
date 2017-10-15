package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.second.walls.mitchell.walls.gameobjects.RectPlayer;
import com.second.walls.mitchell.walls.gameobjects.Wall;
import com.second.walls.mitchell.walls.managers.WallManager;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-07-16.
 */
public class SmallWallsHazard extends Hazard {

    public SmallWallsHazard(){
        iconPaint.setColor(Color.GREEN);
    }

    @Override
    public void onWallUpdate(WallManager wallManager, double delta) {
        wallManager.getWalls().getFirst().setWallGap((int) (1.50 * (double) Constants.PLAYER_WIDTH));
        wallManager.regularUpdate(delta);
    }

    @Override
    public void onTermination() {
        for (Wall wall : wallManager.getWalls()){
            wall.resetWallGap();
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setTextSize(Constants.PLAYER_WIDTH * 1.5f);
        canvas.drawText("Small Walls",0, Constants.SCREEN_HEIGHT * 0.4f, paint);
    }
}
