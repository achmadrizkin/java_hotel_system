package com.example.java_hotel_system.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.user.ListUser;
import com.example.java_hotel_system.model.user.UserRequest;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAllUser  extends RecyclerView.Adapter<RecyclerViewAllUser.MyViewHolder> {
    private ListUser listItems;
    private Context context;

    public void setListDataItems(ListUser listItems) {
        this.listItems = listItems;
    }

    public RecyclerViewAllUser(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAllUser.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_all_user, parent, false);
        return new RecyclerViewAllUser.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAllUser.MyViewHolder holder, int position) {
        holder.tvUID.setText(listItems.getData().get(position).getRole());
        holder.tvName.setText(listItems.getData().get(position).getName());
        Glide.with(holder.ivPicture).load(listItems.getData().get(position).getImage_url()).placeholder(R.drawable.erorr_picture).into(holder.ivPicture);
    }

    @Override
    public int getItemCount() {
        if (listItems == null) {
            return 0;
        } else {
            return listItems.getData().size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvUID;
        ImageView ivPicture;
        CardView cvUser;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPicture = itemView.findViewById(R.id.ivPicture);
            tvName = itemView.findViewById(R.id.tvName);
            cvUser = itemView.findViewById(R.id.cvUser);
            tvUID = itemView.findViewById(R.id.tvUID);
        }
    }
}
