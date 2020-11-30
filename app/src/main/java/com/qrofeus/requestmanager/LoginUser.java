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
    private String dataKey;

    private UserAccount curAccount;

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
        dataKey = "";
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount account;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    account = (UserAccount) snap.getValue(UserAccount.class);
                    assert account != null;
                    if (account.getUsername().equals(username_text)) {
                        dataKey = snap.getKey();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LoginUser.this, "Database Read Failed", Toast.LENGTH_SHORT).show();
            }
        });

        if (dataKey.equals("")) {
            Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
            return;
        }

        curAccount = new UserAccount();
        reference.child(dataKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    curAccount = (UserAccount) snapshot.getValue(UserAccount.class);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Check User Type
        if (!(use.equals("Admin") && curAccount.getType().equals("Admin")) || !(use.equals("Customer")
                && curAccount.getType().equals("Customer"))) {
            Toast.makeText(this, "No user found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check password
        if (!password_text.equals(curAccount.getPassword())) {
            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Start Activity based on usage
        if (use.equals("Admin")) {
            startActivity(new Intent(this, Dashboard_Admin.class)
                    .putExtra("Database Key", dataKey)
                    .putExtra("Username", curAccount.getUsername()));
            finish();
        }

        if (use.equals("Customer")) {
            startActivity(new Intent(this, Dashboard_User.class)
                    .putExtra("Database Key", dataKey));
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