package com.example.allapps.recyclerView.employeeDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Employee {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("employee_name")
    @Expose
    private String name;

    @SerializedName("employee_age")
    @Expose
    private String age;

    @SerializedName("employee_salary")
    @Expose
    private String salary;

    public void setId(String id){
        this.id=id;
    }

    public void setName(String name){
        this.name=name;
    }

    public void setAge(String age){
        this.age=age;
    }

    public void setSalary(String salary){
        this.salary=salary;
    }

    public String getId(){ return this.id; }

    public String getName(){
        return this.name;
    }

    public String getAge(){
        return this.age;
    }

    public String getSalary(){
        return this.salary;
    }

}
