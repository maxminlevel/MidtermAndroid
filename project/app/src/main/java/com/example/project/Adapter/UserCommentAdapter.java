package com.example.project.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Domain.UserCommentDomain;
import com.example.project.R;

import java.util.ArrayList;

public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentAdapter.ViewHolder> {
    ArrayList<UserCommentDomain> userCommentDomains;

    public UserCommentAdapter(ArrayList<UserCommentDomain> userCommentDomains) {
        this.userCommentDomains = userCommentDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_usercomment, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(userCommentDomains.get(position).getUsername());
        holder.comment.setText(userCommentDomains.get(position).getComment());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(userCommentDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return userCommentDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView username;
        TextView comment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            username = itemView.findViewById(R.id.username);
            comment = itemView.findViewById(R.id.comment);
        }
    }
}
