package com.example.project.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class FoodInRestaurantDomain implements Serializable, Parcelable {
    protected FoodInRestaurantDomain(Parcel in) {
        resName = in.readString();
        address = in.readString();
        price = in.readDouble();
        rating = in.readDouble();
        lat = in.readDouble();
        lng = in.readDouble();
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

    FoodDomain food;
    String resName;
    String address;
    double price;
    double rating;
    double lat,lng;

    public FoodDomain getFood() {
        return food;
    }

    public void setFood(FoodDomain food) {
        this.food = food;
    }

    public String getTel() {
        return tel;
    }

    String tel;

    public FoodInRestaurantDomain(String ResName, double Rating, double Price, String Address, double Lat, double Lng, String Tel) {
        resName = ResName;
        rating = Rating;
        price = Price;
        address = Address;
        lat = Lat;
        lng = Lng;
        tel = Tel;
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(resName);
        dest.writeString(address);
        dest.writeDouble(price);
        dest.writeDouble(rating);
        dest.writeDouble(lat);
        dest.writeDouble(lng);
    }
}
