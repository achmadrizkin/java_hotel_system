package com.example.java_hotel_system.dao;

import com.example.java_hotel_system.model.booking.Booking;
import com.example.java_hotel_system.model.kamar.Kamar;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DaoBooking {
    private DatabaseReference databaseReference;

    public DaoBooking() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Booking.class.getSimpleName());
    }

    public Task<Void> addBooking(Booking booking) {
        return databaseReference.push().setValue(booking);
    }

    public Query getBookingByUser(String uid) {
        return databaseReference.orderByChild("user_buy").startAt(uid).endAt(uid+ "~");
    }

    public Query checkKdBooking(String kd_booking) {
        return databaseReference.orderByChild("kd_booking").startAt(kd_booking).endAt(kd_booking+ "~");
    }

    // 20-06-2022
    public Query getBookingByCurrentDate(String current_date) {
        return databaseReference.orderByChild("current_date").startAt(current_date).endAt(current_date+ "~");
    }
}
