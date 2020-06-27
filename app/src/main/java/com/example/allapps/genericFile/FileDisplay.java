package com.example.allapps.genericFile;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.Fade;

import com.example.allapps.Interface.ItemFileClickListener;
import com.example.allapps.R;
import com.example.allapps.Utils;
import com.example.allapps.VideoActivity;
import com.example.allapps.genericFile.fragments.pictureBrowserFragment;
import com.example.allapps.genericFile.utils.MarginDecoration;
import com.example.allapps.genericFile.utils.PicHolder;

import com.example.allapps.model.AllFacer;
import com.example.allapps.genericFile.utils.FilesAdapter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.allapps.Utils.setTimeText;

public class FileDisplay extends AppCompatActivity implements ItemFileClickListener {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        TextView folderName = findViewById(R.id.foldername);
        String fName=getIntent().getStringExtra("folderName");
        folderName.setText(fName);

        String foldePath = getIntent().getStringExtra("folderPath");
        ArrayList<AllFacer> allpictures = new ArrayList<>();
        RecyclerView imageRecycler = findViewById(R.id.recycler);
        imageRecycler.addItemDecoration(new MarginDecoration(this));
        imageRecycler.hasFixedSize();
        ProgressBar load = findViewById(R.id.loader);
        String type = getIntent().getStringExtra("type");

        System.out.println("//folder path : "+foldePath);
        if (allpictures.isEmpty()) {
            load.setVisibility(View.VISIBLE);
            if (type.equalsIgnoreCase("Image")){
                allpictures = Utils.getAllImagesByFolder(foldePath, this);
                imageRecycler.setAdapter(new FilesAdapter(allpictures, FileDisplay.this, this, type));}
            else if(type.equalsIgnoreCase("Video")) {
                allpictures = Utils.getAllVideoByFolder(foldePath, this);
                imageRecycler.setAdapter(new FilesAdapter(allpictures, FileDisplay.this, this, type));
            }
            else if(type.equalsIgnoreCase("Audio")) {
              //  System.out.println("//folder path : "+foldePath);
                allpictures = Utils.getAllAudioByFolder(foldePath, this);
                imageRecycler.setLayoutManager(new LinearLayoutManager(this));
                imageRecycler.setAdapter(new AudioAdapter(this,allpictures));
            }
            System.out.println("//type : "+ type);
            System.out.println("//size : "+ allpictures.size());

            load.setVisibility(View.GONE);
        }
    }

    /**
     * @param holder   The ViewHolder for the clicked picture
     * @param position The position in the grid of the picture that was clicked
     * @param pics     An ArrayList of all the items in the Adapter
     */
    @Override
    public void onPicClicked(PicHolder holder, int position, ArrayList<AllFacer> pics) {
        pictureBrowserFragment browser = pictureBrowserFragment.newInstance(pics, position, FileDisplay.this);


        browser.setEnterTransition(new Fade());
        browser.setExitTransition(new Fade());

        getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.picture, position + "picture")
                .add(R.id.displayContainer, browser)
                .addToBackStack(null)
                .commit();


    }

    @Override
    public void onVideoClicked(PicHolder holder, int position, ArrayList<AllFacer> pics) {
        Intent intent = new Intent(FileDisplay.this, VideoActivity.class);
        intent.putExtra("PlayerActivity", pics.get(position).getFilePath());
        startActivity(intent);

    }

    @Override
    public void onAudioClicked(PicHolder holder, int position, ArrayList<AllFacer> pics) {

        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(FileDisplay.this, R.style.myDialog));
        LayoutInflater layoutInflater = LayoutInflater.from(FileDisplay.this);
        View dialogView = layoutInflater.inflate(R.layout.song_running_view, null);
        builder.setView(dialogView);
        TextView songName = dialogView.findViewById(R.id.songName);
        ImageButton rewind = dialogView.findViewById(R.id.rewind);
        ImageButton forward = dialogView.findViewById(R.id.forward);
        pause = dialogView.findViewById(R.id.pause);
        seekBar = dialogView.findViewById(R.id.songRunning);
        startDuration=dialogView.findViewById(R.id.startTime);
        final TextView endDuration = dialogView.findViewById(R.id.endTime);
        mediaPlayer = new MediaPlayer();
        handler = new Handler();
        try {
            mediaPlayer.setDataSource(pics.get(position).getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            finalTime=mediaPlayer.getDuration();
            seekBar.setMax(finalTime);
            startTime=mediaPlayer.getCurrentPosition();
            setTimeText(startDuration, startTime);
            handler.postDelayed(setProgress, 500);
            songName.setText(new File(pics.get(position).getFilePath()).getName());
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

}
