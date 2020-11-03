package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class RequestList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        final Spinner prioritySpin = findViewById(R.id.priority_spin);
        final RecyclerView recyclerView = findViewById(R.id.request_recycler);
        final String user = getIntent().getExtras().getString("User");
        final AdapterClass adapterClass = new AdapterClass(this, user);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);

        recyclerView.setAdapter(adapterClass);

        ArrayAdapter<CharSequence> priority_adapter = ArrayAdapter.createFromResource(this, R.array.priorities, android.R.layout.simple_spinner_item);
        priority_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prioritySpin.setAdapter(priority_adapter);

        prioritySpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(RequestList.this, "Showing list for priority "+ parent.getItemAtPosition(position).toString(), Toast.LENGTH_SHORT).show();
                //ToDo Change ArrayList to show request of selected priority
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Nothing
            }
        });
        //Get requestData from database

        //temporary arrayList
        ArrayList<RequestClass> list = new ArrayList<>();
        list.add(new RequestClass("00230A10","User_1","subject_1","details_1"));
        list.add(new RequestClass("00230A11","User_2","subject_2","details_2"));
        list.add(new RequestClass("00230A12","User_3","subject_3","details_3"));
        list.add(new RequestClass("00230A13","User_4","subject_4","details_4"));
        list.add(new RequestClass("00230A14","User_5","subject_5","details_5"));

        adapterClass.setupAdapter(list);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}