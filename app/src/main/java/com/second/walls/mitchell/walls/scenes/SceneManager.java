package com.second.walls.mitchell.walls.scenes;

import android.graphics.Canvas;
import android.view.MotionEvent;

import com.second.walls.mitchell.walls.managers.Manager;

import java.util.ArrayList;

/**
 * Created by Mitchell on 2017-06-26.
 */
public class SceneManager implements Manager {

    private Scene currentScene;

    public void setActive(Scene s){
        if (currentScene != null)
            currentScene.terminate();
        currentScene = s;
    }

    public Scene getCurrentScene() {
        return currentScene;
    }

    @Override
    public void handleUpdates(double delta) {
        if (currentScene != null)
            currentScene.update(delta);
    }

    @Override
    public void handleDraws(Canvas canvas) {
        if (currentScene != null)
            currentScene.draw(canvas);
    }

    public boolean handleTouches(MotionEvent event){
        if (currentScene != null)
            currentScene.receiveTouchEvent(event);
        return true;
    }
}
