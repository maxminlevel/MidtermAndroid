package com.example.project.Domain;

import java.io.Serializable;

public class FoodDomain implements Serializable {
    private String title;
    private String pic;
    private String description;
    private String ingredient;
    private double averageRating;

    public FoodDomain(String title, String pic, String description, String ingredient, double averageRating) {
        this.title = title;
        this.pic = pic;
        this.description = description;
        this.ingredient = ingredient;
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

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getAverageRating() {
        return averageRating;
    }
}
