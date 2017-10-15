package com.second.walls.mitchell.walls.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

/**
 * Created by Mitchell on 2017-06-26.
 */
public interface Scene {
    void update(double delta);
    void draw(Canvas canvas);
    void terminate();
    boolean receiveTouchEvent(MotionEvent event);
}
