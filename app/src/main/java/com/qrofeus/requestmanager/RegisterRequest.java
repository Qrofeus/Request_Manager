package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterRequest extends AppCompatActivity {

    private String username_text;
    private String subject_text;
    private String details_text;
    private String request_priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_request);

        final EditText username = (EditText) findViewById(R.id.username);
        //final TextView req_id = (TextView) findViewById(R.id.requestID);
        final EditText subject = (EditText) findViewById(R.id.request_subject);
        final EditText details = (EditText) findViewById(R.id.request_details);
        final Spinner priority = (Spinner) findViewById(R.id.priority_dropdown);
        final Button register = (Button) findViewById(R.id.button_register);

        /*String request_id = generateID();
        req_id.setText(request_id);*/

        // Set up dropdown list

        /*ArrayAdapter<String> priority_adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.priorities));*/
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
                Toast.makeText(RegisterRequest.this, "Request Registered", Toast.LENGTH_SHORT).show();
                username_text = username.getText().toString();
                subject_text = subject.getText().toString();
                details_text = details.getText().toString();
            }
        });

        //storeData();
    }

    private String generateID(){
        //ToDo: Generate Request ID
        return "Request ID : "+"========";
    }

    private void storeData(){
        //ToDo: Store all request parameters in database
    }
}