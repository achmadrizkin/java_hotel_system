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
import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.view.bottomNav.booking.booking_details.BookingDetailsActivity;

import java.util.ArrayList;

public class RecyclerViewBooking extends RecyclerView.Adapter<RecyclerViewBooking.MyViewHolder>{
    private ArrayList<Booking> listItems;
    private Context context;

    public void setListDataItems(ArrayList<Booking> listItems) {
        this.listItems = listItems;
    }

    public RecyclerViewBooking(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewBooking.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_booking, parent, false);
        return new RecyclerViewBooking.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewBooking.MyViewHolder holder, int position) {
        holder.tvNamaKamar.setText(listItems.get(position).getNama_kamar());
        holder.tvKota.setText(listItems.get(position).getKota());
        holder.tvRating.setText(listItems.get(position).getRating());
        Glide.with(holder.ivImage).load(listItems.get(position).getImage_url()).placeholder(R.drawable.erorr_picture).into(holder.ivImage);

        holder.cvHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), BookingDetailsActivity.class);

                i.putExtra("nama_kamar", listItems.get(holder.getAdapterPosition()).getNama_kamar());
                i.putExtra("rating", listItems.get(holder.getAdapterPosition()).getRating());
                i.putExtra("lokasi", listItems.get(holder.getAdapterPosition()).getLokasi());
                i.putExtra("image_url", listItems.get(holder.getAdapterPosition()).getImage_url());
                i.putExtra("user_buy", listItems.get(holder.getAdapterPosition()).getUser_buy());
                i.putExtra("kd_booking", listItems.get(holder.getAdapterPosition()).getKd_booking());
                i.putExtra("kota", listItems.get(holder.getAdapterPosition()).getKota());
                i.putExtra("check_in", listItems.get(holder.getAdapterPosition()).getCheck_in());
                i.putExtra("check_out", listItems.get(holder.getAdapterPosition()).getCheck_out());
                i.putExtra("user_buy", listItems.get(holder.getAdapterPosition()).getUser_buy());
                i.putExtra("total", listItems.get(holder.getAdapterPosition()).getTotal());

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
        TextView tvNamaKamar, tvRating, tvKota;
        ImageView ivImage;
        CardView cvHotel;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNamaKamar = itemView.findViewById(R.id.tvNamaKamar);
            tvRating = itemView.findViewById(R.id.tvRating);
            tvKota = itemView.findViewById(R.id.tvKota);
            ivImage = itemView.findViewById(R.id.ivImage);
            cvHotel = itemView.findViewById(R.id.cvHotel);
        }
    }
}
