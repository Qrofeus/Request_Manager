package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterAdmin extends AppCompatActivity implements DialogClass.DialogResults {

    private String username_text;
    private String password_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_admin);

        final EditText username = findViewById(R.id.register_username);
        final EditText password = findViewById(R.id.register_password);
        final Button register = findViewById(R.id.register_button);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username_text = username.getText().toString();
                password_text = password.getText().toString();

                openDialog(username_text, password_text);
            }
        });
    }

    private void openDialog(String username_text, String password_text){
        String text = "Username: "+username_text+"\nPassword: "+password_text;
        DialogClass dialogClass = new DialogClass("Register User", text);
        dialogClass.show(getSupportFragmentManager(), "Confirm Admin Registration");
    }

    @Override
    public void confirmDialog() {
        //ToDo Add admin to database
        Toast.makeText(RegisterAdmin.this, "Admin User added", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}