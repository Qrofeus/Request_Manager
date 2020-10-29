package com.qrofeus.requestmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;

import com.ramotion.foldingcell.FoldingCell;

public class FoldingCells extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folding_cells);

        //ToDo
        // get extras : Request ID, Username, Subject, Details, layout_user

        // layout_user - External -> Hide the manage_request button
        // layout_user - Admin -> Hide the view_request button

        // Folding Cell Animation
        final FoldingCell fc = (FoldingCell) findViewById(R.id.folding_cell);

        fc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fc.toggle(false);
            }
        });
    }
}