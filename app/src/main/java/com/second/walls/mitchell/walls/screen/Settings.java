package com.second.walls.mitchell.walls.screen;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;

import com.second.walls.mitchell.walls.R;

public class Settings extends Activity {

    private int FPS;;

    private RadioButton fps30rb;
    private RadioButton fps60rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        fps30rb = (RadioButton) findViewById(R.id.fps30rb);
        fps60rb = (RadioButton) findViewById(R.id.fps60rb);

        switch (getIntent().getIntExtra("currentFPS", 60)){
            case 30:
                fps30rb.setChecked(true);
                FPS = 30;
                break;
            case 60:
                fps60rb.setChecked(true);
                FPS = 60;
                break;
            default:
                fps60rb.setChecked(true);
                FPS = 60;
        }

        fps30rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fps30rb.setChecked(true);
                fps60rb.setChecked(false);
                FPS = 30;
                Intent intent = new Intent();
                intent.putExtra("fps", FPS);
                setResult(RESULT_OK, intent);
                Log.d("Called", "Destroy: " + FPS);
                finish();
            }
        });

        fps60rb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fps30rb.setChecked(false);
                fps60rb.setChecked(true);
                FPS = 60;
                Intent intent = new Intent();
                intent.putExtra("fps", FPS);
                setResult(RESULT_OK, intent);
                Log.d("Called", "Destroy: " + FPS);
                finish();
            }
        });

    }
}
