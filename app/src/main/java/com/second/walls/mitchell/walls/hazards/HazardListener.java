package com.second.walls.mitchell.walls.hazards;

/**
 * Created by Mitchell on 2017-07-05.
 */
public interface HazardListener {
    void notify(Hazard hazard);
    void regularUpdate(double delta);
}
