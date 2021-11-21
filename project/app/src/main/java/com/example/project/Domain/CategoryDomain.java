package com.example.project.Domain;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class CategoryDomain implements Serializable{

    @Exclude
    private String id;

    private String name;
    private String pic;

    public CategoryDomain(){

    }

    public CategoryDomain(String name, String pic) {
        this.name = name;
        this.pic = pic;
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
}
