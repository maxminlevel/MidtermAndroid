package com.example.project.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private String id;
    private String title;
    private String pic;
    private String description;
    private double averageRating;

    public FoodDomain(String title, String pic, String description, double averageRating) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.averageRating = averageRating;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getAverageRating() {
        return averageRating;
    }
}
