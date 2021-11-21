package com.example.project.Domain;

import com.google.firebase.database.Exclude;
import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

public class FoodDomain implements Serializable {

    @Exclude
    private String id;

    private String name, pic, desc;
    DocumentReference category;
    private String averageRating;

    public FoodDomain(){

    }

    public FoodDomain(String name, String pic, String desc, DocumentReference category, String averageRating) {
        this.name = name;
        this.pic = pic;
        this.desc = desc;
        this.category = category;
        this.averageRating = averageRating;
    }

    public FoodDomain(String name, String pic, String description, String averageRating) {
        this.name = name;
        this.pic = pic;
        this.desc = description;
        this.averageRating = averageRating;
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
}
