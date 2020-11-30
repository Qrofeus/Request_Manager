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
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class Register_Activity extends AppCompatActivity {

    private String use;
    private String text_username;

    private boolean exists = false;

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

    public void registerAccount(View view) {

        text_username = username.getText().toString();
        String text_password = password.getText().toString();
        String text_phone = phone.getText().toString();
        String text_mail = mail.getText().toString();

        if (text_username.isEmpty() || text_password.isEmpty() || text_mail.isEmpty() || text_phone.isEmpty()) {
            Toast.makeText(this, "Please fill all required data", Toast.LENGTH_SHORT).show();
            return;
        }

        checkDuplicate();
        if (exists) {
            Toast.makeText(this, "Enter unique username", Toast.LENGTH_SHORT).show();
            return;
        }

        if (verifyInput(text_mail, text_phone)) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Accounts");
            String user_key = reference.push().getKey();
            UserAccount account = new UserAccount(user_key, text_username, text_password, text_mail, text_phone, use);
            assert user_key != null;
            reference.child(user_key).setValue(account);
            Toast.makeText(this, "Account Created", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Invalid Email/Phone format", Toast.LENGTH_SHORT).show();
        }
    }

    private void checkDuplicate() {
        FirebaseDatabase.getInstance().getReference().child("Account").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    UserAccount account = snap.getValue(UserAccount.class);
                    assert account != null;
                    if (text_username.equals(account.getUsername()))
                        exists = true;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private boolean verifyInput(String mail, String phone) {
        Pattern patternMail = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
        Pattern patternPhone = Pattern.compile("[789][0-9]{9}");

        return patternMail.matcher(mail).matches() && patternPhone.matcher(phone).matches();
    }

}