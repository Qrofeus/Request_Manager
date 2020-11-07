package com.qrofeus.requestmanager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.cardview.widget.CardView;

public class Dialog_RequestDetails extends AppCompatDialogFragment {

    private final RequestClass request;
    private final String user;

    public Dialog_RequestDetails(RequestClass request, String user) {
        this.request = request;
        this.user = user;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_request_details, null);

        Toast.makeText(getActivity(), "Dialog Opened", Toast.LENGTH_SHORT).show();

        final TextView reqID = view.findViewById(R.id.popup_id);
        final TextView reqSubject = view.findViewById(R.id.popup_subject);
        final TextView reqUsername = view.findViewById(R.id.popup_username);
        final TextView reqDetails = view.findViewById(R.id.popup_details);

        reqID.setText(request.getRequest_id());
        reqUsername.setText(request.getUsername());
        reqSubject.setText(request.getRequest_subject());
        reqDetails.setText(request.getRequest_details());

        if (user.equals("Admin")) {
            final CardView reqAdminCard = view.findViewById(R.id.adminCard);
            reqAdminCard.setVisibility(View.VISIBLE);
        }

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
