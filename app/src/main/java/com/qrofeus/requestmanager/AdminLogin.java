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

        final String action = getIntent().getExtras().getString("Action");
        final TextView titleLogin = findViewById(R.id.login_page_title);
        final EditText username = findViewById(R.id.text_username);
        final CardView button_submit = findViewById(R.id.button_submit);
        final TextView submitCard = findViewById(R.id.text_submit);

        if (action.equals("AdminLogin")){
            titleLogin.setText(R.string.admin_login);
            submitCard.setText(R.string.login);

            final EditText password = findViewById(R.id.text_password);
            final TextView error_message = findViewById(R.id.text_error_message);

            //ToDo Login User if present in database
            button_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //if(database_contains_(username.getText().toString())){
                    //get password for provided username
                    if(password.getText().toString().equals("QrofeusPass")){
                        startActivity(new Intent(AdminLogin.this, AdminDashboard.class).putExtra("Username", username.getText().toString()));
                        finish();
                    } else {
                        error_message.setVisibility(View.VISIBLE);
                        password.setText("");
                    }
                }
            });
        }

        if (action.equals("DeleteAccount")){
            final String username_loggedIn = getIntent().getExtras().getString("Username");
            username.setText(username_loggedIn);
            titleLogin.setText("Deleting Account");
            submitCard.setText("Delete Account");

            final EditText password = findViewById(R.id.text_password);
            final TextView error_message = findViewById(R.id.text_error_message);

            button_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //get password for provided username
                    if(password.getText().toString().equals("QrofeusPass")){
                        //ToDo add pop up for confirmation for account deletion
                        startActivity(new Intent(AdminLogin.this, Dashboard.class));
                        finish();
                    } else {
                        error_message.setVisibility(View.VISIBLE);
                        password.setText("");
                    }
                }
            });
        }

        //ToDo Add forgot_password functionality
    }
}