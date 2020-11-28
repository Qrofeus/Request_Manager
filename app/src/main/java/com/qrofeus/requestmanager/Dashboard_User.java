package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Dashboard_User extends AppCompatActivity implements Dialog_Confirmation.Interface_DialogResults {

    private String username;
    private String mail;
    private String phone;
    private String dataKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_user);

        dataKey = getIntent().getExtras().getString("Database Key");

        // Get User details
        FirebaseDatabase.getInstance().getReference().child("Accounts").child(dataKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount account = snapshot.getValue(UserAccount.class);
                username = account.getUsername();
                mail = account.getMailID();
                phone = account.getPhone_number();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Set page title
        TextView title = findViewById(R.id.user_title);
        title.setText(username);
    }

    public void displayQueue(View view) {
        startActivity(new Intent(this, RequestQueue.class)
                .putExtra("User", "Customer")
                .putExtra("Username", username));
    }

    public void userProfile(View view) {
        startActivity(new Intent(this, Account_Profile.class)
                .putExtra("Database Key", dataKey));
        finish();
    }

    public void makeRequest(View view) {
        startActivity(new Intent(this, RegisterRequest.class)
                .putExtra("User", username)
                .putExtra("Mail", mail)
                .putExtra("Phone", phone));
    }

    public void prevRequests(View view) {
        startActivity(new Intent(this, RequestQueue.class)
                .putExtra("User", "Customer")
                .putExtra("Username", "user 1"));
    }

    @Override
    public void onBackPressed() {
        Dialog_Confirmation dialogClass = new Dialog_Confirmation("Confirm Logout");
        dialogClass.show(getSupportFragmentManager(), "Confirm Logout");
    }

    @Override
    public void confirmDialog() {
        startActivity(new Intent(this, Dashboard_Main.class));
        finish();
    }
}