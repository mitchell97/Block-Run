package com.second.walls.mitchell.walls.screen;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.MotionEvent;

import com.second.walls.mitchell.walls.R;
import com.second.walls.mitchell.walls.scenes.GameplayScene;
import com.second.walls.mitchell.walls.scenes.SceneManager;

/**
 * Created by Mitchell on 2017-06-04.
 */
public class GamePanel extends Activity {

   // private GPanel gPanel;

    private GameThread thread;
    private GameCanvas gameCanvas;
    private SceneManager sceneManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        gPanel = new GPanel(this);
//        setContentView(gPanel);

        setContentView(R.layout.game_panel);
        gameCanvas = (GameCanvas) findViewById(R.id.gameCanvas);
        gameCanvas.setGamePanel(this);

        sceneManager = new SceneManager();
        sceneManager.setActive(new GameplayScene(findViewById(R.id.leftSide), findViewById(R.id.rightSide)));

        thread = new GameThread(gameCanvas, this, getIntent().getIntExtra("fps", 60));
        thread.setRunning(true);
        thread.start();
    }

    public void update(double delta){
        sceneManager.handleUpdates(delta);
    }

    public void draw(Canvas canvas){
        sceneManager.handleDraws(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        sceneManager.handleTouches(event);
        return true;
    }

    @Override
    protected void onDestroy() {
        //TODO STOP THREAD SOMEHOW
        super.onDestroy();
        if (sceneManager.getCurrentScene() != null)
            sceneManager.getCurrentScene().terminate();
        thread.setRunning(false);
        try {
            thread.join();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
