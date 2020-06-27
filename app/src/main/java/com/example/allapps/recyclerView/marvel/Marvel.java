package com.example.allapps.recyclerView.marvel;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.allapps.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Marvel {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("realName")
    @Expose
    private String realName;

    @SerializedName("team")
    @Expose
    private String team;

    @SerializedName("firstappearance")
    @Expose
    private String firstAppearance;

    @SerializedName("createdby")
    @Expose
    private String createdBy;

    @SerializedName("publisher")
    @Expose
    private String publisher;

    @SerializedName("imageurl")
    @Expose
    private String image;

    @SerializedName("bio")
    @Expose
    private String bio;

    public void setName(String name){
        this.name=name;
    }

    public void setRealName(String realname){
        this.realName =realname;
    }

    public void setTeam(String team){
        this.team=team;
    }

    public void setFirstAppearance(String firstAppearance){
        this.firstAppearance=firstAppearance;
    }

    public void setCreatedBy(String createdBy){
        this.createdBy=createdBy;
    }

    public void setPublisher(String publisher){
        this.publisher=publisher;
    }

    public void setImage(String image){
        this.image=image;
    }

    public void setBio(String bio){
        this.bio=bio;
    }

    public String getName(){
        return this.name;
    }

    public String getRealName(){
        return this.realName;
    }

    public String getTeam(){
        return this.team;
    }

    public String getFirstAppearance(){
        return this.firstAppearance;
    }

    public String getCreatedBy(){
        return this.createdBy;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public String getImage(){
        return this.image;
    }

    public String getBio(){
        return this.bio;
    }

    @BindingAdapter({ "imageurl" })
    public static void loadImage(ImageView imageView, String imageURL) {
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(imageView);
    }

}
