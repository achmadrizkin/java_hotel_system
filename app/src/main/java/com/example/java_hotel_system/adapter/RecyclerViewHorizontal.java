package com.example.java_hotel_system.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.java_hotel_system.R;
import com.example.java_hotel_system.model.kamar.Kamar;

import java.util.List;

public class RecyclerViewHorizontal extends RecyclerView.Adapter<RecyclerViewHorizontal.MyViewHolder> {
    private List<Kamar> listItems;

    public void setListDataItems(List<Kamar> listItems) {
        this.listItems = listItems;
    }

    @NonNull
    @Override
    public RecyclerViewHorizontal.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_horizontal, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHorizontal.MyViewHolder holder, int position) {
        holder.tvNama.setText(listItems.get(position).getNama());
        holder.tvLokasi.setText(listItems.get(position).getLokasi());
        holder.tvRating.setText(listItems.get(position).getRating());
        Glide.with(holder.ivKamar).load(listItems.get(position).getImage_url()).into(holder.ivKamar);
    }

    @Override
    public int getItemCount() {
        if (listItems == null) {
            return 0;
        } else {
            return listItems.size();
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvRating, tvLokasi;
        ImageView ivKamar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            ivKamar = itemView.findViewById(R.id.ivKamar);
        }
    }

}
