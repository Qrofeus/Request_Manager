package com.qrofeus.requestmanager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
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
    private ArrayList<RequestClass> requestQueue = null;
    private DatabaseReference reference;

    private RequestAdapter adapter;
    private Spinner spinner;
    private EditText searchBar;

    private String user;
    private String priority;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_queue_layout);

        user = getIntent().getExtras().get("User").toString();
        reference = FirebaseDatabase.getInstance().getReference().child("Low");

        spinner = findViewById(R.id.recycler_prioritySpin);
        searchBar = findViewById(R.id.searchEdit);

        initialSetUp();
    }

    private void initialSetUp() {
        priority = "Low";
        reference = FirebaseDatabase.getInstance().getReference(priority);
        requestQueue = new ArrayList<>();

        Toast.makeText(this, "Please wait a few seconds...", Toast.LENGTH_SHORT).show();

        // Get initial list
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(RequestQueue.this, "No current requests", Toast.LENGTH_SHORT).show();
                }
                try {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        RequestClass requestClass = dataSnapshot.getValue(RequestClass.class);
                        if (requestClass != null) {
                            requestQueue.add(requestClass);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(RequestQueue.this, "Error occurred: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
                displayQueue = requestQueue;
                setUpAdapter();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RequestQueue.this, "Database Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
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
                if (searchList != null)
                    requestDetails = new Dialog_RequestDetails(searchList.get(position), user);
                else
                    requestDetails = new Dialog_RequestDetails(displayQueue.get(position), user);
                requestDetails.show(getSupportFragmentManager(), "Request Details");
            }
        });

        activatePriority();
        activateSearchBar();
        String username = getIntent().getExtras().get("Username").toString();
        if (!username.isEmpty()) {
            searchBar.setText("");
            searchBar.setClickable(false);
            searchBar.setVisibility(View.GONE);
            filterUsername(username);
        }
    }

    private void activatePriority() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getPriorityList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void getPriorityList() {
        requestQueue = new ArrayList<>();
        priority = spinner.getSelectedItem().toString();
        reference = FirebaseDatabase.getInstance().getReference(priority);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(RequestQueue.this, "No current requests", Toast.LENGTH_SHORT).show();
                    return;
                }
                RequestClass requestClass;
                try {
                    for (DataSnapshot snap : snapshot.getChildren()) {
                        requestClass = snap.getValue(RequestClass.class);
                        requestQueue.add(requestClass);
                    }
                } catch (Exception e) {
                    Toast.makeText(RequestQueue.this, "Error occurred: " + e.toString(), Toast.LENGTH_SHORT).show();
                }

                displayQueue = requestQueue;
                adapter.updateList(displayQueue);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(RequestQueue.this, "Database Error: " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void activateSearchBar() {
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

    // This works
    private void searchQuery(String text) {
        searchList = new ArrayList<>();
        for (RequestClass request : displayQueue) {
            if (request.getRequest_id().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(request);
            }
        }
        adapter.updateList(searchList);
    }

    // This works
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

    @Override
    // This works
    public void deleteRequest(String requestID) {
        // Remove request from list
        int pos;
        for (pos = 0; pos < displayQueue.size(); pos++) {
            if (displayQueue.get(pos).getRequest_id().equals(requestID)) {
                break;
            }
        }
        reference.child(requestID).removeValue();
        displayQueue.remove(pos);
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    // This works
    public void completeRequest(String requestID) {
        // Remove request from list
        int pos;
        for (pos = 0; pos < displayQueue.size(); pos++) {
            if (displayQueue.get(pos).getRequest_id().equals(requestID)) {
                break;
            }
        }
        reference.child(requestID).removeValue();
        displayQueue.remove(pos);
        requestDetails.dismiss();
        adapter.notifyItemRemoved(pos);
    }

    @Override
    // This works
    public void closeDialog() {
        requestDetails.dismiss();
    }
}