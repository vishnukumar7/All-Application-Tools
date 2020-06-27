package com.example.allapps.fileManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.Interface.OnItemClickListener;
import com.example.allapps.R;
import com.example.allapps.Utils;
import com.example.allapps.VideoActivity;
import com.example.allapps.model.AllFacer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static com.example.allapps.Utils.setTimeText;

public class FileManagerActivity extends AppCompatActivity {


    public static int position = 1;
    public static String path;
    private final int forwardTime = 5000, backwardTime = 5000;
    ImageButton back, createNew;
    HashMap<Integer, Integer> hashMap;
    private MediaPlayer mediaPlayer;
    private ImageButton pause;
    private AppCompatSeekBar seekBar;
    private Handler handler;
    private int finalTime, startTime;
    private TextView startDuration;
    private final Runnable setProgress = new Runnable() {
        @SuppressLint("DefaultLocale")
        @Override
        public void run() {
            int startTime = mediaPlayer.getCurrentPosition();
            seekBar.setProgress(startTime);
            handler.postDelayed(setProgress, 500);

            setTimeText(startDuration, startTime);
        }
    };

    @SuppressLint("UseSparseArrays")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_manager);
        back = findViewById(R.id.backButton);
        createNew = findViewById(R.id.create_new_file);
        //Bundle extras=getIntent().getExtras();
        path = Environment.getExternalStorageDirectory().getAbsolutePath();
        callList(path, 0);
        hashMap = new HashMap<>();
        //callListData(path);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position <= 1)
                    onBackPressed();
                else {
                    position = position - 1;

                    path = new File(path).getParent();

                    callList(path, hashMap.get(position));
                }
            }
        });

        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewFolder(FileManagerActivity.this, path);

            }
        });

    }

    public void callList(final String callPath, int setAdapterPosition) {
        final ArrayList<AllFacer> models = Utils.getFilesMEdisStore(callPath, this);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        FileAdapter adapter = new FileAdapter(models, new OnItemClickListener() {

            @Override
            public void onItemClick(Object object, int adapterPosition) {
                AllFacer model = (AllFacer) object;
                if (!model.isFile()) {
                    hashMap.put(position, adapterPosition);
                    position = position + 1;
                    path = model.getFilePath();
                    callList(path, 0);
                } else {


                    switch (model.getMediaType()) {
                        case MediaStore.Files.FileColumns.MEDIA_TYPE_AUDIO:
                            callRunSong(model.getFilePath(), FileManagerActivity.this);
                            break;
                        case MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO:
                            Intent intent = new Intent(FileManagerActivity.this, VideoActivity.class);
                            intent.putExtra("filePath", model.getFilePath());
                            startActivity(intent);
                            break;
                        case MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE:
//                            Intent move = new Intent(FileManagerActivity.this, FileDisplay.class);
//                            move.putExtra("folderPath", model.getFilePath());
//                            move.putExtra("folderName", model.getFolderName());
//                            move.putExtra("type", "Image");
//                            startActivity(move);

                    }
                }
            }

            @Override
            public void onLongItemClick(Object object) {
                final AllFacer model = (AllFacer) object;
                new AlertDialog.Builder(FileManagerActivity.this)
                        .setTitle(model.getFileName())
                        .setMessage("File Options")
                        .setCancelable(false)
                        .setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new File(model.getFilePath()).delete();
                                callList(callPath, 0);
                            }
                        })
                        .setPositiveButton("CANCEL", null)
                        .setNeutralButton("Properties", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Utils.viewProperties(FileManagerActivity.this, model.getFilePath());
                            }
                        })
                        .show();
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recyclerViewList);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(manager);
        recyclerView.scrollToPosition(setAdapterPosition);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {
        if (position <= 1)
            super.onBackPressed();
        else {
            position = position - 1;
            path = new File(path).getParent();
            callList(path, hashMap.get(position));
        }
    }

    private void createNewFolder(final Context context, final String path) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.create_new_folder, null);
        final EditText newFileName = dialogView.findViewById(R.id.createNewFileName);

        builder.setView(dialogView)
                .setCancelable(false)
                .setPositiveButton("CREATE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final File file = new File(path + File.separator + newFileName.getText().toString());
                        if (file.exists())
                            Toast.makeText(context, "File Name is already created", Toast.LENGTH_SHORT).show();
                        else {
                            boolean success = file.mkdir();
                            if (success) {
                                Toast.makeText(context, "Directory Created", Toast.LENGTH_SHORT).show();
                                callList(path, 0);
                            } else
                                Toast.makeText(context, "Failed - Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("CANCEL", null)
                .show();

    }

    private void callRunSong(String filePath, Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.myDialog));
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View dialogView = layoutInflater.inflate(R.layout.song_running_view, null);
        builder.setView(dialogView);
        TextView songName = dialogView.findViewById(R.id.songName);
        ImageButton rewind = dialogView.findViewById(R.id.rewind);
        ImageButton forward = dialogView.findViewById(R.id.forward);
        pause = dialogView.findViewById(R.id.pause);
        seekBar = dialogView.findViewById(R.id.songRunning);
        startDuration = dialogView.findViewById(R.id.startTime);
        TextView endDuration = dialogView.findViewById(R.id.endTime);
        mediaPlayer = new MediaPlayer();
        handler = new Handler();
        try {
            mediaPlayer.setDataSource(filePath);
            mediaPlayer.prepare();
            mediaPlayer.start();
            finalTime = mediaPlayer.getDuration();
            seekBar.setMax(finalTime);
            startTime = mediaPlayer.getCurrentPosition();
            setTimeText(startDuration, startTime);
            handler.postDelayed(setProgress, 500);
            songName.setText(new File(filePath).getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        setTimeText(endDuration, finalTime);
        System.out.println("//endtime : " + finalTime);
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                    handler.removeCallbacks(setProgress);
                    mediaPlayer.pause();
                } else {
                    pause.setImageResource(R.drawable.ic_pause_black_24dp);
                    handler.postDelayed(setProgress, 500);
                    mediaPlayer.start();
                }

            }
        });

        rewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = mediaPlayer.getCurrentPosition();
                if ((startTime - backwardTime) > 0) {
                    startTime = startTime - backwardTime;
                    mediaPlayer.seekTo(startTime);
                }
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = mediaPlayer.getCurrentPosition();
                if ((startTime + forwardTime) <= finalTime) {
                    startTime = startTime + forwardTime;
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
}
