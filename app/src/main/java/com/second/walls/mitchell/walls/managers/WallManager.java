package com.second.walls.mitchell.walls.managers;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.util.Log;

import com.second.walls.mitchell.walls.hazards.Hazard;
import com.second.walls.mitchell.walls.hazards.HazardListener;
import com.second.walls.mitchell.walls.screen.Constants;
import com.second.walls.mitchell.walls.gameobjects.RectPlayer;
import com.second.walls.mitchell.walls.gameobjects.Wall;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Deque;

/**
 * Created by Mitchell on 2017-05-27.
 */
public class WallManager implements Manager, HazardListener {
    private Deque<Wall> walls;
    private Deque<Wall> passed;
    private int WALL_COLOUR = Color.BLACK;
    private int WALL_SEPARATION = 5 * Constants.PLAYER_WIDTH;

    private boolean passedBottom;

    private Hazard hazard;

    public WallManager(){
        hazard = null;
        passedBottom = false;
        walls = new ArrayDeque<>();
        passed = new ArrayDeque<>();
        createWall();
    }

    public Deque<Wall> getWalls() {
        return walls;
    }

    public Deque<Wall> getPassed() {
        return passed;
    }

    private void createWall(){
        Wall wall = new Wall(WALL_COLOUR);
        walls.addLast(wall);
    }

    public void handleDraws(Canvas canvas){
        try {
            for (Wall wall : walls)
                wall.draw(canvas);
            for (Wall wall : passed)
                wall.draw(canvas);
        }catch (ConcurrentModificationException cme){
            cme.printStackTrace();
        }
    }

   public void handleUpdates(double delta){
        if (hazard == null) {
            regularUpdate(delta);
        }else {
            hazard.onWallUpdate(this, delta);
        }
   }

    public void regularUpdate(double delta){
        if (walls.isEmpty()) return;

        for (Wall wall : passed){
            if (!wall.isActive()){
                passed.removeFirst();
            }else{
                wall.update(delta);
            }
        }

        Wall last = walls.peekLast();
        if (last.getY() > WALL_SEPARATION)
            createWall();

        for (Wall wall : walls)
            wall.update(delta);
    }

    @Override
    public void notify(Hazard hazard) {
        this.hazard = hazard;
        if (hazard != null){
            hazard.initWalls(this);
        }
    }


    public boolean wallPassed (RectPlayer player){
        if (walls.getFirst().getY() > player.getY() + Constants.PLAYER_WIDTH
                && !passed.contains(walls.peekFirst())){

            passedBottom = false;
            passed.addLast(walls.removeFirst());
            passed.peekLast().changeColour();
            return true;
        }
        return false;
    }


    public boolean playerCollide (RectPlayer player){
        Wall wall = walls.peekFirst();

        if (wall.getY() + wall.getHEIGHT() >= player.getY() && (wall.getX() < player.getX() && wall.getX() + wall.getWallGap() > player.getX() + Constants.PLAYER_WIDTH))
            passedBottom = true;

        if (wall.getY() + Constants.PLAYER_WIDTH < player.getY() || player.getY() + Constants.PLAYER_WIDTH <= wall.getY())
            return false;

        if (wall.getX() > player.getX() || wall.getX() + wall.getWallGap() < player.getX() + Constants.PLAYER_WIDTH) {
            if (passedBottom){
                if (wall.getX() > player.getX()){
                    player.setX(wall.getX());
                }else{
                    player.setX(wall.getX() + wall.getWallGap() - Constants.PLAYER_WIDTH);
                }
            }else{
                player.setY(wall.getY() + Constants.PLAYER_WIDTH);
            }
            wall.draw(true);
            return true;
        }

        return false;
    }

    public int getWALL_SEPARATION() {
        return WALL_SEPARATION;
    }
}
