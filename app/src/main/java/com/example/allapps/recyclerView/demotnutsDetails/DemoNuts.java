package com.example.allapps.recyclerView.demotnutsDetails;

import com.example.allapps.recyclerView.DataItem;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class DemoNuts implements Serializable {
    private String status;
    private String message;
    private List<DataItem> data;

    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return status;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }

    public void setData(List<DataItem> data){
        this.data = data;
    }

    public List<DataItem> getData(){
        return data;
    }

    @NotNull
    @Override
    public String toString(){
        return
                "DemoNuts{" +
                        "status = '" + status + '\'' +
                        ",message = '" + message + '\'' +
                        ",data = '" + data + '\'' +
                        "}";
    }
}
