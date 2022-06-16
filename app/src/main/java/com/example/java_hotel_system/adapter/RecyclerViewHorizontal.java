package com.example.java_hotel_system.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.java_hotel_system.model.kamar.Kamar;
import com.example.java_hotel_system.view.bottomNav.home.byCity.GetByCityActivity;
import com.example.java_hotel_system.view.bottomNav.room_detail.RoomDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewHorizontal extends RecyclerView.Adapter<RecyclerViewHorizontal.MyViewHolder> {
    private ArrayList<Kamar> listItems;
    private Context context;

    public void setListDataItems(ArrayList<Kamar> listItems) {
        this.listItems = listItems;
    }

    public RecyclerViewHorizontal(Context context) {
        this.context = context;
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
        holder.tvLokasi.setText(listItems.get(position).getKota());
        holder.tvRating.setText(listItems.get(position).getRating());
        Glide.with(holder.ivKamar).load(listItems.get(position).getImage_url()).placeholder(R.drawable.erorr_picture).into(holder.ivKamar);

        // INTENT
        holder.cvHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), RoomDetailActivity.class);
                i.putExtra("key", listItems.get(holder.getAdapterPosition()).getKey());
                i.putExtra("name", listItems.get(holder.getAdapterPosition()).getNama());
                i.putExtra("kd_kamar", listItems.get(holder.getAdapterPosition()).getKd_kamar());
                i.putExtra("image_url", listItems.get(holder.getAdapterPosition()).getImage_url());
                i.putExtra("rating", listItems.get(holder.getAdapterPosition()).getRating());
                i.putExtra("kota", listItems.get(holder.getAdapterPosition()).getKota());
                i.putExtra("deskripsi", listItems.get(holder.getAdapterPosition()).getDeskripsi());
                i.putExtra("jmlh_ruangan", listItems.get(holder.getAdapterPosition()).getJmlh_ruangan());
                i.putExtra("jmlh_kasur", listItems.get(holder.getAdapterPosition()).getJmlh_kasur());
                i.putExtra("location", listItems.get(holder.getAdapterPosition()).getLokasi());
                i.putExtra("harga", listItems.get(holder.getAdapterPosition()).getHarga());
                holder.cvHotel.getContext().startActivity(i);
            }
        });
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
        CardView cvHotel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNama = itemView.findViewById(R.id.tvNama);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvLokasi = itemView.findViewById(R.id.tvLokasi);
            ivKamar = itemView.findViewById(R.id.ivKamar);
            cvHotel = itemView.findViewById(R.id.cvHotel);
        }
    }

}
