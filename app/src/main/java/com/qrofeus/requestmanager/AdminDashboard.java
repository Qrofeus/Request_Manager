package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class AdminDashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        final String username = getIntent().getExtras().getString("Username");
        final TextView textUsername = findViewById(R.id.username_text);
        textUsername.setText(username);
    }

    public void registerAdmin(View view){
        //Start Activity register User
    }

    public void profile(View view){
        //Start Activity for Admin profile
    }

    public void requestQueue(View view){
        startActivity(new Intent(this, RequestList.class).putExtra("User", "Admin"));
    }

    public void logout(View view){
        //ToDo add pop up confirmation
        finish();
    }
}