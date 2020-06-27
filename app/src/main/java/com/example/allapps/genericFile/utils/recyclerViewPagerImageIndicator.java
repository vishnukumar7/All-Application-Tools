package com.example.allapps.genericFile.utils;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.allapps.Interface.imageIndicatorListener;
import com.example.allapps.R;
import com.example.allapps.model.AllFacer;

import java.util.ArrayList;

/**
 * Author CodeBoy722
 */
public class recyclerViewPagerImageIndicator extends RecyclerView.Adapter<indicatorHolder> {

    ArrayList<AllFacer> pictureList;
    Context pictureContx;
    private final imageIndicatorListener imageListerner;

    /**
     *
     * @param pictureList ArrayList of AllFacer objects
     * @param pictureContx The Activity of fragment context
     * @param imageListerner Interface for communication between adapter and fragment
     */
    public recyclerViewPagerImageIndicator(ArrayList<AllFacer> pictureList, Context pictureContx, imageIndicatorListener imageListerner) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.imageListerner = imageListerner;
    }


    @NonNull
    @Override
    public indicatorHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.indicator_holder, parent, false);
        return new indicatorHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull indicatorHolder holder, final int position) {

        final AllFacer pic = pictureList.get(position);

        holder.positionController.setBackgroundColor(pic.getSelected() ? Color.parseColor("#00000000") : Color.parseColor("#8c000000"));

        Glide.with(pictureContx)
                .load(pic.getFilePath())
                .apply(new RequestOptions().centerCrop())
                .into(holder.image);

        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //holder.card.setCardElevation(5);
                pic.setSelected(true);
                notifyDataSetChanged();
                imageListerner.onImageIndicatorClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
