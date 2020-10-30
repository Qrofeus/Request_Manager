package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class RequestList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterClass adapterClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);

        //Recycler View Set Up
        recyclerView = findViewById(R.id.request_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setFocusable(false);

        adapterClass = new AdapterClass(this);
        recyclerView.setAdapter(adapterClass);

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
}