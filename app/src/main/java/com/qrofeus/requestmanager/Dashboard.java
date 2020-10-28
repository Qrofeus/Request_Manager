package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        final TextView textView = (TextView) findViewById(R.id.dashboardTitle);
        textView.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_animation));
    }

    public void requestList(View view){
        startActivity(new Intent(this, RequestList.class));
    }

    public void makeRequest(View view){
        startActivity(new Intent(this, RegisterRequest.class));
    }
}