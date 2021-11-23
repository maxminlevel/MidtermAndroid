package com.example.project.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activity.FoodRestaurantActivity;
import com.example.project.Activity.MapsActivity;
import com.example.project.Domain.FoodInRestaurantDomain;
import com.example.project.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantListByFoodAdapter extends RecyclerView.Adapter<RestaurantListByFoodAdapter.ViewHolder>{

    ArrayList<FoodInRestaurantDomain> foodInRestaurant;

    public RestaurantListByFoodAdapter(ArrayList<FoodInRestaurantDomain> FoodInRestaurant) {
        this.foodInRestaurant = FoodInRestaurant;
    }

    @NonNull
    @Override
    public RestaurantListByFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_restaurant, parent, false);

        return new RestaurantListByFoodAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListByFoodAdapter.ViewHolder holder, int position) {
        FoodInRestaurantDomain restaurant = foodInRestaurant.get(position);
        holder.res_name.setText(restaurant.getResName());
        holder.price.setText(String.valueOf((int)restaurant.getPrice()));
        holder.rating.setText(String.valueOf(restaurant.getRating()));

        holder.marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), MapsActivity.class);
                intent.putExtra("food_store",  (Serializable) restaurant);
                intent.putExtra("food",(Serializable) restaurant.getFood());
                holder.itemView.getContext().startActivity(intent);
                // Open GG map and marker the postion of Restaurant
            }

        });
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), FoodRestaurantActivity.class);
                intent.putExtra("food_store", (Serializable) restaurant);
                intent.putExtra("food",(Serializable) restaurant.getFood());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return foodInRestaurant.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView res_name, price, rating, detailBtn;
        ImageView marker;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            res_name = itemView.findViewById(R.id.resName);
            price = itemView.findViewById(R.id.price_food_res);
            rating = itemView.findViewById(R.id.rating_food_res);
            marker = itemView.findViewById(R.id.marker_res);
            detailBtn = itemView.findViewById(R.id.detailBtn);
        }
    }
}
