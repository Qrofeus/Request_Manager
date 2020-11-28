package com.qrofeus.requestmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestQueue extends AppCompatActivity implements Dialog_RequestDetails.Interface_requestDetails {

    private Dialog_RequestDetails requestDetails;
    private ArrayList<RequestClass> displayQueue;
    private RequestAdapter adapter;
    private String priority;
    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_queue_layout);

        user = getIntent().getExtras().get("User").toString();
        EditText searchBar = findViewById(R.id.searchEdit);

        setUpList();
        setUpAdapter();

        String username = getIntent().getExtras().get("Username").toString();
        if (!username.isEmpty()) {
            searchBar.setClickable(false);
            searchBar.setVisibility(View.GONE);
            filterUsername(username);
        } else {
            searchBar.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    searchQuery(s.toString());
                }
            });
        }
    }

    private void setUpList() {
        // Temporary List
        ArrayList<RequestClass> requestQueue = new ArrayList<>();

        requestQueue.add(new RequestClass("012456", "User 1", "Subject 1", "Details 1", "email", "phone number"));
        requestQueue.add(new RequestClass("012407", "User 2", "Subject 2", "Details 2", "email", "phone number"));
        requestQueue.add(new RequestClass("012461", "User 3", "Subject 3", "Details 3", "email", "phone number"));
        requestQueue.add(new RequestClass("012472", "User 4", "Subject 4", "Details 4", "email", "phone number"));
        requestQueue.add(new RequestClass("012401", "User 1", "Subject 5", "Details 5", "email", "phone number"));
        requestQueue.add(new RequestClass("012484", "User 6", "Subject 6", "Details 6", "email", "phone number"));
        requestQueue.add(new RequestClass("012432", "User 7", "Subject 7", "Details 7", "email", "phone number"));
        requestQueue.add(new RequestClass("012451", "User 1", "Subject 8", "Details 8", "email", "phone number"));

        displayQueue = requestQueue;
    }

    private void filterUsername(String username) {
        ArrayList<RequestClass> filterList = new ArrayList<>();
        for (RequestClass request : displayQueue) {
            if (request.getUsername().toLowerCase().contains(username.toLowerCase())) {
                filterList.add(request);
            }
        }
        adapter.filter(filterList);
    }

    private void setUpAdapter() {
        // Set Up Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_request);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RequestAdapter(displayQueue);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // Open Dialog
                requestDetails = new Dialog_RequestDetails(displayQueue.get(position), user);
                requestDetails.show(getSupportFragmentManager(), "Request Details");
            }
        });
    }

    private void searchQuery(String text) {
        ArrayList<RequestClass> filterList = new ArrayList<>();
        for (RequestClass request : displayQueue) {
            if (request.getRequest_id().toLowerCase().contains(text.toLowerCase())) {
                filterList.add(request);
            }
        }
        adapter.filter(filterList);
    }

    @Override
    public void deleteRequest(String requestID) {
        // Remove request from list
        int pos;
        for (pos = 0; pos < displayQueue.size(); pos++) {
            if (displayQueue.get(pos).getRequest_id().equals(requestID)) {
                break;
            }
        }
        displayQueue.remove(pos);
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void completeRequest(String requestID) {
        // Remove request from list
        int pos;
        for (pos = 0; pos < displayQueue.size(); pos++) {
            if (displayQueue.get(pos).getRequest_id().equals(requestID)) {
                break;
            }
        }
        displayQueue.remove(pos);
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void closeDialog() {
        requestDetails.dismiss();
    }
}