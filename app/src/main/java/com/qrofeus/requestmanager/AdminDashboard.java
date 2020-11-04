package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AdminDashboard extends AppCompatActivity implements DialogClass.DialogResults, ProfilePopUp.ProfileInterface, UserEntry.UserEntryInterface {

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
        UserEntry entry = new UserEntry("Register Admin User", "Register");
        entry.show(getSupportFragmentManager(), "Register User");
    }

    public void profile(View view){
        ProfilePopUp popUp = new ProfilePopUp(username, password);
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
        onBackPressed();
    }

    @Override
    public void confirmDialog() {
        finish();
    }

    @Override
    public void updateDetails(String username, String password) {
        if (!this.username.equals(username) || !this.password.equals(password)){
            textUsername.setText(username);
            //Update User details in database
        } else {
            Toast.makeText(this, "No changes in user details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void deleteUser() {
        //Remove User from database
        Toast.makeText(AdminDashboard.this, "Admin User Removed", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void userDetails(String username, String password) {
        //Add User to database
        Toast.makeText(this, "New Admin User added", Toast.LENGTH_SHORT).show();
    }
}