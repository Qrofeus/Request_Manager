package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Declare usage terms
        final Animation topLoad = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        final TextView title = (TextView) findViewById(R.id.loadTitle);

        //Set Animation
        title.setAnimation(topLoad);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, FoldingCells.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}