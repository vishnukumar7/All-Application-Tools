package com.example.allapps.Interface;

import com.example.allapps.genericFile.utils.PicHolder;
import com.example.allapps.model.AllFacer;

import java.util.ArrayList;

public interface ItemFileClickListener {
    void onPicClicked(PicHolder holder, int position, ArrayList<AllFacer> pics);

    void onVideoClicked(PicHolder holder, int position, ArrayList<AllFacer> pics);

    void onAudioClicked(PicHolder holder, int position, ArrayList<AllFacer> pics);


}
