package com.qrofeus.requestmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ramotion.foldingcell.FoldingCell;

import java.util.ArrayList;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ItemViewHolder> {
    private Context context;
    private String user;
    private ArrayList<RequestClass> requestArrayList;

    public AdapterClass(Context context, String user) {
        this.context = context;
        this.user = user;
    }

    //List to Adapter
    public void setupAdapter(ArrayList<RequestClass> arrayList) {
        requestArrayList = arrayList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.folding_cells, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        RequestClass request = requestArrayList.get(position);

        holder.title_id.setText(request.getRequest_id());
        holder.title_subject.setText(request.getRequest_subject());
        holder.content_details.setText(request.getRequest_details());
        holder.content_username.setText(request.getUsername());

        if (user.equals("Admin")) {
            holder.button_manage.setEnabled(true);
            holder.button_manage.setVisibility(View.VISIBLE);
        } else if (user.equals("Customer")) {
            holder.button_manage.setEnabled(false);
            holder.button_manage.setVisibility(View.INVISIBLE);
        }

        holder.foldingCell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.foldingCell.toggle(false);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestArrayList.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        private FoldingCell foldingCell;
        private TextView title_id;
        private TextView title_subject;
        private TextView content_username;
        private TextView content_details;
        private CardView button_manage;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);

            foldingCell = itemView.findViewById(R.id.folding_cell);
            title_id = itemView.findViewById(R.id.requestID);
            title_subject = itemView.findViewById(R.id.request_subject);
            content_username = itemView.findViewById(R.id.content_username);
            content_details = itemView.findViewById(R.id.content_details);
            button_manage = itemView.findViewById(R.id.manage_request);
        }
    }
}
