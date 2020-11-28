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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Account_Profile extends AppCompatActivity {

    private String username;
    private String password;
    private String mail;
    private String phone;
    private String type;

    private String dataKey;

    private EditText username_edit;
    private EditText password_edit;
    private EditText mail_edit;
    private EditText phone_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_profile);

        dataKey = getIntent().getExtras().getString("Database Key");

        // Get User details
        FirebaseDatabase.getInstance().getReference().child("Accounts").child(dataKey).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserAccount account = snapshot.getValue(UserAccount.class);
                username = account.getUsername();
                password = account.getPassword();
                mail = account.getMailID();
                phone = account.getPhone_number();
                type = account.getType();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        username_edit = findViewById(R.id.admin_user);
        password_edit = findViewById(R.id.admin_pass);
        mail_edit = findViewById(R.id.admin_mail);
        phone_edit = findViewById(R.id.admin_phone);

        username_edit.setText(username);
        password_edit.setText(password);
        mail_edit.setText(mail);
        phone_edit.setText(phone);
    }

    public void onUpdate(View view) {
        String newName = username_edit.getText().toString();
        String newPass = password_edit.getText().toString();
        String newMail = mail_edit.getText().toString();
        String newPhone = phone_edit.getText().toString();

        if (!newName.equals(username) || !newPass.equals(password) || !newMail.equals(mail) || !newPhone.equals(phone)) {
            if (verifyInput(newMail, newPhone)) {
                username = newName;
                password = newPass;
                mail = newMail;
                phone = newPhone;

                UserAccount account = new UserAccount(username, password, mail, phone, type);
                FirebaseDatabase.getInstance().getReference().child("Accounts").child(dataKey).setValue(account);

                Toast.makeText(this, "User profile updated", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Invalid Format for Mail/Phone", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No Changes Applied", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verifyInput(String mail, String phone) {
        Pattern patternMail = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
        Pattern patternPhone = Pattern.compile("[789][0-9]{9}");

        return patternMail.matcher(mail).matches() && patternPhone.matcher(phone).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (type.equals("Admin")) {
            startActivity(new Intent(this, Dashboard_Admin.class)
                    .putExtra("Username", username)
                    .putExtra("Database Key", dataKey));
            finish();
        } else if (type.equals("Customer")) {
            startActivity(new Intent(this, Dashboard_User.class)
                    .putExtra("Database Key", dataKey));
        }
    }
}