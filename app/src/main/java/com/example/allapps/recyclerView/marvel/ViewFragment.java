package com.example.allapps.recyclerView.marvel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.allapps.R;
import com.example.allapps.Utils;
import com.example.allapps.recyclerView.DataItem;

import java.io.File;

public class ViewFragment extends Fragment {

    private TextView characterName, realName, team, firstAppearance, createdBy, publisher, bio;
    private TextView id, name, city, country;
    private ImageView imageView, imageViewDemo;
    private final Context context;
    private final int type;
    private final Object object;
    private View view;
    private TableLayout marvelViewTabLayout, demoNutsTabLayout;

    public ViewFragment(Context context, Object object, int type) {
        this.context = context;
        this.object = object;
        this.type = type;

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_image_view, container, false);
        marvelViewTabLayout = view.findViewById(R.id.marvelViewTabLayout);
        demoNutsTabLayout = view.findViewById(R.id.demoNutsTableLayout);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        switch (type) {
            case 1:
                Marvel marvel = (Marvel) object;
                initMarvel(view);
                marvelViewTabLayout.setVisibility(View.VISIBLE);
                demoNutsTabLayout.setVisibility(View.GONE);
                realName.setText(marvel.getRealName());
                bio.setText(marvel.getBio());
                createdBy.setText(marvel.getCreatedBy());
                firstAppearance.setText(marvel.getFirstAppearance());
                publisher.setText(marvel.getPublisher());
                team.setText(marvel.getTeam());
                characterName.setText(marvel.getName());
                Glide.with(context).load(new File(Utils.getFileStorage(marvel.getImage()))).into(imageView);
                break;
            case 2:
                DataItem dataItem = (DataItem) object;
                initDemo(view);
                marvelViewTabLayout.setVisibility(View.GONE);
                demoNutsTabLayout.setVisibility(View.VISIBLE);
                id.setText(dataItem.getId());
                name.setText(dataItem.getName());
                city.setText(dataItem.getCity());
                country.setText(dataItem.getCountry());
                Glide.with(context).load(new File(Utils.getFileStorage(dataItem.getImgURL()))).into(imageViewDemo);
                break;
            case 3:

        }

    }

    private void initDemo(View view) {
        id = view.findViewById(R.id.demoId);
        name = view.findViewById(R.id.demoName);
        city = view.findViewById(R.id.city);
        country = view.findViewById(R.id.country);
        imageViewDemo = view.findViewById(R.id.imageViewDemo);
    }

    private void initMarvel(View view) {
        characterName = view.findViewById(R.id.characterName);
        realName = view.findViewById(R.id.realName);
        team = view.findViewById(R.id.team);
        firstAppearance = view.findViewById(R.id.firstAppearance);
        createdBy = view.findViewById(R.id.createdBy);
        publisher = view.findViewById(R.id.publisher);
        bio = view.findViewById(R.id.bio);
        imageView = view.findViewById(R.id.imageView);

    }
}
