package com.example.allapps.genericFile;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.Utils;
import com.example.allapps.R;
import com.example.allapps.model.AllFacer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.allapps.Utils.setTimeText;

public class AudioAdapter extends RecyclerView.Adapter<AudioAdapter.CustomerViewHolder> {

    private final ArrayList<AllFacer> playerModels;
    private MediaPlayer mediaPlayer;
    private ImageButton pause;
    private AppCompatSeekBar seekBar;
    private Handler handler;
    private int finalTime,startTime;
    private final int forwardTime=5000,backwardTime=5000;
    private TextView startDuration;
    private final Runnable setProgress = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            int startTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(startTime);
            handler.postDelayed(setProgress, 500);

            setTimeText(startDuration,startTime);
        }
    };
    private final Context context;

    public AudioAdapter(Context context, ArrayList<AllFacer> playerModels) {
        this.playerModels = playerModels;
        this.context=context;
        mediaPlayer = new MediaPlayer();
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CustomerViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_audio, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomerViewHolder holder, final int position) {
        final AllFacer model = playerModels.get(position);
        holder.name.setText(model.getFileName().trim());

        holder.songView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callRunSong(model.getFilePath(), v,holder);


            }
        });
        holder.songView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(model.getFileName())
                        .setMessage("File Options")
                        .setCancelable(false)
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new File(model.getFilePath()).delete();
                                notifyItemRemoved(position);
                            }
                        })
                        .setPositiveButton("CANCEL", null)
                        .setNeutralButton("Properties", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.viewProperties(context, model.getFilePath());
                            }
                        })
                        .show();
                return true;
            }
        });

    }

    private void callRunSong(String filePath, View v, CustomerViewHolder holder) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(v.getContext(), R.style.myDialog));
        LayoutInflater layoutInflater = LayoutInflater.from(v.getContext());
        View dialogView = layoutInflater.inflate(R.layout.song_running_view, null);
        builder.setView(dialogView);
        TextView songName = dialogView.findViewById(R.id.songName);
        ImageButton rewind = dialogView.findViewById(R.id.rewind);
        ImageButton forward = dialogView.findViewById(R.id.forward);
        pause = dialogView.findViewById(R.id.pause);
        seekBar = dialogView.findViewById(R.id.songRunning);
        startDuration=dialogView.findViewById(R.id.startTime);
        TextView endDuration = dialogView.findViewById(R.id.endTime);
        mediaPlayer = new MediaPlayer();
        handler = new Handler();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            finalTime=mediaPlayer.getDuration();
            seekBar.setMax(finalTime);
            startTime=mediaPlayer.getCurrentPosition();
            setTimeText(startDuration, startTime);
            handler.postDelayed(setProgress, 500);
            songName.setText(new File(filePath).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTimeText(endDuration, finalTime);
        System.out.println("//endtime : "+finalTime);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    handler.removeCallbacks(setProgress);
                    mediaPlayer.pause();
                } else {
                    pause.setImageResource(R.drawable.ic_pause_black_24dp);
                    handler.postDelayed(setProgress,500);
                    mediaPlayer.start();
                }

            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime=mediaPlayer.getCurrentPosition();
                if((startTime-backwardTime)>0)
                {
                    startTime=startTime-backwardTime;
                    mediaPlayer.seekTo(startTime);
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime=mediaPlayer.getCurrentPosition();
                if((startTime+forwardTime)<=finalTime)
                {
                    startTime=startTime+forwardTime;
                    mediaPlayer.seekTo(startTime);
                }
            }
        });


        AlertDialog alertDialog = builder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mediaPlayer.stop();
                        handler.removeCallbacks(setProgress);
                    }
                })
                .create();
        alertDialog.show();


    }

    private void callRunSong1(String filePath, View v, CustomerViewHolder holder) {
       mediaPlayer=new MediaPlayer();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            if(mediaPlayer.isPlaying()){
                holder.imageView.setImageResource(R.drawable.ic_play_circle_filled_black_24dp);
                mediaPlayer.pause();
            }
            else{
                mediaPlayer.start();
                holder.imageView.setImageResource(R.drawable.ic_pause_circle_filled_black_24dp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return playerModels.size();
    }

    static class CustomerViewHolder extends RecyclerView.ViewHolder {
        final TextView name;
        final ImageView imageView;
        final LinearLayout songView;

        CustomerViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageSong);
            name = itemView.findViewById(R.id.setName);
            songView = itemView.findViewById(R.id.songView);
        }
    }
}
