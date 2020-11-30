package com.qrofeus.requestmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

public class Dialog_RequestDetails extends AppCompatDialogFragment {

    private final RequestClass request;
    private final String user;
    private Interface_requestDetails requestDetails;

    public Dialog_RequestDetails(RequestClass request, String user) {
        this.request = request;
        this.user = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.dialog_request_details, null);

        final TextView reqID = view.findViewById(R.id.popup_id);
        final TextView reqSubject = view.findViewById(R.id.popup_subject);
        final TextView reqUsername = view.findViewById(R.id.popup_username);
        final TextView reqDetails = view.findViewById(R.id.popup_details);

        reqID.setText(String.format("RID: %s", request.getRequest_id()));
        reqUsername.setText(String.format("Username: %s", request.getUsername()));
        reqSubject.setText(String.format("Subject: %s", request.getRequest_subject()));
        reqDetails.setText(String.format("Details: %s", request.getRequest_details()));

        if (user.equals("Admin")) {

            final Button dismiss = view.findViewById(R.id.popup_dismissButton);
            final Button complete = view.findViewById(R.id.popup_completeButton);
            final Button confirm = view.findViewById(R.id.popup_confirmButton);
            final Button cancel = view.findViewById(R.id.popup_cancelButton);

            final TextView phone = view.findViewById(R.id.popup_phone);
            final TextView mail = view.findViewById(R.id.popup_mail);

            final CardView reqAdminCard = view.findViewById(R.id.adminCard);
            reqAdminCard.setVisibility(View.VISIBLE);

            phone.setText(String.format("Phone: %s", request.getPhone()));
            mail.setText(String.format("Mail: %s", request.getEmail()));

            //Admin functionality
            dismiss.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss.setText(R.string.confirm_dismissal);
                    complete.setClickable(false);
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setClickable(true);
                    cancel.setVisibility(View.VISIBLE);
                    cancel.setClickable(true);
                }
            });

            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    complete.setText(R.string.confirm_completion);
                    dismiss.setClickable(false);
                    confirm.setVisibility(View.VISIBLE);
                    confirm.setClickable(true);
                    cancel.setVisibility(View.VISIBLE);
                    cancel.setClickable(true);
                }
            });

            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (complete.getText().toString().equals("Confirm Completion")) {
                        requestDetails.completeRequest(request.getRequest_id());
                    } else if (dismiss.getText().toString().equals("Confirm Dismissal")) {
                        requestDetails.deleteRequest(request.getRequest_id());
                    }
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestDetails.closeDialog();
                }
            });
        }

        //Set view to builder
        builder.setView(view);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            requestDetails = (Interface_requestDetails) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement Interface_requestDetails");
        }
    }

    public interface Interface_requestDetails {
        void deleteRequest(String requestID);

        void completeRequest(String requestID);

        void closeDialog();
    }
}
