package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.second.walls.mitchell.walls.gameobjects.Wall;
import com.second.walls.mitchell.walls.managers.WallManager;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-07-22.
 */
public class FakeWallHazard extends Hazard {

    public FakeWallHazard(){
        iconPaint.setColor(Color.DKGRAY);
    }

    @Override
    public void onWallUpdate(WallManager wallManager, double delta) {
        if (!wallManager.getWalls().getLast().hasFakeWall()){
            wallManager.getWalls().getLast().generateFakeWall();
        }

        if (wallManager.getWalls().getFirst().hasFakeWall()){
            wallManager.getWalls().getFirst().destroyFakeWall();
        }
        wallManager.regularUpdate(delta);
    }

    @Override
    public void onTermination() {
        for (Wall wall : wallManager.getWalls()){
            wall.destroyFakeWall();
        }
    }

    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(Constants.PLAYER_WIDTH * 1.5f);
        canvas.drawText("Fake Wall",0, Constants.SCREEN_HEIGHT * 0.4f, paint);
    }

    @Override
    public void update(double delta) {

    }
}
