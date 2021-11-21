package com.example.project.Domain;

import java.io.Serializable;

public class FoodInRestaurant {
    public String getResName() {
        return resName;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public double getRating() {
        return rating;
    }

    String resName;
    String address;
    String location;
    double price;
    double rating;
    String phone;

    public void setResName(String resName) {
        this.resName = resName;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public FoodInRestaurant(String ResName, double Rating, double Price, String Address, String Location, String Phone) {
        resName = ResName;
        rating=Rating;
        price=Price;
        location = Location;
        address=Address;
        phone = Phone;
    }
}
