package com.example.allapps.genericFile;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.allapps.Interface.ItemFolderClickListener;
import com.example.allapps.R;
import com.example.allapps.model.FolderModel;


import java.util.ArrayList;

/**
 * Author CodeBoy722
 *
 * An adapter for populating RecyclerView with items representing folders that contain images
 */
public class FolderAdapter extends RecyclerView.Adapter<FolderAdapter.FolderHolder>{

    private ArrayList<FolderModel> folders;
    private Context folderContx;
    private ItemFolderClickListener listenToClick;
    private String type;


    public FolderAdapter(ArrayList<FolderModel> folders, Context folderContx, ItemFolderClickListener listen, String type) {
        this.folders = folders;
        this.folderContx = folderContx;
        this.listenToClick = listen;
        this.type=type;
    }

    @NonNull
    @Override
    public FolderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View cell = inflater.inflate(R.layout.picture_folder_item, parent, false);
        return new FolderHolder(cell);

    }

    @Override
    public void onBindViewHolder(@NonNull FolderHolder holder, int position) {
        final FolderModel folder = folders.get(position);
        if(type.equalsIgnoreCase("Audio"))
            Glide.with(folderContx)
                .load(R.drawable.audio)
                .apply(new RequestOptions().centerCrop())
                .into(holder.folderPic);
        else
            Glide.with(folderContx)
                    .load(folder.getFirstPics())
                    .apply(new RequestOptions().centerCrop())
                    .into(holder.folderPic);

        String text = "("+folder.getPics()+") "+folder.getFolderName();
        holder.folderName.setText(text);

        holder.folderPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenToClick.onPicClicked(folder.getFolderPath(),folder.getFolderName());
            }
        });

    }

    @Override
    public int getItemCount() {
        return folders.size();
    }


    public class FolderHolder extends RecyclerView.ViewHolder{
       ImageView folderPic;
       TextView folderName;
       CardView folderCard;

        public FolderHolder(@NonNull View itemView) {
            super(itemView);
           folderPic = itemView.findViewById(R.id.folderPic);
           folderName = itemView.findViewById(R.id.folderName);
           folderCard = itemView.findViewById(R.id.folderCard);
        }
    }

}
