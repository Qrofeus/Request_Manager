package com.qrofeus.requestmanager;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestQueue extends AppCompatActivity {

    private Dialog_RequestDetails requestDetails;

    private ArrayList<RequestClass> requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_queue_layout);

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
        RequestAdapter adapter = new RequestAdapter(requestQueue);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // Open Dialog
                requestDetails = new Dialog_RequestDetails(requestQueue.get(position), "Customer");
                requestDetails.show(getSupportFragmentManager(), "Request Details");
            }
        });
    }
}