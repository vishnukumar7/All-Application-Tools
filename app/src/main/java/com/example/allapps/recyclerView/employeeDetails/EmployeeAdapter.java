package com.example.allapps.recyclerView.employeeDetails;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.allapps.R;
import com.example.allapps.databinding.EmployeeViewBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class EmployeeAdapter extends RecyclerView.Adapter<EmployeeAdapter.CustomViewHolder> {
    private final ArrayList<Employee> employees;


    public EmployeeAdapter(ArrayList<Employee> employees) {
        this.employees = employees;
    }


    @NotNull
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EmployeeViewBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.employee_view, parent, false);
        return new CustomViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(EmployeeAdapter.CustomViewHolder holder, final int position) {
        Employee employee = employees.get(position);
        System.out.println("//employee : "+employee.getId());
        System.out.println("//employee : "+employee.getName());
        System.out.println("//employee : "+employee.getSalary());
        System.out.println("//employee : "+employee.getAge());
       holder.binding.setEmployee(employee);

    }

    @Override
    public int getItemCount() {
        return employees.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        EmployeeViewBinding binding;
        CustomViewHolder(EmployeeViewBinding view) {
            super(view.getRoot());
            binding=view;
        }

    }


}
