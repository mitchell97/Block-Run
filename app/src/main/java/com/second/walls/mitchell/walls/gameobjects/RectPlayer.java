package com.second.walls.mitchell.walls.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.shapes.RoundRectShape;
import android.util.Log;

import com.second.walls.mitchell.walls.hazards.Hazard;
import com.second.walls.mitchell.walls.hazards.HazardListener;
import com.second.walls.mitchell.walls.hazards.IceHazard;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-05-09.
 */
public class RectPlayer implements GameObject, HazardListener{

    private RectF rect;
   // private Rect rectangle;
    private int x,y;
    private Paint paint;

    private final int WIDTH = Constants.SCREEN_WIDTH/10;
    private final int HEIGHT = WIDTH;
    private int speed = 18;
    private Hazard hazard;

    private boolean movingLeft, movingRight;

    public void setMovingLeft(boolean bool){movingLeft = bool;}
    public void setMovingRight(boolean bool){movingRight = bool;}
    public boolean isMovingLeft() {
        return movingLeft;
    }
    public boolean isMovingRight() {
        return movingRight;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int speed(){return speed;}

    public RectPlayer(int colour){
        Constants.PLAYER_WIDTH = WIDTH;
        Constants.CORNER_RADIUS = WIDTH/10;
        x = Constants.SCREEN_WIDTH/2 - WIDTH/2;
        y = Constants.SCREEN_HEIGHT - (HEIGHT*6);
       // rectangle = new Rect(x, y,x + WIDTH,y + HEIGHT);
        rect = new RectF(x, y, x + WIDTH, y + HEIGHT);

        paint = new Paint();
        paint.setColor(colour);

        movingLeft = false;
        movingRight = false;
    }

    @Override
    public void notify(Hazard hazard) {
        this.hazard = hazard;
        if (hazard != null)
            hazard.initPlayer(this);
    }

    @Override
    public void draw(Canvas canvas) {
//      rectangle = new Rect(x, y, x + WIDTH,y + HEIGHT);
//     canvas.drawRect(rectangle, paint);

        rect = new RectF(x, y, x + WIDTH, y + HEIGHT);
        canvas.drawRoundRect(rect , Constants.CORNER_RADIUS, Constants.CORNER_RADIUS, paint);
    }

    @Override
    public void update(double delta) {
        if (hazard == null)
            regularUpdate(delta);
        else
            hazard.onPlayerUpdate(this, delta);
    }

    public void regularUpdate(double delta){
        if (movingLeft)
            moveLeft(delta);
        if (movingRight)
            moveRight(delta);
    }


    public void moveLeft(double delta){
        //Log.d("GOING", "LEFT");
        if (x - (speed * delta * Constants.GAME_SPEED) <= 0 ){
            x = 0;
            movingLeft = false;
        }else{
            x = x - (int)(speed * delta * Constants.GAME_SPEED);
        }
    }

    public void moveRight(double delta){
        //Log.d("GOING", "RIGHT");
        if (x + (speed *  delta * Constants.GAME_SPEED) >= Constants.SCREEN_WIDTH - WIDTH ){
            x = Constants.SCREEN_WIDTH - WIDTH;
            movingRight = false;
        }else{
            x = x + (int)(speed *  delta * Constants.GAME_SPEED);
        }
    }
}
