package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import com.second.walls.mitchell.walls.gameobjects.Wall;
import com.second.walls.mitchell.walls.managers.WallManager;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-07-13.
 */
public class InvisibleWallsHazard extends Hazard {

    public InvisibleWallsHazard(){
        iconPaint.setColor(Color.YELLOW);
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setTextSize(Constants.PLAYER_WIDTH * 1.5f);
        canvas.drawText("INVISIWALL",0, Constants.SCREEN_HEIGHT * 0.4f, paint);
    }

    @Override
    public void onWallUpdate(WallManager wallmanager, double delta) {
        wallmanager.regularUpdate(delta);

        for (Wall wall: wallmanager.getWalls()){
            if (wall.getY() > Constants.SCREEN_HEIGHT - 13 * Constants.PLAYER_WIDTH){
                wall.draw(false);
            }
        }

        for (Wall wall : wallmanager.getPassed()){
            wall.draw(true);
        }
    }

    @Override
    public void onTermination() {
        for (Wall wall : wallManager.getWalls()){
            wall.draw(true);
        }
        for (Wall wall : wallManager.getPassed())
            wall.draw(true);
    }

    @Override
    public void update(double delta) {

    }
}
