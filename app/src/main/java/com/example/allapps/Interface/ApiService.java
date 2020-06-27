package com.example.allapps.Interface;

import com.example.allapps.recyclerView.demotnutsDetails.DemoNuts;
import com.example.allapps.recyclerView.employeeDetails.EmployeeR;
import com.example.allapps.recyclerView.marvel.Marvel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("api/v1/employees")
    Call<EmployeeR> getMyJSONEmployee();

    @GET("demos/marvel/")
    Call<ArrayList<Marvel>> getMyJSONMarvel();

    @GET("json_parsing.php")
    Call<DemoNuts> getDemoNuts();
}
