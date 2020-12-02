package com.qrofeus.requestmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoginUser extends AppCompatActivity {

    private EditText username;
    private EditText password;

    private ArrayList<UserAccount> userAccounts;

    private String use;
    private String username_text;
    private String password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);

        use = getIntent().getStringExtra("use");

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);

        getUsers();
    }

    private void getUsers() {
        userAccounts = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Accounts").child(use);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        UserAccount account = dataSnapshot.getValue(UserAccount.class);
                        userAccounts.add(account);
                    }
                } catch (Exception e) {
                    Toast.makeText(LoginUser.this, "Error occurred: " + e.toString(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginUser.this, "Database Error: " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onSubmitClick(View view) {
        username_text = username.getText().toString().trim();
        password_text = password.getText().toString().trim();

        if (username_text.isEmpty()) {
            Toast.makeText(this, "Username cannot be empty", Toast.LENGTH_SHORT).show();
            username.requestFocus();
            return;
        }

        if (password_text.isEmpty()) {
            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
            password.requestFocus();
            return;
        }

        checkUser();
    }

    private void checkUser() {
        for (UserAccount account : userAccounts) {
            if (username_text.equals(account.getUsername())) {
                checkPassword(account);
                return;
            }
        }
        Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show();
        username.requestFocus();
    }

    private void checkPassword(UserAccount account) {
        if (password_text.equals(account.getPassword())) {
            String dataKey = account.getUser_id();
            String email_address = account.getMailID();
            String phone_number = account.getPhone_number();

            // Start Activity based on usage
            if (use.equals("Admin")) {
                startActivity(new Intent(LoginUser.this, Dashboard_Admin.class)
                        .putExtra("Database Key", dataKey)
                        .putExtra("Username", username_text)
                        .putExtra("Password", password_text)
                        .putExtra("Mail ID", email_address)
                        .putExtra("Phone", phone_number));
                finish();
            } else if (use.equals("Customer")) {
                startActivity(new Intent(LoginUser.this, Dashboard_User.class)
                        .putExtra("Database Key", dataKey)
                        .putExtra("Username", username_text)
                        .putExtra("Password", password_text)
                        .putExtra("Mail ID", email_address)
                        .putExtra("Phone", phone_number));
                finish();
            }
        }
    }

    public void onRegisterUser(View view) {
        startActivity(new Intent(this, Register_Activity.class)
                .putExtra("use", "Customer"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Dashboard_Main.class));
        finish();
    }
}