package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        //ToDo set Manage Button to visible and clickable after admin is logged in

        //For request list from admin
        //startActivity(new Intent(this, RequestList.class).putExtra("User", "Admin"));
    }

    public void logout(View view){
        //ToDo add pop up confirmation
        startActivity(new Intent(AdminDashboard.this, Dashboard.class));
        finish();
    }
}