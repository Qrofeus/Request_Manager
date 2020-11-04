package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity implements DialogClass.DialogResults, ProfilePopUp.ProfileInterface {

    private TextView textUsername;
    private String username;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        username = getIntent().getExtras().getString("Username");
        password = getIntent().getExtras().getString("Password");
        textUsername = findViewById(R.id.username_text);
        textUsername.setText(username);
    }

    public void registerAdmin(View view){
        startActivity(new Intent(AdminDashboard.this, RegisterAdmin.class));
    }

    public void profile(View view){
        ProfilePopUp popUp = new ProfilePopUp(username, password, this);
        popUp.show(getSupportFragmentManager(), "Profile");
    }

    public void requestQueue(View view){
        startActivity(new Intent(this, RequestList.class).putExtra("User", "Admin"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        DialogClass dialogClass = new DialogClass("Closing Admin Dashboard", "Confirm Logout");
        dialogClass.show(getSupportFragmentManager(), "Confirm Logout");
    }

    public void logout(View view){
        DialogClass dialogClass = new DialogClass("Closing Admin Dashboard", "Confirm Logout");
        dialogClass.show(getSupportFragmentManager(), "Confirm Logout");
    }

    @Override
    public void confirmDialog() {
        finish();
    }

    @Override
    public void updateDetails(String username, String password) {
        textUsername.setText(username);
        //Update User details in database
    }
}