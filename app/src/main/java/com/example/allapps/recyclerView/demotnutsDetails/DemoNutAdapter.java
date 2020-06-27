package com.example.allapps.recyclerView.demotnutsDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;
import com.example.allapps.databinding.ViewDemonutsBinding;
import com.example.allapps.recyclerView.DataItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class DemoNutAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<DemoNutAdapter.CustomViewHolder> {

    private final ArrayList<DataItem> dataItems;
    public DemoNutAdapter(ArrayList<DataItem> dataItems) {
        this.dataItems = dataItems;
    }

    @NotNull
    @Override
    public DemoNutAdapter.CustomViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        ViewDemonutsBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.view_demonuts, parent, false);

        return new DemoNutAdapter.CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NotNull final DemoNutAdapter.CustomViewHolder holder, final int position) {
        final DataItem dataItem = dataItems.get(position);
        holder.binding.setDataItem(dataItem);
        holder.binding.currentData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.currentData.setVisibility(View.GONE);
                holder.binding.demoNutsTableLayout.setVisibility(View.VISIBLE);
            }
        });

        holder.binding.demoNutsTableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.demoNutsTableLayout.setVisibility(View.GONE);
                holder.binding.currentData.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataItems.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {

        ViewDemonutsBinding binding;

        CustomViewHolder(ViewDemonutsBinding view) {
            super(view.getRoot());

            binding = view;
        }
    }
}

