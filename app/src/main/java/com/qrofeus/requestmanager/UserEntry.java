package com.qrofeus.requestmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class UserEntry extends AppCompatDialogFragment {

    private final String title;
    private final String button_text;
    private UserEntryInterface entryInterface;

    public UserEntry(String title, String button_text) {
        this.title = title;
        this.button_text = button_text;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_user_entry, null);

        final EditText username_text = view.findViewById(R.id.login_username);
        final EditText password_text = view.findViewById(R.id.login_password);

        if (button_text.equals("Login")){
            password_text.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        }

        builder.setView(view)
                .setTitle(title)
                .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(button_text, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String username = username_text.getText().toString();
                        String password = password_text.getText().toString();

                        entryInterface.userDetails(username, password);
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            entryInterface = (UserEntryInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Class must implement UserEntryInterface");
        }
    }

    public interface UserEntryInterface{
        void userDetails(String username, String password);
    }
}
