package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Dashboard_User extends AppCompatActivity implements Dialog_UserEntry.Interface_UserEntry {

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
        startActivity(new Intent(this, RequestQueue.class).putExtra("User", "Customer"));
    }

    public void makeRequest(View view) {
        startActivity(new Intent(this, RegisterRequest.class));
    }

    public void adminLogin(View view) {
        Dialog_UserEntry entry = new Dialog_UserEntry("Login as Admin User", "Login");
        entry.show(getSupportFragmentManager(), "Login User");
    }

    public void contactUs(View view) {
        Dialog_UserEntry mail = new Dialog_UserEntry("Contact Us", "Send");
        mail.show(getSupportFragmentManager(), "Contact Us");
    }

    @Override
    public void userDetails(String username, String password) {
        //if(database_contains_(username.getText().toString())){
        //get password for provided username
        if (password.equals("password")) {
            startActivity(new Intent(this, Dashboard_Admin.class)
                    .putExtra("Username", username)
                    .putExtra("Password", password));
            finish();
        } else {
            Toast.makeText(this, "Invalid Username / Password", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void sendMail(String mailSubject, String mailDetails) {
        // Send Mail to admin
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"pday3683@gmail.com"});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, mailSubject);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, mailDetails);
        startActivity(Intent.createChooser(emailIntent, "Complete action using..."));
    }
}