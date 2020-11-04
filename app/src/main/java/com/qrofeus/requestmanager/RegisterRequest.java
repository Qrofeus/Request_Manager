package com.qrofeus.requestmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterRequest extends AppCompatActivity {

    private String username_text;
    private String subject_text;
    private String details_text;
    private String request_priority;
    private String email_address;
    private String phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_request);

        final EditText username = findViewById(R.id.username);
        final EditText mail_id = findViewById(R.id.email_address);
        final EditText phone = findViewById(R.id.contact_phone);
        //final TextView req_id = findViewById(R.id.requestID);
        final EditText subject = findViewById(R.id.request_subject);
        final EditText details = findViewById(R.id.request_details);
        final Spinner priority = findViewById(R.id.priority_dropdown);
        final Button register = findViewById(R.id.button_register);

        /*String request_id = generateID();
        req_id.setText(request_id);*/

        // Set up dropdown list
        ArrayAdapter<CharSequence> priority_adapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item);
        priority_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        priority.setAdapter(priority_adapter);

        priority.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request_priority = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_text = username.getText().toString();
                email_address = mail_id.getText().toString();
                phone_number = phone.getText().toString();
                subject_text = subject.getText().toString();
                details_text = details.getText().toString();
                if (username_text.isEmpty() || email_address.isEmpty() || phone_number.isEmpty() || subject_text.isEmpty() || details_text.isEmpty()){
                    Toast.makeText(RegisterRequest.this, "Please fill all the boxes", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterRequest.this, "Request Registered", Toast.LENGTH_SHORT).show();
                    //storeData();
                    finish();
                }
            }
        });
    }

    private String generateID() {
        //ToDo: Generate Request ID
        return "Request ID : " + "========";
    }

    private void storeData() {
        //ToDo: Store all request parameters in database
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}