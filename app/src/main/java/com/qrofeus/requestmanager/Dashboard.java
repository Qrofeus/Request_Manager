package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class Dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void requestList(View view){
        startActivity(new Intent(this, RequestList.class).putExtra("User", "Customer"));
    }

    public void makeRequest(View view){
        startActivity(new Intent(this, RegisterRequest.class));
    }

    public void adminLogin(View view){
        startActivity(new Intent(this, AdminLogin.class));
    }
}