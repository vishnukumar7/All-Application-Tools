package com.example.allapps.recyclerView;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.allapps.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class DataItem {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("country")
    @Expose
    private String country;

    @SerializedName("city")
    @Expose
    private String city;

    @SerializedName("imgURL")
    @Expose
    private String imgURL;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setCountry(String country){
        this.country = country;
    }

    public String getCountry(){
        return country;
    }

    public void setCity(String city){
        this.city = city;
    }

    public String getCity(){
        return city;
    }

    public void setImgURL(String imgURL){
        this.imgURL = imgURL;
    }

    public String getImgURL(){
        return imgURL;
    }

    @NotNull
    @Override
    public String toString(){
        return
                "DataItem{" +
                        "id = '" + id + '\'' +
                        ",name = '" + name + '\'' +
                        ",country = '" + country + '\'' +
                        ",city = '" + city + '\'' +
                        ",imgURL = '" + imgURL + '\'' +
                        "}";
    }

    @BindingAdapter({ "imgURL" })
    public static void loadImage(ImageView imageView, String imgURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions().circleCrop())
                .load(imgURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }
}