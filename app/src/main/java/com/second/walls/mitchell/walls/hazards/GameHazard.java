package com.second.walls.mitchell.walls.hazards;

import android.graphics.Canvas;

import com.second.walls.mitchell.walls.gameobjects.GameObject;

/**
 * Created by Mitchell on 2017-06-23.
 */
public interface GameHazard extends GameObject {
    void setEnabled(boolean enabled);
    boolean isEnabled();
    void drawIcon(Canvas canvas);
    void setIconY(float iconY);
}
