package com.second.walls.mitchell.walls.gameobjects;

import android.graphics.Canvas;

/**
 * Created by Mitchell on 2017-05-09.
 */
public interface GameObject {
    public void draw (Canvas canvas);
    public void update(double delta);
}
