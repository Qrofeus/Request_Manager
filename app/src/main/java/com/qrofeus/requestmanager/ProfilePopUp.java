package com.qrofeus.requestmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class ProfilePopUp extends AppCompatDialogFragment {

    private EditText username_text;
    private EditText password_text;
    private ProfileInterface profile;

    private final String username;
    private final String password;

    public ProfilePopUp(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.profile_user, null);

        builder.setView(view)
                .setTitle("Profile")
                .setNeutralButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String edit_username = username_text.getText().toString();
                        String edit_password = password_text.getText().toString();

                        profile.updateDetails(edit_username, edit_password);
                    }
                });

        username_text = view.findViewById(R.id.profile_username);
        username_text.setText(username);
        password_text = view.findViewById(R.id.profile_password);
        password_text.setText(password);
        final Button delete_button = view.findViewById(R.id.profile_delete);
        final Button confirm_delete = view.findViewById(R.id.profile_confirm);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_button.setClickable(false);
                confirm_delete.setEnabled(true);
                confirm_delete.setVisibility(View.VISIBLE);
            }
        });

        confirm_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profile.deleteUser();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            profile = (ProfileInterface) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Class must implement ProfileInterface");
        }
    }

    public interface ProfileInterface {
        void updateDetails(String username, String password);
        void deleteUser();
    }
}
