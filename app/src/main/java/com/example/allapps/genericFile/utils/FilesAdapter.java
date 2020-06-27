package com.example.allapps.genericFile.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.allapps.R;
import com.example.allapps.Utils;
import com.example.allapps.Interface.ItemFileClickListener;
import com.example.allapps.model.AllFacer;

import java.io.File;
import java.util.ArrayList;

import static androidx.core.view.ViewCompat.setTransitionName;

/**
 * Author CodeBoy722
 * <p>
 * A RecyclerView Adapter class that's populates a RecyclerView with images from
 * a folder on the device external storage
 */
public class FilesAdapter extends RecyclerView.Adapter<PicHolder> {

    private final ItemFileClickListener picListerner;
    private ArrayList<AllFacer> pictureList;
    private Context pictureContx;
    private String type;

    /**
     * @param pictureList  ArrayList of AllFacer objects
     * @param pictureContx The Activities Context
     * @param picListerner An interface for listening to clicks on the RecyclerView's items
     * @param type
     */
    public FilesAdapter(ArrayList<AllFacer> pictureList, Context pictureContx, ItemFileClickListener picListerner, String type) {
        this.pictureList = pictureList;
        this.pictureContx = pictureContx;
        this.picListerner = picListerner;
        this.type = type;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(container.getContext());
        View cell = inflater.inflate(R.layout.pic_holder_item, container, false);
        return new PicHolder(cell);
    }

    @Override
    public void onBindViewHolder(@NonNull final PicHolder holder, final int position) {

        final AllFacer image = pictureList.get(position);
        if (type.equalsIgnoreCase("Audio"))
            Glide.with(pictureContx)
                    .load(R.drawable.audio)
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.picture);
        else
            Glide.with(pictureContx)
                    .load(image.getFilePath())
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.picture);

        setTransitionName(holder.picture, position + "_image");

        holder.picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equalsIgnoreCase("Image"))
                    picListerner.onPicClicked(holder, position, pictureList);
                else if (type.equalsIgnoreCase("Video"))
                    picListerner.onVideoClicked(holder, position, pictureList);
                else if (type.equalsIgnoreCase("Audio"))
                    picListerner.onAudioClicked(holder, position, pictureList);
            }
        });

        holder.picture.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View v) {
                new AlertDialog.Builder(v.getContext())
                        .setTitle(image.getFileName())
                        .setMessage("File Options")
                        .setCancelable(false)
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new File(image.getFilePath()).delete();
                                notifyItemRemoved(position);
                            }
                        })
                        .setPositiveButton("CANCEL", null)
                        .setNeutralButton("Properties", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.viewProperties(v.getContext(), image.getFilePath());
                            }
                        })
                        .show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }
}
