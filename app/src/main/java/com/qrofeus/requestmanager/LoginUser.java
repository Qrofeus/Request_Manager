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

public class LoginUser extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private String use;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login_activity);

        use = getIntent().getExtras().get("use").toString();

        username = findViewById(R.id.login_username);
        password = findViewById(R.id.login_password);
    }

    public void onSubmit(View view) {
        final String username_text = username.getText().toString();
        String password_text = password.getText().toString();

        if (username_text.isEmpty() || password_text.isEmpty()) {
            Toast.makeText(this, "Empty Username/Password", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Accounts");

        // Check if username exists
        final String[] dataKey = {""};
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    UserAccount account = snap.getValue(UserAccount.class);
                    if (account.getUsername().equals(username_text)) {
                        dataKey[0] = snap.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginUser.this, "Database Read Failed", Toast.LENGTH_SHORT).show();
            }
        });

        if (dataKey[0].equals("")) {
            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        final UserAccount[] account = new UserAccount[1];
        reference.child(dataKey[0]).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                account[0] = snapshot.getValue(UserAccount.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Check User Type
        if (!(use.equals("Admin") && account[0].getType().equals("Admin")) || !(use.equals("Customer") && account[0].getType().equals("Customer"))) {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password
        if (!password_text.equals(account[0].getPassword())) {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Start Activity based on usage
        if (use.equals("Admin")) {
            startActivity(new Intent(this, Dashboard_Admin.class)
                    .putExtra("Database Key", dataKey[0])
                    .putExtra("Username", account[0].getUsername()));
            finish();
        }

        if (use.equals("Customer")) {
            startActivity(new Intent(this, Dashboard_User.class)
                    .putExtra("Database Key", dataKey[0]));
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, Dashboard_Main.class));
        finish();
    }
}