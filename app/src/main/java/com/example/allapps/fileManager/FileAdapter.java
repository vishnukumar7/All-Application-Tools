package com.example.allapps.fileManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.Interface.OnItemClickListener;
import com.example.allapps.R;
import com.example.allapps.Utils;
import com.example.allapps.model.AllFacer;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.CustomFileViewHolder> {

    private ArrayList<AllFacer> mediaFileListModels;
    private OnItemClickListener listener;

    public FileAdapter( ArrayList<AllFacer> mediaFileListModels, OnItemClickListener listener){
        this.mediaFileListModels=mediaFileListModels;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CustomFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomFileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.media_list_item_view,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CustomFileViewHolder holder, final int position) {
        final AllFacer model=mediaFileListModels.get(position);
        print(model);
        holder.fileName.setText(model.getFileName());
        holder.imageIcon.setImageResource(Utils.getFileImageType(model.getFilePath()));
        holder.listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(model,position);
            }
        });
        holder.listView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                listener.onLongItemClick(model);
                return true;
            }
        });

    }

    private void print(AllFacer model) {
        System.out.println("//model "+model.getFileName()+" "+model.getFilePath());
    }

    @Override
    public int getItemCount() {
        return mediaFileListModels.size();
    }

    public class CustomFileViewHolder extends RecyclerView.ViewHolder{
        LinearLayout listView;
        ImageView imageIcon;
        TextView fileName;
        public CustomFileViewHolder(@NonNull View itemView) {
            super(itemView);
            listView=itemView.findViewById(R.id.list_view);
            imageIcon=itemView.findViewById(R.id.fileAdapterIcon);
            fileName=itemView.findViewById(R.id.fileAdaptername);
        }
    }
}
