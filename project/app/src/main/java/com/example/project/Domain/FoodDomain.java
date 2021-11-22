package com.example.project.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;
import java.util.ArrayList;

public class FoodDomain implements Serializable {

    @Exclude
    private String id;
    private String name, pic, desc;
    DocumentReference category;
    private String averageRating;

    public FoodDomain(){

    }

    public FoodDomain(String id, String name, String pic, String desc, DocumentReference category, String averageRating) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.desc = desc;
        this.category = category;
        this.averageRating = averageRating;
    }

    public FoodDomain(String id, String name, String pic, String description, String averageRating) {
        this.id = id;
        this.name = name;
        this.pic = pic;
        this.desc = description;
        this.averageRating = averageRating;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    protected FoodDomain(Parcel in) {
        id = in.readString();
        name = in.readString();
        pic = in.readString();
        desc = in.readString();
        averageRating = in.readString();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public DocumentReference getCategory() {
        return category;
    }

    public void setCategory(DocumentReference category) {
        this.category = category;
    }

    public String getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(String averageRating) {
        this.averageRating = averageRating;
    }

    public String getId() {
        return id;
    }


}
