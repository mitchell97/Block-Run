package com.second.walls.mitchell.walls.screen;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.second.walls.mitchell.walls.screen.GamePanel;

/**
 * Created by Mitchell on 2017-06-16.
 */
public class GameCanvas extends View {

    private GamePanel gamePanel;

    public GameCanvas(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        setWillNotDraw(false);
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        gamePanel.draw(canvas);
    }
}
