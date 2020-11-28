package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard_Admin extends AppCompatActivity implements Dialog_Confirmation.Interface_DialogResults {

    private String dataKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_admin);

        dataKey = getIntent().getExtras().getString("Database Key");
        String username = getIntent().getExtras().getString("Username");

        TextView textUsername = findViewById(R.id.username_text);
        textUsername.setText(username);
    }

    public void registerAdmin(View view) {
        startActivity(new Intent(this, Register_Activity.class)
                .putExtra("use", "Admin"));
    }

    public void profile(View view) {
        startActivity(new Intent(this, Account_Profile.class)
                .putExtra("Database Key", dataKey));
        finish();
    }

    public void requestQueue(View view) {
        startActivity(new Intent(this, RequestQueue.class)
                .putExtra("User", "Admin")
                .putExtra("Username", ""));
    }

    @Override
    public void onBackPressed() {
        Dialog_Confirmation dialogClass = new Dialog_Confirmation("Confirm Logout");
        dialogClass.show(getSupportFragmentManager(), "Confirm Logout");
    }

    public void logout(View view) {
        onBackPressed();
    }

    @Override
    public void confirmDialog() {
        startActivity(new Intent(this, Dashboard_Main.class));
        finish();
    }
}