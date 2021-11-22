package com.example.project.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FoodInRestaurantDomain implements Serializable, Parcelable {
    FoodDomain food;
    String foodID, resName, address, tel;
    double price, rating, lat,lng;

    public FoodInRestaurantDomain(String resName, double rating, double price, String address, double lat, double lng, String tel) {
        this.resName = resName;
        this.rating = rating;
        this.price = price;
        this.address = address;
        this.lat = lat;
        this.lng = lng;
        this.tel = tel;
    }

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

    public FoodDomain getFood() {
        return food;
    }

    public void setFood(FoodDomain food) {
        this.food = food;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFoodID() {
        return foodID;
    }

    public void setFoodID(String foodID) {
        this.foodID = foodID;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected FoodInRestaurantDomain(Parcel in) {
        resName = in.readString();
        address = in.readString();
        tel = in.readString();
        price = in.readDouble();
        rating = in.readDouble();
        lat = in.readDouble();
        lng = in.readDouble();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resName);
        dest.writeString(address);
        dest.writeString(tel);
        dest.writeDouble(price);
        dest.writeDouble(rating);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
    public static final Creator<FoodInRestaurantDomain> CREATOR = new Creator<FoodInRestaurantDomain>() {
        @Override
        public FoodInRestaurantDomain createFromParcel(Parcel in) {
            return new FoodInRestaurantDomain(in);
        }

        @Override
        public FoodInRestaurantDomain[] newArray(int size) {
            return new FoodInRestaurantDomain[size];
        }
    };
}
