package com.example.allapps;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.example.allapps.Interface.CallBacks;
import com.google.android.exoplayer2.ui.PlayerView;

public class VideoActivity extends AppCompatActivity implements CallBacks.playerCallBack {
    String mFilePath = null;
    private static final String FILE_PATH = "PlayerActivity";
    PlayerView mPlayerView;
    private final Handler handler = new Handler();
    private final Runnable hideRunnable = new Runnable() {
        @Override
        public void run() {
            mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        if (getIntent().hasExtra(FILE_PATH)) {
            mFilePath = getIntent().getStringExtra(FILE_PATH);
        }
         mPlayerView = findViewById(R.id.mPlayerView);
        mPlayerView.setPlayer(PlayerManager.getSharedInstance(VideoActivity.this).getPlayerView().getPlayer());
        PlayerManager.getSharedInstance(VideoActivity.this).playStream(mFilePath);
        PlayerManager.getSharedInstance(this).setPlayerListener(this);
    }


    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        //  mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        // handler.removeCallbacks(showRunnable);
        handler.postDelayed(hideRunnable, 300);
    }


    @Override
    public void onItemClickOnItem(Integer albumId) {

    }

    @Override
    public void onPlayingEnd() {
        PlayerManager.getSharedInstance(VideoActivity.this).pausePlayer();
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        PlayerManager.getSharedInstance(VideoActivity.this).pausePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        hide();
        PlayerManager.getSharedInstance(VideoActivity.this).resumePlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PlayerManager.getSharedInstance(VideoActivity.this).pausePlayer();
    }

}