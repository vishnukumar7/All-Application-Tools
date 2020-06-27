package com.example.allapps.recyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;
import com.example.allapps.databinding.ActivityRecyclerViewBinding;
import com.example.allapps.recyclerView.employeeDetails.Employee;
import com.example.allapps.recyclerView.employeeDetails.EmployeeAdapter;
import com.example.allapps.recyclerView.marvel.Marvel;
import com.example.allapps.recyclerView.marvel.MarvelAdpater;
import com.example.allapps.recyclerView.demotnutsDetails.DemoNutAdapter;

import java.util.ArrayList;


public class Tab extends Fragment implements LifecycleOwner {
    private final String title;
    private RecyclerView recyclerView;
    private ActivityRecyclerViewBinding binding;

    private MainViewModel mainViewModel;


    public Tab(String title) {
        this.title = title;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.activity_recycler_view,container,false);

        switch (title) {
            default:
            case "Employee":
                recyclerView=binding.myRecycleView;
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setHasFixedSize(false);
                mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
                mainViewModel.getEmployee().observe(getViewLifecycleOwner(), new Observer<ArrayList<Employee>>() {
                    @Override
                    public void onChanged(ArrayList<Employee> employees) {
                        EmployeeAdapter employeeAdpater = new EmployeeAdapter(employees);
                        recyclerView.setAdapter(employeeAdpater);
                    }
                });
                break;
            case "Marvel":
                recyclerView=binding.myRecycleView;
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setHasFixedSize(false);
                mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
                mainViewModel.getMarvel().observe(getViewLifecycleOwner(), new Observer<ArrayList<Marvel>>() {
                    @Override
                    public void onChanged(ArrayList<Marvel> marvels) {
                        MarvelAdpater marvelAdpater = new MarvelAdpater(marvels);
                        recyclerView.setAdapter(marvelAdpater);
                    }
                });
                break;
            case "DemoNuts":
                recyclerView=binding.myRecycleView;
                recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                recyclerView.setHasFixedSize(false);
                mainViewModel = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
                mainViewModel.getDataItem().observe(getViewLifecycleOwner(), new Observer<ArrayList<DataItem>>() {
                    @Override
                    public void onChanged(ArrayList<DataItem> dataItems) {
                        DemoNutAdapter demoAdapter = new DemoNutAdapter(dataItems);
                        recyclerView.setAdapter(demoAdapter);
                    }
                });
                break;
        }
        return binding.getRoot();
    }
}
