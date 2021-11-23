package com.example.project.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.project.Domain.MessageDomain;
import com.example.project.R;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    ArrayList<MessageDomain> messageDomains;

    public MessageAdapter(ArrayList<MessageDomain> messageDomains) {
        this.messageDomains = messageDomains;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_message, parent, false);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.username.setText(messageDomains.get(position).getUsername());
        holder.uptime.setText(messageDomains.get(position).getUptime());
        holder.newestMessage.setText(messageDomains.get(position).getNewestMessage());

        int drawableResourceId = holder.itemView.getContext().getResources().getIdentifier(messageDomains.get(position).getPic(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext())
                .load(drawableResourceId)
                .into(holder.pic);
    }

    @Override
    public int getItemCount() {
        return messageDomains.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView pic;
        TextView username, uptime, newestMessage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pic = itemView.findViewById(R.id.pic);
            username = itemView.findViewById(R.id.username);
            uptime = itemView.findViewById(R.id.uptime);
            newestMessage = itemView.findViewById(R.id.newestMessage);
        }
    }

    public void filterList(ArrayList<MessageDomain> filteredItems) {
        messageDomains = filteredItems;
        notifyDataSetChanged();
    }
}
