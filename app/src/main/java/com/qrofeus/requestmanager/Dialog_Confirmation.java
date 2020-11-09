package com.qrofeus.requestmanager;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Dialog_Confirmation extends AppCompatDialogFragment {

    private String message;
    private final String title;
    private Interface_DialogResults results;

    public Dialog_Confirmation(String title) {
        this.title = title;
    }

    public Dialog_Confirmation(String title, String message) {
        this.message = message;
        this.title = title;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        results.confirmDialog();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        if (message != null) {
            builder.setMessage(message);
        }
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            results = (Interface_DialogResults) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " Class must implement Interface_DialogResults");
        }
    }

    public interface Interface_DialogResults {
        void confirmDialog();
    }
}
