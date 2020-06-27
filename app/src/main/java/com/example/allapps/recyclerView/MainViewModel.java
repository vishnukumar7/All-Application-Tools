package com.example.allapps.recyclerView;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.allapps.recyclerView.employeeDetails.Employee;
import com.example.allapps.recyclerView.marvel.Marvel;

import java.util.ArrayList;

public class MainViewModel extends AndroidViewModel {
    private LiveData liveData;

    public MainViewModel(@NonNull Application application) {
        super(application);
        liveData=new LiveData();

    }

    public MutableLiveData<ArrayList<Employee>> getEmployee(){
        return liveData.getEmployeeLiveData();
    }

    public MutableLiveData<ArrayList<DataItem>> getDataItem(){
        return liveData.getDemoNutsLiveData();
    }

    public MutableLiveData<ArrayList<Marvel>> getMarvel(){
        return liveData.getMarvelLiveData();
    }
}
