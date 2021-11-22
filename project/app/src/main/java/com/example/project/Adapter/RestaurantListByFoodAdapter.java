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
import com.example.project.Activity.OneMarkerMapsActivity;
import com.example.project.Domain.FoodInRestaurantDomain;
import com.example.project.R;

import java.io.Serializable;
import java.util.ArrayList;

public class RestaurantListByFoodAdapter extends RecyclerView.Adapter<RestaurantListByFoodAdapter.ViewHolder>{
    ArrayList<FoodInRestaurantDomain> foodInRestaurantDomain;

    public RestaurantListByFoodAdapter(ArrayList<FoodInRestaurantDomain> FoodInRestaurantDomain) {
        this.foodInRestaurantDomain = FoodInRestaurantDomain;
    }

    @NonNull
    @Override
    public RestaurantListByFoodAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_restaurant, parent, false);

        return new RestaurantListByFoodAdapter.ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantListByFoodAdapter.ViewHolder holder, int position) {
        FoodInRestaurantDomain restaurant = foodInRestaurantDomain.get(position);
        holder.res_name.setText(restaurant.getResName());
        holder.price.setText(String.valueOf(restaurant.getPrice()));
        holder.rating.setText(String.valueOf(restaurant.getRating()));

        //int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodInRestaurant.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

//        Glide.with(holder.itemView.getContext())
//                .load(drawableResourceId)
//                .into(holder.pic);

        holder.marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), OneMarkerMapsActivity.class);
                intent.putExtra("restaurant",  (Serializable) restaurant);
                holder.itemView.getContext().startActivity(intent);
                // Open GG map and marker the postion of Restaurant
            }

        });
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), FoodRestaurantActivity.class);
                intent.putExtra("price", restaurant.getPrice());
                intent.putExtra("nameStore", restaurant.getResName());

                intent.putExtra("phoneStore", restaurant.getTel());

                intent.putExtra("addressStore", restaurant.getAddress());
                intent.putExtra("rating", restaurant.getRating());
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return foodInRestaurantDomain.size();
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
