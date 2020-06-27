package com.example.allapps.recyclerView.employeeDetails;

import java.util.List;

public class EmployeeR{
	private List<Employee> data;
	private String status;

	public void setData(List<Employee> data){
		this.data = data;
	}

	public List<Employee> getData(){
		return data;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"EmployeeR{" + 
			"data = '" + data + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}