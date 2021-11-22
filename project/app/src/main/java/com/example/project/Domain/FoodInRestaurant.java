package com.example.project.Domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.DocumentReference;

import java.io.Serializable;

public class FoodInRestaurant implements Serializable, Parcelable {
    protected FoodInRestaurant(Parcel in) {
        resName = in.readString();
        address = in.readString();
        price = in.readDouble();
        rating = in.readDouble();
        lat = in.readDouble();
        lng = in.readDouble();
    }

    public static final Creator<FoodInRestaurant> CREATOR = new Creator<FoodInRestaurant>() {
        @Override
        public FoodInRestaurant createFromParcel(Parcel in) {
            return new FoodInRestaurant(in);
        }

        @Override
        public FoodInRestaurant[] newArray(int size) {
            return new FoodInRestaurant[size];
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

    public FoodInRestaurant(String ResName, double Rating, double Price, String Address,double Lat,double Lng,String Tel) {
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
