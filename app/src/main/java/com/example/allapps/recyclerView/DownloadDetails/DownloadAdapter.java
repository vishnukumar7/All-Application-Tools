package com.example.allapps.recyclerView.DownloadDetails;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;


public class DownloadAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<DownloadAdapter.CustomViewHolder>  {
    private final ArrayList<DataList> downloadContent;

    public DownloadAdapter(ArrayList<DataList> downloadContents) {
        this.downloadContent = downloadContents;
    }

    @NotNull
    @Override
    public DownloadAdapter.CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.download_view, parent, false);
        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DownloadAdapter.CustomViewHolder holder, int position) {
        DataList download = downloadContent.get(position);
        holder.id.setText(download.getUpload_id());
        holder.display.setText(download.getContent_display_name());
        holder.checksum.setText(download.getChecksum());
        holder.size.setText(download.getContentSize());
        holder.path.setText(download.getContent_download_path());
        holder.type.setText(download.getContent_type());
    }

    @Override
    public int getItemCount() {
        return downloadContent.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView type;
        private final TextView path;
        private final TextView size;
        private final TextView checksum;
        private final TextView display;

        CustomViewHolder(View view) {
            super(view);
            id=view.findViewById(R.id.upload_id);
            type=view.findViewById(R.id.content_type);
            path=view.findViewById(R.id.download_path);
            size=view.findViewById(R.id.download_size);
            checksum=view.findViewById(R.id.checksum);
            display=view.findViewById(R.id.display);

        }
    }
}
