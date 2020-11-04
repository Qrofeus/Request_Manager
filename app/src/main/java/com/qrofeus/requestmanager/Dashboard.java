package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new DecelerateInterpolator());
        fadeIn.setDuration(2000);

        final CardView titleCard = findViewById(R.id.title_card);
        titleCard.setAnimation(fadeIn);
    }

    public void requestList(View view) {
        startActivity(new Intent(this, RequestList.class).putExtra("User", "Customer"));
    }

    public void makeRequest(View view) {
        startActivity(new Intent(this, RegisterRequest.class));
    }

    public void adminLogin(View view) {
        startActivity(new Intent(this, AdminLogin.class).putExtra("Action", "AdminLogin"));
    }
}