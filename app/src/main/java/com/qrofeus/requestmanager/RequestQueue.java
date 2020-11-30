package com.qrofeus.requestmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RequestQueue extends AppCompatActivity implements Dialog_RequestDetails.Interface_requestDetails {

    private Dialog_RequestDetails requestDetails;
    private ArrayList<RequestClass> displayQueue;
    private ArrayList<RequestClass> searchList = null;
    private DatabaseReference reference;

    private RequestAdapter adapter;
    private Spinner spinner;
    private EditText searchBar;

    private String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_queue_layout);

        user = getIntent().getExtras().get("User").toString();
        reference = FirebaseDatabase.getInstance().getReference().child("Low");

        spinner = findViewById(R.id.recycler_prioritySpin);
        searchBar = findViewById(R.id.searchEdit);

        setUpList();
        setUpAdapter();

        String username = getIntent().getExtras().get("Username").toString();
        if (!username.isEmpty()) {
            searchBar.setText("");
            searchBar.setClickable(false);
            searchBar.setVisibility(View.GONE);
            filterUsername(username);
        }
    }

    public void onSearch(View view) {
        searchList = null;
        String priority = spinner.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference().child(priority);
        setUpList();
        adapter.updateList(displayQueue);

        if (!searchBar.getText().toString().equals("")) {
            searchQuery(searchBar.getText().toString());
        }
    }

    private void setUpList() {
        final ArrayList<RequestClass> requestQueue = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                RequestClass requestClass;
                for (DataSnapshot snap : snapshot.getChildren()) {
                    requestClass = (RequestClass) snap.getValue(RequestClass.class);
                    requestQueue.add(requestClass);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        displayQueue = requestQueue;
    }

    private void filterUsername(String username) {
        ArrayList<RequestClass> userList = new ArrayList<>();
        for (RequestClass request : displayQueue) {
            if (request.getUsername().toLowerCase().contains(username.toLowerCase())) {
                userList.add(request);
            }
        }
        displayQueue = userList;
        adapter.updateList(displayQueue);
    }

    private void setUpAdapter() {
        // Set Up Recycler View
        RecyclerView recyclerView = findViewById(R.id.recycler_request);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        adapter = new RequestAdapter(displayQueue);
        Toast.makeText(this, "Adapter Created", Toast.LENGTH_SHORT).show();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "Added to Recycler", Toast.LENGTH_SHORT).show();

        adapter.setOnItemClickListener(new RequestAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                // Open Dialog
                if (searchList == null)
                    requestDetails = new Dialog_RequestDetails(displayQueue.get(position), user);
                else
                    requestDetails = new Dialog_RequestDetails(searchList.get(position), user);
                requestDetails.show(getSupportFragmentManager(), "Request Details");
            }
        });
    }

    private void searchQuery(String text) {
        searchList = new ArrayList<>();
        for (RequestClass request : displayQueue) {
            if (request.getRequest_id().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(request);
            }
        }
        adapter.updateList(searchList);
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
        reference.child(requestID).removeValue();
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
        reference.child(requestID).removeValue();
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    public void closeDialog() {
        requestDetails.dismiss();
    }
}