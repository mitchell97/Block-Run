package com.second.walls.mitchell.walls.gameobjects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.second.walls.mitchell.walls.screen.Constants;

import java.util.ArrayDeque;
import java.util.ConcurrentModificationException;
import java.util.Deque;

/**
 * Created by Mitchell on 2017-06-20.
 */
public class PlayerTrail implements GameObject {

    public static class TrailCrumb implements GameObject {

        private Paint paint;
        private int x;
        private int y;
        private boolean active;

        private TrailCrumb(int colour, int x, int y){
            paint = new Paint();
            paint.setColor(colour);
            this.x = x;
            this.y = y;
            active = true;
        }

        @Override
        public void update(double delta) {
            if (y > Constants.SCREEN_HEIGHT)
                active = false;
            else
                y+= Constants.VERTICAL_SPEED * delta * Constants.GAME_SPEED;
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(x,y, (float) Constants.PLAYER_WIDTH/10, paint);
        }

        public boolean isActive() {
            return active;
        }
    }

    private int colour;
    private final float CRUMB_SPACE = Constants.PLAYER_WIDTH/1.5f;
    private Deque<TrailCrumb> crumbs;
    private RectPlayer player;

    public PlayerTrail (int colour, RectPlayer player){
        this.colour = colour;
        this.player = player;
        crumbs = new ArrayDeque<>();
    }

    @Override
    public void update(double delta){
        if (crumbs.isEmpty()) {
            crumbs.add(new TrailCrumb(colour, player.getX() + Constants.PLAYER_WIDTH/2, player.getY() + Constants.PLAYER_WIDTH));
        }

        TrailCrumb first = crumbs.peekFirst();
        while (!first.isActive()) {
            crumbs.removeFirst();
            first = crumbs.peekFirst();
        }

        TrailCrumb last = crumbs.peekLast();
        if (CRUMB_SPACE < Math.sqrt(Math.pow(last.x - (player.getX() + Constants.PLAYER_WIDTH/2),2) + Math.pow( last.y - (player.getY() + Constants.PLAYER_WIDTH),2)))
            crumbs.add(new TrailCrumb(colour, player.getX() + Constants.PLAYER_WIDTH/2, player.getY() + Constants.PLAYER_WIDTH));

        for (TrailCrumb crumb: crumbs)
            crumb.update(delta);
    }

    @Override
    public void draw(Canvas canvas){
        try {
            for (TrailCrumb crumb : crumbs)
                crumb.draw(canvas);
        } catch (ConcurrentModificationException cme){
            cme.printStackTrace();
        }
    }
}
