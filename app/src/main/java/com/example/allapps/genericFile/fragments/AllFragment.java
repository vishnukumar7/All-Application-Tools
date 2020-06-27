package com.example.allapps.genericFile.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;
import com.example.allapps.genericFile.FileDisplay;
import com.example.allapps.genericFile.FolderAdapter;
import com.example.allapps.Interface.ItemFolderClickListener;
import com.example.allapps.model.FolderModel;

import java.util.ArrayList;

public class AllFragment extends Fragment implements ItemFolderClickListener {

    RecyclerView recyclerView;
    TextView empty, genericName;
    private String title;
    private ArrayList<FolderModel> folds;
    private View view;

    public AllFragment(){
        super();
    }

    public AllFragment(String title, ArrayList<FolderModel> folds) {
        this.title = title;
        this.folds = folds;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_generic, container, false);
        recyclerView = view.findViewById(R.id.folderRecycler);
        empty = view.findViewById(R.id.empty);
        genericName = view.findViewById(R.id.genericName);

        if (title != null) {
            if (folds.isEmpty()) {
                empty.setVisibility(View.VISIBLE);
            } else {
                System.out.println("//folder szie : " + folds.size());
                RecyclerView.Adapter folderAdapter = new FolderAdapter(folds, requireContext(), this, title);
                recyclerView.setAdapter(folderAdapter);
            }

        }
        return view;
    }


    @Override
    public void onPicClicked(String pictureFolderPath, String folderName) {
        Intent move = new Intent(requireContext(), FileDisplay.class);
        move.putExtra("folderPath", pictureFolderPath);
        move.putExtra("folderName", folderName);
        move.putExtra("type", title);
        startActivity(move);
    }
}
