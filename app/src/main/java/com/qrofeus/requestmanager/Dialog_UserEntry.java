package com.qrofeus.requestmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_UserEntry extends AppCompatDialogFragment {

    private final String title_text;
    private final String button_text;
    private Interface_UserEntry entryInterface;

    public Dialog_UserEntry(String title_text, String button_text) {
        this.title_text = title_text;
        this.button_text = button_text;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_user_entry, null);

        final EditText username_view = view.findViewById(R.id.login_username);
        final EditText password_view = view.findViewById(R.id.login_password);
        final TextView title_view = view.findViewById(R.id.login_title);
        final Button button_login = view.findViewById(R.id.login_button);
        final Button button_confirm = view.findViewById(R.id.login_confirm);

        if (button_text.equals("Register")) {
            password_view.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }

        title_view.setText(title_text);
        button_login.setText(button_text);
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = username_view.getText().toString();
                final String password = password_view.getText().toString();

                if (button_text.equals("Register")) {
                    // Register User
                    button_login.setClickable(false);
                    button_login.setBackgroundColor(getResources().getColor(R.color.disabledButton));
                    button_confirm.setVisibility(View.VISIBLE);
                    button_confirm.setClickable(true);

                    button_confirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            entryInterface.userDetails(username, password);
                        }
                    });
                } else {
                    // Login
                    entryInterface.userDetails(username, password);
                }
            }
        });

        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            entryInterface = (Interface_UserEntry) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Class must implement Interface_UserEntry");
        }
    }

    public interface Interface_UserEntry {
        void userDetails(String username, String password);
    }
}
