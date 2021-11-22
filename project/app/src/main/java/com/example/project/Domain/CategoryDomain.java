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

    public CategoryDomain(String id, String name, String pic) {
        this.id = id;
        this.name = name;
        this.pic = pic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
