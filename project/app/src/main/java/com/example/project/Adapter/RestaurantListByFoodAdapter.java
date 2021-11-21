package com.example.project.Adapter;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Activity.FoodStoreActivity;
import com.example.project.Activity.LoginActivity;
import com.example.project.Domain.FoodDomain;
import com.example.project.Domain.FoodInRestaurant;
import com.example.project.R;

import java.util.ArrayList;

public class RestaurantListByFoodAdapter extends RecyclerView.Adapter<RestaurantListByFoodAdapter.ViewHolder>{
    ArrayList<FoodInRestaurant> foodInRestaurant;

    public RestaurantListByFoodAdapter(ArrayList<FoodInRestaurant> FoodInRestaurant) {
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
        holder.res_name.setText(foodInRestaurant.get(position).getResName());
        holder.price.setText(String.valueOf(foodInRestaurant.get(position).getPrice()));
        holder.rating.setText(String.valueOf(foodInRestaurant.get(position).getRating()));
        //int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(foodInRestaurant.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

//        Glide.with(holder.itemView.getContext())
//                .load(drawableResourceId)
//                .into(holder.pic);

        holder.marker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(holder.itemView.getContext(), FoodStoreActivity.class);
//                //intent.putExtra("object", foodInRestaurant.get(position));
//                holder.itemView.getContext().startActivity(intent);
                // Open GG map and marker the postion of Restaurant
            }
        });
        holder.detailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), FoodStoreActivity.class));
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
