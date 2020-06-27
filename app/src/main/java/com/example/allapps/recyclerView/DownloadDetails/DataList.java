package com.example.allapps.recyclerView.DownloadDetails;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DataList {

    @SerializedName("content_upload_id")
    @Expose
    private String upload_id;

    @SerializedName("content_type")
    @Expose
    private String content_type;

    @SerializedName("content_download_path")
    @Expose
    private String content_download_path;

    @SerializedName("contentSize")
    @Expose
    private String contentSize;

    @SerializedName("checksum")
    @Expose
    private String checksum;

    @SerializedName("content_display_name")
    @Expose
    private String content_display_name;


    public void setUpload_id(String id){
        upload_id=id;
    }

    public void setContent_type(String type){
        content_type=type;
    }

    public void setContent_download_path(String path){
        content_download_path=path;
    }

    public void setContentSize(String size){
        contentSize=size;
    }

    public void setChecksum(String checksum){
        this.checksum=checksum;
    }

    public void setContent_display_name(String name){
        content_display_name=name;
    }

    public String getUpload_id(){
        return this.upload_id;
    }

    public String getContent_type(){
        return this.content_type;
    }

    public String getContent_download_path(){
        return this.content_download_path;
    }

    public String getContentSize(){
        return this.contentSize;
    }

    public String getChecksum(){
        return this.checksum;
    }

    public String getContent_display_name(){
        return this.content_display_name;
    }
}
