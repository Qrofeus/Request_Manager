package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity implements UserEntry.UserEntryInterface {

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
        UserEntry entry = new UserEntry("Login as Admin User", "Login");
        entry.show(getSupportFragmentManager(), "Login User");
    }

    @Override
    public void userDetails(String username, String password) {
        //if(database_contains_(username.getText().toString())){
        //get password for provided username
        if (password.equals("QrofeusPass")) {
            startActivity(new Intent(this, AdminDashboard.class)
                    .putExtra("Username", username)
                    .putExtra("Password", password));
            finish();
        } else {
            Toast.makeText(this, "Invalid Username / Password", Toast.LENGTH_SHORT).show();
        }
    }
}