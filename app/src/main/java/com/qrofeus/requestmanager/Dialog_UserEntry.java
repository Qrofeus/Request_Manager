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
        final View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_user_entry, null);

        final EditText editText1 = view.findViewById(R.id.login_edit1);
        final EditText editText2 = view.findViewById(R.id.login_edit2);
        final TextView title_view = view.findViewById(R.id.login_title);
        final Button posButton = view.findViewById(R.id.login_button);
        final Button button_confirm = view.findViewById(R.id.login_confirm);

        switch (button_text) {
            case "Register":
                editText2.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                editText2.setMaxLines(1);
                break;
            case "Send":
                final TextView text1 = view.findViewById(R.id.text1);
                text1.setText("Mail Subject:");
                final TextView text2 = view.findViewById(R.id.text2);
                text2.setText("Mail Body:");
                editText2.setLines(5);
                editText2.setMaxLines(7);

                editText1.setHint("Subject");
                editText2.setHint("Body");
                break;
            case "Login":
                title_view.setText(title_text);
                editText2.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                editText2.setMaxLines(1);
                break;
        }
        posButton.setText(button_text);
        posButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userSubject = editText1.getText().toString();
                final String passDetails = editText2.getText().toString();

                switch (button_text) {
                    case "Register":
                        // Register User
                        posButton.setClickable(false);
                        posButton.setBackgroundColor(getResources().getColor(R.color.disabledButton));
                        button_confirm.setVisibility(View.VISIBLE);
                        button_confirm.setClickable(true);

                        button_confirm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                entryInterface.userDetails(userSubject, passDetails);
                            }
                        });
                        break;
                    case "Send":
                        //Contact Us
                        entryInterface.sendMail(userSubject, passDetails);
                        break;
                    case "Login":
                        // Login
                        entryInterface.userDetails(userSubject, passDetails);
                        break;
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

        void sendMail(String mailSubject, String mailDetails);
    }
}
