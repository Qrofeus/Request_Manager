package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Dashboard_Admin extends AppCompatActivity implements Dialog_Confirmation.Interface_DialogResults, Dialog_AdminProfile.ProfileInterface, Dialog_UserEntry.Interface_UserEntry {

    private TextView textUsername;
    private String username;
    private String password;
    private Dialog_UserEntry entry;
    private Dialog_AdminProfile popUp;

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
        entry = new Dialog_UserEntry("Register Admin User", "Register");
        entry.show(getSupportFragmentManager(), "Register User");
    }

    public void profile(View view){
        popUp = new Dialog_AdminProfile(username, password);
        popUp.show(getSupportFragmentManager(), "Profile");
    }

    public void requestQueue(View view){
        startActivity(new Intent(this, RequestQueue.class).putExtra("User", "Admin"));
    }

    @Override
    public void onBackPressed() {
        Dialog_Confirmation dialogClass = new Dialog_Confirmation("Closing Admin Dashboard");
        dialogClass.show(getSupportFragmentManager(), "Confirm Logout");
    }

    public void logout(View view){
        onBackPressed();
    }

    @Override
    public void confirmDialog() {
        startActivity(new Intent(this, Dashboard_User.class));
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
        Toast.makeText(Dashboard_Admin.this, "Admin User Removed", Toast.LENGTH_SHORT).show();
        popUp.dismiss();
        confirmDialog();
    }

    @Override
    public void userDetails(String username, String password) {
        //Add User to database
        Toast.makeText(this, "New Admin User added", Toast.LENGTH_SHORT).show();
        entry.dismiss();
    }

    @Override
    public void sendMail(String mailSubject, String mailDetails) {
    }
}