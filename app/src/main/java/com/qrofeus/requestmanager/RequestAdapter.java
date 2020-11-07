package com.qrofeus.requestmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.RequestViewHolder> {

    private ArrayList<RequestClass> arrayList;
    private OnItemClickListener clickListener;

    public RequestAdapter(ArrayList<RequestClass> arrayList) {
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        clickListener = listener;
    }

    @NonNull
    @Override
    public RequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_card, parent, false);
        return new RequestViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RequestViewHolder holder, int position) {
        RequestClass request = arrayList.get(position);

        holder.cardID.setText(request.getRequest_id());
        holder.cardSubject.setText(request.getRequest_subject());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public static class RequestViewHolder extends RecyclerView.ViewHolder {

        public TextView cardID;
        public TextView cardSubject;

        public RequestViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            cardID = itemView.findViewById(R.id.request_listID);
            cardSubject = itemView.findViewById(R.id.request_listSubject);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(getAdapterPosition());
                }
            });
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
