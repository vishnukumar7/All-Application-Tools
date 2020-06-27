package com.example.allapps.splitWise.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.splitWise.Item;
import com.example.allapps.R;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.CustomViewHolder> {
    private final ArrayList<Item> items;

    public ItemAdapter(ArrayList<Item> items) {

        this.items=items;
    }

    @NonNull
    @Override
    public ItemAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_view, parent, false);
        return new CustomViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Item item=items.get(position);
        System.out.println("://items : "+item.getName());
        holder.name.setText(item.getName());
        holder.amount.setText(item.getAmount());
        holder.date.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{
        final TextView name;
        final TextView amount;
        final TextView date;
        final CardView items;
         CustomViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.setItemName);
            amount=itemView.findViewById(R.id.setItemAmount);
            date=itemView.findViewById(R.id.setItemDate);
            items=itemView.findViewById(R.id.items);
        }
    }

    
}
