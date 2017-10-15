package com.second.walls.mitchell.walls.screen;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.second.walls.mitchell.walls.R;

/**
 * Created by Mitchell on 2017-07-25.
 */
public class Run extends Activity {

    private GLSurfaceView glSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // requesting to turn the title OFF
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // making it full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Initiate the Open GL view and
        // create an instance with this activits
        glSurfaceView = new GLSurfaceView(this);

        // set our renderer to be the main renderer with
        // the current activity context
        glSurfaceView.setRenderer(new Renderer());
        setContentView(glSurfaceView);

    }

    /** Remember to resume the glSurface  */
    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }

    /** Also pause the glSurface  */
    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
    }

}
