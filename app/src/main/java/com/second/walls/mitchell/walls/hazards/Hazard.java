package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.second.walls.mitchell.walls.gameobjects.RectPlayer;
import com.second.walls.mitchell.walls.managers.WallManager;
import com.second.walls.mitchell.walls.screen.Constants;

/**
 * Created by Mitchell on 2017-06-24.
 */
public abstract class Hazard implements GameHazard{
    public final static int ICON_RADIUS = Constants.PLAYER_WIDTH*4/7;
    public final static float SPACING = ICON_RADIUS * 2 + .3f * ICON_RADIUS;
    public final static float ICON_X= Constants.SCREEN_WIDTH - (Constants.PLAYER_WIDTH * 0.70f);

    protected Paint iconPaint = new Paint();
    protected int opacity = 100;
    protected float iconY;
    protected boolean enabled = false;

    protected WallManager wallManager;
    protected RectPlayer player;

    public void setIconY(float iconY) {
        this.iconY = iconY;
    }

    @Override
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            onTermination();
        }
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void onPlayerUpdate(RectPlayer player, double delta){
        player.regularUpdate(delta);
    }

    public void onWallUpdate(WallManager wallManager, double delta){wallManager.regularUpdate(delta);}

    public void onTermination(){}

    public void initPlayer(RectPlayer player){this.player = player;}

    public void initWalls(WallManager wallmanager){this.wallManager = wallmanager;}

    @Override
    public void drawIcon(Canvas canvas) {
        iconPaint.setAlpha(opacity);
        canvas.drawCircle(ICON_X, iconY, ICON_RADIUS, iconPaint);
    }
}
