package com.example.allapps.recyclerView;

import androidx.lifecycle.MutableLiveData;

import com.example.allapps.Interface.ApiService;
import com.example.allapps.Utils;
import com.example.allapps.recyclerView.employeeDetails.Employee;
import com.example.allapps.recyclerView.employeeDetails.EmployeeR;
import com.example.allapps.recyclerView.marvel.Marvel;
import com.example.allapps.recyclerView.demotnutsDetails.DemoNuts;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LiveData {

    private ArrayList<DataItem> demoNutsArrayList=new ArrayList<>();
    private ArrayList<Marvel> marvelArrayList=new ArrayList<>();
    private ArrayList<Employee> employeeArrayList=new ArrayList<>();


    private MutableLiveData<ArrayList<DataItem>> demoNutsLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Marvel>> marvelLiveData=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Employee>> employeeLiveData=new MutableLiveData<>();
    public LiveData(){

    }

    public MutableLiveData<ArrayList<DataItem>> getDemoNutsLiveData() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://demonuts.com/Demonuts/JsonTest/Tennis/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService api=retrofit.create(ApiService.class);
        Call<DemoNuts> demoNutsCall=api.getDemoNuts();

        //retrieve the data where store in webservice using internet
        demoNutsCall.enqueue(new Callback<DemoNuts>() {
            @Override
            public void onResponse(@NotNull Call<DemoNuts> call, @NotNull Response<DemoNuts> response) {
                if(response.isSuccessful()){
                    if (response.body() != null) {
                        demoNutsArrayList= (ArrayList<DataItem>) response.body().getData();
                        demoNutsLiveData.setValue(demoNutsArrayList);
                    }
                }
                else
                {
                    Utils.logcatDemoNuts("not response");
                }
            }
            @Override
            public void onFailure(@NotNull Call<DemoNuts> call, @NotNull Throwable t) {

            }
        });
        return demoNutsLiveData;
    }



    public MutableLiveData<ArrayList<Employee>> getEmployeeLiveData() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("http://dummy.restapiexample.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api=retrofit.create(ApiService.class);

        //locating the data endpoint path in webservice
        Call<EmployeeR> call=api.getMyJSONEmployee();

        //retrieve the data where store in webservice using internet
        call.enqueue(new Callback<EmployeeR>() {
            @Override
            public void onResponse(@NotNull Call<EmployeeR> call, @NotNull Response<EmployeeR> response) {

                if(response.isSuccessful()){
                  employeeArrayList= (ArrayList<Employee>) response.body().getData();
                  employeeLiveData.setValue(employeeArrayList);
                    System.out.println("//employee : "+employeeArrayList.size());

                }
                else
                {
                    System.out.println("//employee not response");
                }
            }
            @Override
            public void onFailure(@NotNull Call<EmployeeR> call, @NotNull Throwable t) {
                System.out.println("//employee on failure "+t.toString());
            }
        });
        return employeeLiveData;
    }

    public MutableLiveData<ArrayList<Marvel>> getMarvelLiveData() {
        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://simplifiedcoding.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService api=retrofit.create(ApiService.class);

        //locating the data endpoint path in webservice
        Call<ArrayList<Marvel>> call=api.getMyJSONMarvel();

        //retrieve the data where store in webservice using internet
        call.enqueue(new Callback<ArrayList<Marvel>>() {
            @Override
            public void onResponse(@NotNull Call<ArrayList<Marvel>> call, @NotNull Response<ArrayList<Marvel>> response) {

                if(response.isSuccessful()){
                    marvelArrayList=response.body();
                   marvelLiveData.setValue(marvelArrayList);

                }
                else
                {
                    Utils.logcatMarvel("not response");
                }
            }
            @Override
            public void onFailure(@NotNull Call<ArrayList<Marvel>> call, @NotNull Throwable t) {

            }
        });
        return marvelLiveData;
    }
}
