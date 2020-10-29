package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AdminLogin extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        final EditText username = (EditText) findViewById(R.id.text_username);
        final EditText password = (EditText) findViewById(R.id.text_password);
        final TextView error_message = (TextView) findViewById(R.id.text_error_message);
        final CardView button_login = (CardView) findViewById(R.id.button_login);

        //ToDo Login User if present in database
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if(database_contains_(username.getText().toString())){
                    //get password for provided username
                    if(password.getText().toString().equals("User_Password_from_database")){
                        startActivity(new Intent(AdminLogin.this, AdminDashboard.class));
                        finish();
                    } else {
                        error_message.setVisibility(View.VISIBLE);
                        password.setText("");
                    }
                //} else {
                //      error_message.setVisibility(View.VISIBLE);
                //      password.setText("");
                //}
            }
        });

        //ToDo Add forgot_password functionality
    }
}