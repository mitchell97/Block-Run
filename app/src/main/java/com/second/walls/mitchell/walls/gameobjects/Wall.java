package com.second.walls.mitchell.walls.gameobjects;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

import com.second.walls.mitchell.walls.screen.Constants;

import java.util.Random;

/**
 * Created by Mitchell on 2017-05-27.
 */
public class Wall implements GameObject {

    private final static int HEIGHT = Constants.PLAYER_WIDTH;
    private final static int POSSIBLE_WALL_SPOTS = 8; // the number of different locations walls can be
    private final static Random rand = new Random();
    private final static int WALL_GAP = (int) 2.5 * HEIGHT; // gap between left and right wall

   // private Rect left, right, center;
    private RectF leftF, rightF, centerF;
    private int x, y;
    private int fakeX;
    private Paint paint;
    private boolean active;
    private int holeIndex;

    private boolean fakeHole;
    private boolean draw;
    private int wallGap;

    private int trackDraw = 0;
    private int trackUpdate = 0;

    private final static int [] wallSpots = generateWallSpots();
    private static boolean [] placed = new boolean[POSSIBLE_WALL_SPOTS];

    public void changeColour(){
        paint.setColor(Color.BLUE);
    }

    public void setWallGap(int wallGap){
        if (x == 0){
            this.wallGap = wallGap;
            return;
        }

        if (x == Constants.SCREEN_WIDTH - this.wallGap){
            this.wallGap = wallGap;
            x = Constants.SCREEN_WIDTH - wallGap;
            return;
        }

        int mid = x + this.wallGap/2;
        x = mid - wallGap/2;
        this.wallGap = wallGap;
    }

    public void resetWallGap(){
        if (x == 0){
            wallGap = WALL_GAP;
            return;
        }

        if (x == Constants.SCREEN_WIDTH - this.wallGap){
            wallGap = WALL_GAP;
            x = Constants.SCREEN_WIDTH - wallGap;
            return;
        }

        int mid = x + this.wallGap/2;
        wallGap = WALL_GAP;
        x = mid - wallGap/2;
    }

    private static int [] generateWallSpots(){
        int [] rtn = new int [POSSIBLE_WALL_SPOTS];

        int diff = (Constants.SCREEN_WIDTH - WALL_GAP)/(POSSIBLE_WALL_SPOTS-1);
        int location = 0;
        for (int i = 0; i < POSSIBLE_WALL_SPOTS; i++) {
            if (i == (POSSIBLE_WALL_SPOTS - 1)){
                rtn[i] = Constants.SCREEN_WIDTH - WALL_GAP;
                break;
            }
            rtn[i] = location;
            location += diff;
        }

        for (int i : rtn)
            Log.d("Location", Integer.toString(i));

        return rtn;
    }

    public boolean isActive() {
        return active;
    }

    public int getY(){
        return y;
    }

    public int getX() {
        return x;
    }

    public int getWallGap() {
        return wallGap;
    }

    public static int getHEIGHT() {
        return HEIGHT;
    }

    public boolean hasFakeWall(){return fakeHole;}

    public Wall (int colour){
        draw = true;

        paint = new Paint();
        paint.setColor(colour);

//        left = new Rect(0,0,x,HEIGHT);
//        right = new Rect(0,0,x,HEIGHT);
//        center = new Rect(0,0,x,HEIGHT);

        leftF = new RectF(0,0,x,HEIGHT);
        rightF = new RectF(0,0,x,HEIGHT);
        centerF = new RectF(0,0,x,HEIGHT);

        // Runs though all wall possibilities before duplicate location
        int times = 0;
        int num = rand.nextInt(wallSpots.length);
        while (placed[num]){
            times++;
            num = rand.nextInt(wallSpots.length);
        }
   //     Log.d("Num extra rand calls", Integer.toString(times));
        placed[num] = true;
        boolean reset = true;
        for (int i = 0; i < placed.length; i++) {
           if (!placed[i]){
               reset = false;
               break;
           }
        }
        if (reset)
            for (int i = 0; i < placed.length; i++) {
                placed[i] = false;
            }

        holeIndex = num;
        x = wallSpots[num];
        y = -1 *HEIGHT;

        wallGap = WALL_GAP;
        active = true;
        fakeHole = false;
    }

    @Override
    public void draw(Canvas canvas) {
        if (!draw) return;

        if (!fakeHole) {
//            left = new Rect(0, y, x, y + HEIGHT);
//            right = new Rect(x + wallGap, y, Constants.SCREEN_WIDTH, y + HEIGHT);
//            canvas.drawRect(left, paint);
//            canvas.drawRect(right, paint);

            leftF = new RectF(-10, y, x, y +HEIGHT);
            rightF = new RectF(x + wallGap, y, Constants.SCREEN_WIDTH + 10, y + HEIGHT);
            canvas.drawRoundRect(leftF, Constants.CORNER_RADIUS, Constants.CORNER_RADIUS, paint);
            canvas.drawRoundRect(rightF, Constants.CORNER_RADIUS, Constants.CORNER_RADIUS, paint);
        }else{
//            left = new Rect(0, y, Math.min(x, fakeX), y + HEIGHT);
//            center = new Rect(Math.min(x, fakeX) + WALL_GAP, y, Math.max(x, fakeX), y + HEIGHT);
//            right = new Rect(Math.max(x, fakeX) + WALL_GAP, y , Constants.SCREEN_WIDTH, y + HEIGHT);
//            canvas.drawRect(left, paint);
//            canvas.drawRect(center, paint);
//            canvas.drawRect(right, paint);

            leftF = new RectF(-10, y, Math.min(x, fakeX), y + HEIGHT);
            centerF = new RectF(Math.min(x, fakeX) + WALL_GAP, y, Math.max(x, fakeX), y + HEIGHT);
            rightF = new RectF(Math.max(x, fakeX) + WALL_GAP, y , Constants.SCREEN_WIDTH + 10, y + HEIGHT);

            canvas.drawRoundRect(leftF, Constants.CORNER_RADIUS, Constants.CORNER_RADIUS, paint);
            canvas.drawRoundRect(centerF,Constants.CORNER_RADIUS, Constants.CORNER_RADIUS, paint);
            canvas.drawRoundRect(rightF, Constants.CORNER_RADIUS, Constants.CORNER_RADIUS , paint);

        }

        trackDraw++;
        trackUpdate=0;
        if(trackDraw >= 2){
            Log.d("Track D", Integer.toString(trackDraw));
        }
    }

    @Override
    public void update(double delta) {
        if (y > Constants.SCREEN_HEIGHT)
            active = false;
        else
            y+= Constants.VERTICAL_SPEED * delta * Constants.GAME_SPEED;

        trackDraw = 0;
        trackUpdate++;
        if (trackUpdate >= 2){
            Log.d("Track U", Integer.toString(trackUpdate));
        }
    }

    public void draw(boolean draw){
        this.draw = draw;
    }

    public void generateFakeWall(){
        fakeHole = true;
        int num = rand.nextInt(wallSpots.length);
        while (Math.abs(num - holeIndex) < 3){
            num = rand.nextInt(wallSpots.length);
        }

        fakeX = wallSpots[num];
    }

    public void destroyFakeWall(){
        fakeHole = false;
    }
}
