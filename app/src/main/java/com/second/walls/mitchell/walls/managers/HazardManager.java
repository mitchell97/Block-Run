package com.second.walls.mitchell.walls.managers;

import android.graphics.Canvas;
import android.util.Log;

import com.second.walls.mitchell.walls.hazards.FakeWallHazard;
import com.second.walls.mitchell.walls.hazards.GameHazard;
import com.second.walls.mitchell.walls.hazards.Hazard;
import com.second.walls.mitchell.walls.hazards.HazardListener;
import com.second.walls.mitchell.walls.hazards.IceHazard;
import com.second.walls.mitchell.walls.hazards.InvisibleWallsHazard;
import com.second.walls.mitchell.walls.hazards.NonStopHazard;
import com.second.walls.mitchell.walls.hazards.SmallWallsHazard;
import com.second.walls.mitchell.walls.screen.Constants;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mitchell on 2017-06-24.
 */
public class HazardManager {

    private ArrayList<Hazard> hazards;
    private ArrayList<HazardListener> listeners;
    private int numberOfWallsBetween = 5;
    private int currentHazardIndex;

    private Random random;
    private boolean activeHazard;

    public HazardManager (){
        hazards = new ArrayList<>();
        activeHazard = false;
        currentHazardIndex = 0;
        hazards.add(new IceHazard());
        hazards.add(new InvisibleWallsHazard());
        hazards.add(new SmallWallsHazard());
        hazards.add(new NonStopHazard());
        hazards.add(new FakeWallHazard());

        listeners = new ArrayList<>();
        random = new Random();
    }

    public void handleUpdates(double delta, int wallsPassed){
        if (wallsPassed !=0 && wallsPassed % (2 * numberOfWallsBetween) == 0){
            hazards.get(currentHazardIndex).setEnabled(false);
            for (HazardListener listener : listeners)
                listener.notify(null);
            activeHazard = false;
        }else {
            if (wallsPassed != 0 && wallsPassed % numberOfWallsBetween == 0 && !activeHazard) {
                currentHazardIndex = Math.abs(random.nextInt()) % hazards.size();

                hazards.get(currentHazardIndex).setEnabled(true);
                for (HazardListener listener : listeners)
                    listener.notify(hazards.get(currentHazardIndex));
                activeHazard = true;
            }
        }
        float iconY;
        int totalSpace = 0;

        for (int i = 0; i < hazards.size(); i++){
            totalSpace += Hazard.SPACING;
        }
        iconY = (Constants.SCREEN_HEIGHT - totalSpace) / 2;

        for (Hazard hazard : hazards){
            hazard.setIconY(iconY);
            iconY += Hazard.SPACING;
        }
    }

    public void handleDraws(Canvas canvas){
        for (GameHazard hazard : hazards){
            //hazard.drawIcon(canvas); Temp disable drawing hazard icons
            if (hazard.isEnabled()){
                hazard.draw(canvas);
            }
        }
    }

    public void addListener(HazardListener listener){
        listeners.add(listener);
    }
}
