package com.example.allapps.splitWise.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.database.DBHandler;
import com.example.allapps.R;
import com.example.allapps.splitWise.Item;
import com.example.allapps.splitWise.adapter.ItemAdapter;

import java.util.ArrayList;

import static com.example.allapps.MainActivity.DATABASE_NAME;

public class Tab extends Fragment {

    private String title;
    private RecyclerView recyclerView;

    public Tab(String title) {
        super();
        this.title = title;
    }
    Tab(){
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_recyler_view, container, false);
        DBHandler dbHandler = new DBHandler(requireContext(), DATABASE_NAME);
        ArrayList<Item> items = dbHandler.getItemArrayList(title);
        TextView total = view.findViewById(R.id.total);
        recyclerView = view.findViewById(R.id.recylerView);
        int totalAmount = getAmount(items);
        total.setText("Total Amount : " + totalAmount);
        setRecyclerView(items);
        return view;
    }

    private int getAmount(ArrayList<Item> list) {
        int total = 0;
        for (Item item : list)
            total += Integer.parseInt(item.getAmount());
        return total;

    }


    private void setRecyclerView(ArrayList<Item> items) {

        ItemAdapter adapter = new ItemAdapter(items);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


}
