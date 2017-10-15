package com.second.walls.mitchell.walls.screen;

/**
 * Created by Mitchell on 2017-05-21.
 */
public class Constants {

    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;
    public static double START_GAME_SPEED = 1.3;
    public static double GAME_SPEED = START_GAME_SPEED;
    public static int VERTICAL_SPEED = 8;
    public static int PLAYER_WIDTH; // initialized in RectPlayer
    public static int CORNER_RADIUS;  // initialized in RectPlayer
    public final static int SCORE_PADDING = 10;
    public final static int INVISIBLE_POINT =  SCREEN_HEIGHT - (PLAYER_WIDTH*15);
}
