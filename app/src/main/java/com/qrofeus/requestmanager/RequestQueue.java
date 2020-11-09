package com.qrofeus.requestmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestQueue extends AppCompatActivity implements Dialog_RequestDetails.Interface_requestDetails {

    private Dialog_RequestDetails requestDetails;
    private ArrayList<RequestClass> requestQueue;
    private RequestAdapter adapter;
    private String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_queue_layout);

        final String user = getIntent().getExtras().getString("User");

        // Temporary List
        requestQueue = new ArrayList<>();

        requestQueue.add(new RequestClass("012456", "User 1", "Subject 1", "Details 1"));
        requestQueue.add(new RequestClass("012407", "User 2", "Subject 2", "Details 2"));
        requestQueue.add(new RequestClass("012461", "User 3", "Subject 3", "Details 3"));
        requestQueue.add(new RequestClass("012472", "User 4", "Subject 4", "Details 4"));
        requestQueue.add(new RequestClass("012401", "User 5", "Subject 5", "Details 5"));
        requestQueue.add(new RequestClass("012484", "User 6", "Subject 6", "Details 6"));
        requestQueue.add(new RequestClass("012432", "User 7", "Subject 7", "Details 7"));
        requestQueue.add(new RequestClass("012451", "User 8", "Subject 8", "Details 8"));

        // Set Up Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_request);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RequestAdapter(requestQueue);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //Set Up Spinner Object for priority
        final Spinner prioritySpin = findViewById(R.id.recycler_prioritySpin);
        final ArrayAdapter<CharSequence> priority_adapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item);
        priority_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpin.setAdapter(priority_adapter);

        prioritySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priority = prioritySpin.getItemAtPosition(position).toString();
                Toast.makeText(RequestQueue.this, "Showing List for priority " + priority, Toast.LENGTH_SHORT).show();

                // Change table to show proper priority
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing
            }
        });

        adapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // Open Dialog
                requestDetails = new Dialog_RequestDetails(requestQueue.get(position), user);
                requestDetails.show(getSupportFragmentManager(), "Request Details");
            }
        });
    }

    @Override
    public void deleteRequest(String requestID) {
        // Remove request from list
        int pos;
        for (pos = 0; pos < requestQueue.size(); pos++) {
            if (requestQueue.get(pos).getRequest_id().equals(requestID)) {
                break;
            }
        }
        requestQueue.remove(pos);
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void completeRequest(String requestID) {
        // Remove request from list
        int pos;
        for (pos = 0; pos < requestQueue.size(); pos++) {
            if (requestQueue.get(pos).getRequest_id().equals(requestID)) {
                break;
            }
        }
        requestQueue.remove(pos);
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void closeDialog() {
        requestDetails.dismiss();
    }
}