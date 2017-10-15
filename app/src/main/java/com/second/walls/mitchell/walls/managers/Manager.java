package com.second.walls.mitchell.walls.managers;

import android.graphics.Canvas;

/**
 * Created by Mitchell on 2017-06-24.
 */
public interface Manager {
    public void handleUpdates(double delta);
    public void handleDraws(Canvas canvas);
}
