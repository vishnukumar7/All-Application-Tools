package com.example.allapps.model;

public class FolderModel {

    private String folderPath;
    private String folderName;
    private int pics=0;
    private String firstPics;
    private long size;


    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getPics() {
        return pics;
    }

    public void setPics(int pics) {
        this.pics = pics;
    }

    public String getFirstPics() {
        return firstPics;
    }

    public void setFirstPics(String firstPics) {
        this.firstPics = firstPics;
    }

    public void addPics(){
        this.pics++;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
