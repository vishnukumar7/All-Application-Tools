package com.example.allapps.recyclerView.marvel;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;
import com.example.allapps.databinding.ActivityMarvelBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class MarvelAdpater extends androidx.recyclerview.widget.RecyclerView.Adapter<MarvelAdpater.CustomViewHolder> {

    private final ArrayList<Marvel> marvelContent;



    public MarvelAdpater(ArrayList<Marvel> marvelContents) {
        this.marvelContent = marvelContents;

    }

    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ActivityMarvelBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.activity_marvel, parent, false);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final CustomViewHolder holder, final int position) {
        final Marvel marvel = marvelContent.get(position);
        holder.binding.setMarvel(marvel);
        holder.binding.currentData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.currentData.setVisibility(View.GONE);
                    holder.binding.current.setVisibility(View.VISIBLE);
            }
        });
        holder.binding.current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.binding.currentData.setVisibility(View.VISIBLE);
                holder.binding.current.setVisibility(View.GONE);
            }
        });



    }


    @Override
    public int getItemCount() {
        return marvelContent.size();
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        ActivityMarvelBinding binding;

        CustomViewHolder(ActivityMarvelBinding view) {
            super(view.getRoot());
            binding=view;
        }
    }
}
