package com.qrofeus.requestmanager;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Register_Activity extends AppCompatActivity {

    private String use;
    private String text_username;
    private String text_password;
    private String text_phone;
    private String text_mail;

    private DatabaseReference reference;

    private EditText username;
    private EditText password;
    private EditText phone;
    private EditText mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_account);

        use = getIntent().getExtras().get("use").toString();

        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_pass);
        phone = findViewById(R.id.register_phone);
        mail = findViewById(R.id.register_mail);
    }

    // This works
    public void onRegisterClick(View view) {

        text_username = username.getText().toString().trim();
        text_password = password.getText().toString().trim();
        text_phone = phone.getText().toString().trim();
        text_mail = mail.getText().toString().trim();

        if (text_username.isEmpty() || text_password.isEmpty() || text_mail.isEmpty() || text_phone.isEmpty()) {
            Toast.makeText(this, "Please fill all required data", Toast.LENGTH_SHORT).show();
            return;
        }
        checkDuplicate();
    }

    // This Works
    private void checkDuplicate() {
        reference = FirebaseDatabase.getInstance().getReference("Accounts").child(use);
        Query checkUsername = reference.orderByChild("username").equalTo(text_username);
        checkUsername.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Toast.makeText(Register_Activity.this, "Enter unique username", Toast.LENGTH_SHORT).show();
                    username.requestFocus();
                } else
                    createUser();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // This works
    private void createUser() {
        if (verifyInput(text_mail, text_phone)) {
            String user_key = reference.push().getKey();
            UserAccount account = new UserAccount(user_key, text_username, text_password, text_mail, text_phone);
            assert user_key != null;
            reference.child(user_key).setValue(account);
            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Invalid Email/Phone format", Toast.LENGTH_SHORT).show();
        }
    }

    // This works
    private boolean verifyInput(String mail, String phone) {
        Pattern patternMail = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
        Pattern patternPhone = Pattern.compile("[789][0-9]{9}");

        return patternMail.matcher(mail).matches() && patternPhone.matcher(phone).matches();
    }

}