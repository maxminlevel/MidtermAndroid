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
    double price;
    double rating;
    double lat,lng;

    public FoodInRestaurant(String ResName, double Rating, double Price, String Address,double Lat,double Lng) {
        resName = ResName;
        rating=Rating;
        price=Price;
        address=Address;
        lat = Lat;
        lng = Lng;
    }
}
