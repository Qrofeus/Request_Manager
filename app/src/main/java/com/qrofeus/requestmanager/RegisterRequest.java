package com.qrofeus.requestmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterRequest extends AppCompatActivity {

    private String username_text;
    private String subject_text;
    private String email_address;
    private String phone_number;

    private Spinner priority;
    private EditText username;
    private EditText mail_id;
    private EditText phone;
    private EditText subject;
    private EditText details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_register_request);

        username = findViewById(R.id.username);
        mail_id = findViewById(R.id.email_address);
        phone = findViewById(R.id.contact_phone);
        subject = findViewById(R.id.request_subject);
        details = findViewById(R.id.request_details);
        priority = findViewById(R.id.priority_dropdown);

        if (!getIntent().getExtras().get("User").toString().isEmpty()) {
            username.setText(getIntent().getExtras().get("User").toString());
            mail_id.setText(getIntent().getExtras().get("Mail").toString());
            phone.setText(getIntent().getExtras().get("Phone").toString());
        }
    }

    public void onSubmit(View view) {
        // Validate Input
        email_address = mail_id.getText().toString();
        phone_number = phone.getText().toString();
        if (email_address.isEmpty() || phone_number.isEmpty()) {
            Toast.makeText(this, "Either email-address or phone-number is required", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!verifyInput(email_address, phone_number)) {
            Toast.makeText(RegisterRequest.this, "Invalid Format Email-Address/Phone-Number", Toast.LENGTH_SHORT).show();
            return;
        }

        username_text = username.getText().toString();
        subject_text = subject.getText().toString();
        if (username_text.isEmpty() || subject_text.isEmpty()) {
            Toast.makeText(RegisterRequest.this, "Username/Subject field is empty", Toast.LENGTH_SHORT).show();
            return;
        }

        storeData();
    }

    private void storeData() {
        String request_priority = priority.getSelectedItem().toString();
        // Create Database Reference
        DatabaseReference reference;
        switch (request_priority) {
            case "Low":
                reference = FirebaseDatabase.getInstance().getReference().child("Low");
                break;
            case "Medium":
                reference = FirebaseDatabase.getInstance().getReference().child("Medium");
                break;
            case "High":
                reference = FirebaseDatabase.getInstance().getReference().child("High");
                break;
            default:
                reference = FirebaseDatabase.getInstance().getReference();
        }

        String details_text = details.getText().toString();
        String ReqID = reference.push().getKey();
        RequestClass newRequest = new RequestClass(ReqID, username_text, subject_text, details_text, email_address, phone_number, request_priority);
        reference.child(ReqID).setValue(newRequest);

        Toast.makeText(this, "Request Registered", Toast.LENGTH_SHORT).show();
        finish();
    }

    private boolean verifyInput(String mail, String phone) {
        Pattern patternMail = Pattern.compile("[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$");
        Pattern patternPhone = Pattern.compile("[789][0-9]{9}");

        return patternMail.matcher(mail).matches() && patternPhone.matcher(phone).matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}